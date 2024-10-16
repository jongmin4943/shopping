package com.min.shopping.product.infra;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductLowestPriceForSingleBrandResponse;
import com.min.shopping.product.application.dto.ProductResponse;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductLowestPriceForSingleBrandResponseRowMapper implements RowMapper<ProductLowestPriceForSingleBrandResponse> {
    @Override
    public ProductLowestPriceForSingleBrandResponse mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final List<ProductResponse> products = new ArrayList<>();

        final Long brandId = rs.getLong("brand_id");
        final String brandName = rs.getString("brand_name");

        return new ProductLowestPriceForSingleBrandResponse(brandId, brandName, products);
    }
}
