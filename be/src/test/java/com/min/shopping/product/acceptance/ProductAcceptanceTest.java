package com.min.shopping.product.acceptance;

import com.min.shopping.common.Category;
import com.min.shopping.core.AcceptanceTest;
import com.min.shopping.product.application.dto.ProductResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.min.shopping.brand.acceptance.steps.BrandSteps.브랜드_등록_요청;
import static com.min.shopping.core.AcceptanceTestUtils.등록_식별자_추출;
import static com.min.shopping.core.AcceptanceTestUtils.등록요청_성공;
import static com.min.shopping.core.AcceptanceTestUtils.삭제요청_성공;
import static com.min.shopping.core.AcceptanceTestUtils.수정요청_성공;
import static com.min.shopping.core.AcceptanceTestUtils.존재하지_않는_리소스_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.상품_등록_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.상품_삭제_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.상품_상세_조회_요청;
import static com.min.shopping.product.acceptance.steps.ProductSteps.상품_수정_요청;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayName("상품 관련 기능")
@AcceptanceTest
class ProductAcceptanceTest {

    private static final long 가방가격 = 1_000L;
    private static final long 모자가격 = 2_000L;

    private String 브랜드_식별자;

    @BeforeEach
    void setUp() {
        final var 브랜드_등록_요청 = 브랜드_등록_요청("test", Map.of());
        브랜드_식별자 = 등록_식별자_추출(브랜드_등록_요청);
    }

    /**
     * When 상품을 등록하면
     * Then 상품이 등록된다
     * Then 상품 상세 조회 시 등록한 상품을 찾을 수 있다
     */
    @DisplayName("상품을 등록한다")
    @Test
    void 상품_등록_테스트() {
        // when
        final var 상품_등록_응답 = 상품_등록_요청(브랜드_식별자, Category.BAG, 가방가격);

        // then
        등록요청_성공(상품_등록_응답);

        // then
        상품_상세_조회_시_상품을_찾을_수_있다(등록_식별자_추출(상품_등록_응답), Category.BAG, 가방가격);
    }

    /**
     * Given 상품을 등록하고
     * When 상품을 수정하면
     * Then 상품 상세 조회 시 수정된 상품을 찾을 수 있다
     */
    @DisplayName("상품을 수정한다")
    @Test
    void 상품_수정_테스트() {
        // given
        final var 상품_등록_응답 = 상품_등록_요청(브랜드_식별자, Category.BAG, 가방가격);

        // when
        final var 상품_식별자 = 등록_식별자_추출(상품_등록_응답);
        final var 상품_수정_응답 = 상품_수정_요청(상품_식별자, Category.HAT, 모자가격);

        // then
        수정요청_성공(상품_수정_응답);
        상품_상세_조회_시_상품을_찾을_수_있다(상품_식별자, Category.HAT, 모자가격);
    }

    /**
     * Given 상품을 등록하고
     * When 상품을 삭제하면
     * Then 상품 상세 조회 시 상품이 제거 되어있다
     */
    @DisplayName("상품 삭제한다")
    @Test
    void 상품_삭제_테스트() {
        // given
        final var 상품_등록_응답 = 상품_등록_요청(브랜드_식별자, Category.BAG, 가방가격);

        // when
        final var 상품_식별자 = 등록_식별자_추출(상품_등록_응답);
        final var 상품_삭제_응답 = 상품_삭제_요청(상품_식별자);

        // then
        삭제요청_성공(상품_삭제_응답);
        상품_상세_조회_시_상품을_찾을_수_없다(상품_식별자);
    }

    private void 상품_상세_조회_시_상품을_찾을_수_있다(final String productId, final Category category, final long price) {
        final ProductResponse productResponse = 상품_상세_조회_요청(productId).as(ProductResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(productResponse.getCategory()).isEqualTo(category);
            softly.assertThat(productResponse.getPrice()).isEqualTo(price);
        });
    }

    private void 상품_상세_조회_시_상품을_찾을_수_없다(final String productId) {
        final var res = 상품_상세_조회_요청(productId);

        존재하지_않는_리소스_요청(res);
    }

}
