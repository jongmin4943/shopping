package com.min.shopping.product.infra;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<ProductResponse> {
    @Override
    public ProductResponse mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new ProductResponse(
                rs.getLong("id"),
                null,
                Category.valueOf(rs.getString("category")),
                rs.getBigDecimal("price")
        );
    }
}
