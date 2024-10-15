package com.min.shopping.brand.acceptance.steps;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

public class BrandSteps {

    public static ExtractableResponse<Response> 브랜드_등록_요청(
            final String name
    ) {
        final Map<String, ?> body = Map.of("name", name);
        return RestAssured
                .given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/brands")
                .then().extract();
    }

    public static ExtractableResponse<Response> 브랜드_목록_조회_요청() {
        return RestAssured
                .given()
                .when().get("/brands")
                .then().extract();
    }

    public static ExtractableResponse<Response> 브랜드_수정_요청(
            final String brandId,
            final String name
    ) {
        final Map<String, ?> body = Map.of("name", name);
        return RestAssured
                .given().pathParam("id", brandId)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/brands/{id}")
                .then().extract();
    }

    public static ExtractableResponse<Response> 브랜드_삭제_요청(
            final String brandId
    ) {
        return RestAssured
                .given().pathParam("id", brandId)
                .when().delete("/brands/{id}")
                .then().extract();
    }

}
