package com.min.shopping.product.infra;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductLowestPricesResponse;
import com.min.shopping.product.application.dto.ProductResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductLowestPricesResponseRowMapper implements RowMapper<ProductLowestPricesResponse> {
    @Override
    public ProductLowestPricesResponse mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final List<ProductResponse> products = new ArrayList<>();
        do {
            final ProductResponse productResponse = new ProductResponse(
                    rs.getLong("id"),
                    rs.getLong("brand_id"),
                    Category.valueOf(rs.getString("category")),
                    rs.getBigDecimal("price")
            );
            products.add(productResponse);
        } while (rs.next());

        return new ProductLowestPricesResponse(products);
    }
}
