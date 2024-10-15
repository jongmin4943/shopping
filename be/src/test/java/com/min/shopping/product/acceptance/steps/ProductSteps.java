package com.min.shopping.product.acceptance.steps;

import com.min.shopping.common.Category;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Map;

public class ProductSteps {

    public static ExtractableResponse<Response> 상품_등록_요청(
            final String brandId,
            final Category category,
            final BigDecimal price
    ) {
        final Map<String, ?> body = Map.of("brandId", brandId, "category", category, "price", price);
        return RestAssured
                .given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/products")
                .then().extract();
    }

    public static ExtractableResponse<Response> 상품_상세_조회_요청(
            final String productId
    ) {
        return RestAssured
                .given().pathParam("id", productId)
                .when().get("/products/{id}")
                .then().extract();
    }

    public static ExtractableResponse<Response> 상품_수정_요청(
            final String productId,
            final Category category,
            final BigDecimal price
    ) {
        final Map<String, ?> body = Map.of("category", category, "price", price);
        return RestAssured
                .given().pathParam("id", productId)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/products/{id}")
                .then().extract();
    }

    public static ExtractableResponse<Response> 상품_삭제_요청(
            final String productId
    ) {
        return RestAssured
                .given().pathParam("id", productId)
                .when().delete("/products/{id}")
                .then().extract();
    }

}
