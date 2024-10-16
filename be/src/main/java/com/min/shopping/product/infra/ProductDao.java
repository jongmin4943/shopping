package com.min.shopping.product.infra;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductLowestPriceForSingleBrandResponse;
import com.min.shopping.product.application.dto.ProductLowestPricesResponse;
import com.min.shopping.product.application.dto.ProductPriceRangeForCategoryResponse;
import com.min.shopping.product.application.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private static final RowMapper<ProductResponse> PRODUCT_RESPONSE_ROW_MAPPER = new ProductMapper();
    private static final RowMapper<ProductLowestPricesResponse> PRODUCT_LOWEST_PRICES_RESPONSE_ROW_MAPPER = new ProductLowestPricesResponseRowMapper();
    private static final RowMapper<ProductLowestPriceForSingleBrandResponse> PRODUCT_LOWEST_PRICE_FOR_SINGLE_BRAND_RESPONSE_ROW_MAPPER = new ProductLowestPriceForSingleBrandResponseRowMapper();
    private static final RowMapper<ProductPriceRangeForCategoryResponse> PRODUCT_PRICE_RANGE_FOR_CATEGORY_RESPONSE_ROW_MAPPER = new ProductPriceRangeForCategoryResponseRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<ProductLowestPricesResponse> findLowestPriceProductsByCategory() {
        final String sql = """
                WITH LowestPricePerCategory AS (SELECT p.id,
                                                       p.category,
                                                       b.id                                                         AS brand_id,
                                                       b.name                                                       AS brand_name,
                                                       p.price,
                                                       ROW_NUMBER() OVER (PARTITION BY p.CATEGORY ORDER BY p.PRICE) AS rn
                                                FROM PRODUCT p
                                                         JOIN BRAND b ON p.BRAND_ID = b.ID
                                                where b.STATUS = 'ACTIVE')
                SELECT id,
                       category,
                       brand_id,
                       brand_name,
                       price
                FROM LowestPricePerCategory
                WHERE rn = 1
                ORDER BY CATEGORY
                """;

        return queryForSingleResult(sql, PRODUCT_LOWEST_PRICES_RESPONSE_ROW_MAPPER);
    }

    public Optional<ProductLowestPriceForSingleBrandResponse> findLowestTotalPriceSingleBrandId() {
        final String sql = """
                SELECT b.id         AS brand_id,
                       b.name       AS brand_name,
                       SUM(p.price) AS total_price
                FROM brand b
                         JOIN
                     product p ON b.id = p.brand_id
                WHERE b.status = 'ACTIVE'
                GROUP BY b.id, b.name
                HAVING COUNT(DISTINCT p.category) = :categoryTypeCounts
                ORDER BY total_price
                LIMIT 1
                """;

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("categoryTypeCounts", Category.countTypes());

        return queryForSingleResult(
                sql,
                parameters,
                PRODUCT_LOWEST_PRICE_FOR_SINGLE_BRAND_RESPONSE_ROW_MAPPER
        );
    }

    public List<ProductResponse> getLowestPriceProductByCategoryForBrand(final Long brandId) {
        final String sql = """
                SELECT p.id    AS product_id,
                       p.category,
                       p.price AS lowest_price
                FROM product p
                         JOIN (SELECT category, MIN(price) AS min_price
                               FROM product
                               WHERE brand_id = :brandId
                               GROUP BY category) AS min_prices ON p.category = min_prices.category AND p.price = min_prices.min_price
                WHERE p.brand_id = :brandId
                ORDER BY p.category
                """;

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("brandId", brandId);

        return namedParameterJdbcTemplate.query(sql, parameters, PRODUCT_RESPONSE_ROW_MAPPER);
    }

    // 특정 카테고리에서 최저가격 및 최고가격 브랜드와 가격 조회 API
    public Optional<ProductPriceRangeForCategoryResponse> getPriceRangeForCategory(final Category category) {
        final String sql = """
                SELECT p.category,
                       (SELECT p2.id
                        FROM product p2
                                 JOIN brand b2 ON b2.id = p2.brand_id
                        WHERE p2.category = p.category
                          AND b2.status = 'ACTIVE'
                        ORDER BY p2.price
                        LIMIT 1)    AS lowest_price_product_id,
                       (SELECT b2.name
                        FROM brand b2
                                 JOIN product p2 ON b2.id = p2.brand_id
                        WHERE p2.category = p.category
                          AND b2.status = 'ACTIVE'
                        ORDER BY p2.price
                        LIMIT 1)    AS lowest_price_brand_name,
                       MIN(p.price) AS lowest_price,
                       (SELECT p2.id
                        FROM product p2
                                 JOIN brand b2 ON b2.id = p2.brand_id
                        WHERE p2.category = p.category
                          AND b2.status = 'ACTIVE'
                        ORDER BY p2.price DESC
                        LIMIT 1)    AS highest_price_product_id,
                       (SELECT b2.name
                        FROM brand b2
                                 JOIN product p2 ON b2.id = p2.brand_id
                        WHERE p2.category = p.category
                          AND b2.status = 'ACTIVE'
                        ORDER BY p2.price DESC
                        LIMIT 1)    AS highest_price_brand_name,
                       MAX(p.price) AS highest_price
                FROM product p
                         JOIN brand b ON p.brand_id = b.id
                WHERE p.category = :category
                  AND b.status = 'ACTIVE'
                GROUP BY p.category
                """;

        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("category", category.name());

        return queryForSingleResult(sql, parameters, PRODUCT_PRICE_RANGE_FOR_CATEGORY_RESPONSE_ROW_MAPPER);
    }

    private <T> Optional<T> queryForSingleResult(final String sql, final RowMapper<T> rowMapper) {
        final List<T> result = namedParameterJdbcTemplate.query(sql, rowMapper);
        return getSingleResult(result);
    }

    private <T> Optional<T> queryForSingleResult(final String sql, final MapSqlParameterSource parameters, final RowMapper<T> rowMapper) {
        final List<T> result = namedParameterJdbcTemplate.query(sql, parameters, rowMapper);
        return getSingleResult(result);
    }

    private <T> Optional<T> getSingleResult(final List<T> result) {
        if (result.size() > 1) {
            throw new IllegalStateException("Expected a single result, but got multiple.");
        }
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
