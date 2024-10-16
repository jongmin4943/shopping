package com.min.shopping.product.acceptance;

import com.min.shopping.common.Category;
import com.min.shopping.core.AcceptanceTest;
import com.min.shopping.core.DatabasePopulatorUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static com.min.shopping.product.acceptance.steps.ProductSteps.단일_브랜드_전체_카테고리_최저가격_조회_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.카테고리별_최저가격_브랜드_상품_조회_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.특정_카테고리_최저_최고_가격_상품_브랜드_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("상품 집계 관련 기능")
@AcceptanceTest
class ProductPriceAcceptanceTest {

    @Autowired
    DataSource dataSource;

    /**
     * Given 초기 데이터를 셋팅하고
     */
    @BeforeEach
    void setUp() {
        DatabasePopulatorUtils.initData(dataSource);
    }

    /**
     * When 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 조회하면
     * Then 결과를 확인할 수 있다
     */
    @DisplayName("카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 조회할 수 있다")
    @Test
    void 카테고리별_최저가격_브랜드_상품_조회_테스트() {
        // when
        final var 응답 = 카테고리별_최저가격_브랜드_상품_조회_요청();

        // then
        카테고리별_최저가격_브랜드_상품_조회_결과_확인_성공(응답);
    }

    /**
     * When 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 조회하면
     * Then 결과를 확인할 수 있다
     */
    @DisplayName("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 조회할 수 있다")
    @Test
    void 단일_브랜드_전체_카테고리_최저가격_조회_테스트() {
        // when
        final var 응답 = 단일_브랜드_전체_카테고리_최저가격_조회_요청();

        // then
        단일_브랜드_전체_카테고리_최저가격_조회_결과_확인_성공(응답);
    }

    /**
     * When 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회하면
     * Then 결과를 확인할 수 있다
     */
    @DisplayName("특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회할 수 있다")
    @Test
    void 특정_카테고리_최저_최고_가격_상품_브랜드_조회_테스트() {
        // when
        final var 응답 = 특정_카테고리_최저_최고_가격_상품_브랜드_조회_요청(Category.TOP);

        // then
        특정_카테고리_최저_최고_가격_상품_브랜드_조회_결과_확인_성공(응답);
    }

    private void 카테고리별_최저가격_브랜드_상품_조회_결과_확인_성공(final ExtractableResponse<Response> response) {
        final double totalPrice = response.body().jsonPath().getDouble("totalPrice");

        assertThat(totalPrice).isEqualTo(34100);
    }

    private void 단일_브랜드_전체_카테고리_최저가격_조회_결과_확인_성공(final ExtractableResponse<Response> response) {
        final JsonPath jsonPath = response.body().jsonPath();
        final String brandName = jsonPath.getString("brandName");
        final double totalPrice = jsonPath.getDouble("totalPrice");

        assertSoftly(softly -> {
            softly.assertThat(brandName).isEqualTo("D");
            softly.assertThat(totalPrice).isEqualTo(36100);
        });
    }

    private void 특정_카테고리_최저_최고_가격_상품_브랜드_조회_결과_확인_성공(final ExtractableResponse<Response> response) {
        final JsonPath jsonPath = response.body().jsonPath();
        final String category = jsonPath.getString("category");
        final String lowestBrandName = jsonPath.getString("lowestPriceProduct.brandName");
        final double lowestPrice = jsonPath.getDouble("lowestPriceProduct.price");
        final String highestBrandName = jsonPath.getString("highestPriceProduct.brandName");
        final double highestPrice = jsonPath.getDouble("highestPriceProduct.price");

        assertSoftly(softly -> {
            softly.assertThat(category).isEqualTo("TOP");
            softly.assertThat(lowestBrandName).isEqualTo("C");
            softly.assertThat(lowestPrice).isEqualTo(10000);
            softly.assertThat(highestBrandName).isEqualTo("I");
            softly.assertThat(highestPrice).isEqualTo(11400);
        });
    }

}
