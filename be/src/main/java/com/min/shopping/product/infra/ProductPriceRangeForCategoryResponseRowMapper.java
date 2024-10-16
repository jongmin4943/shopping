package com.min.shopping.product.infra;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductPriceRangeForCategoryResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductPriceRangeForCategoryResponseRowMapper implements RowMapper<ProductPriceRangeForCategoryResponse> {
    @Override
    public ProductPriceRangeForCategoryResponse mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new ProductPriceRangeForCategoryResponse(
                Category.valueOf(rs.getString("category")),
                ProductPriceRangeForCategoryResponse.createBrandProduct(
                        rs.getString("lowest_price_brand_name"),
                        rs.getLong("lowest_price_product_id"),
                        rs.getBigDecimal("lowest_price")
                ),
                ProductPriceRangeForCategoryResponse.createBrandProduct(
                        rs.getString("highest_price_brand_name"),
                        rs.getLong("highest_price_product_id"),
                        rs.getBigDecimal("highest_price")
                )
        );
    }
}
