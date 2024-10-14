package com.min.shopping.brand.acceptance;

import com.min.shopping.brand.application.dto.BrandResponse;
import com.min.shopping.common.Category;
import com.min.shopping.core.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.min.shopping.brand.acceptance.steps.BrandSteps.브랜드_등록_요청;
import static com.min.shopping.brand.acceptance.steps.BrandSteps.브랜드_목록_조회_요청;
import static com.min.shopping.brand.acceptance.steps.BrandSteps.브랜드_삭제_요청;
import static com.min.shopping.brand.acceptance.steps.BrandSteps.브랜드_수정_요청;
import static com.min.shopping.common.Category.ACCESSORY;
import static com.min.shopping.common.Category.BAG;
import static com.min.shopping.common.Category.HAT;
import static com.min.shopping.common.Category.OUTER;
import static com.min.shopping.common.Category.PANTS;
import static com.min.shopping.common.Category.SNEAKERS;
import static com.min.shopping.common.Category.SOCKS;
import static com.min.shopping.common.Category.TOP;
import static com.min.shopping.core.AcceptanceTestUtils.등록_식별자_추출;
import static com.min.shopping.core.AcceptanceTestUtils.등록요청_성공;
import static com.min.shopping.core.AcceptanceTestUtils.삭제요청_성공;
import static com.min.shopping.core.AcceptanceTestUtils.수정요청_성공;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("브랜드 관련 기능")
@AcceptanceTest
class BrandAcceptanceTest {

    private static final String 브랜드이름 = "브랜드";
    private static final String 수정된_브랜드이름 = "수정된 브랜드";
    private static final Map<Category, Long> 카테고리별_최초_가격 = Map.of(
            TOP, 10_000L,
            OUTER, 5_000L,
            PANTS, 4_000L,
            SNEAKERS, 9_000L,
            BAG, 2_000L,
            HAT, 1_000L,
            SOCKS, 500L,
            ACCESSORY, 3_500L
    );

    /**
     * When 브랜드를 등록하면
     * Then 브랜드가 등록된다
     * Then 브랜드 목록 조회 시 등록한 브랜드를 찾을 수 있다
     */
    @DisplayName("브랜드를 등록한다")
    @Test
    void 브랜드_등록_테스트() {
        // when
        final var 브랜드_등록_응답 = 브랜드_등록_요청(브랜드이름, 카테고리별_최초_가격);

        // then
        등록요청_성공(브랜드_등록_응답);
        브랜드_등록_요청이_성공한다(브랜드_등록_응답);

        // then
        브랜드_목록_조회_시_브랜드를_찾을_수_있다(브랜드이름);
    }

    /**
     * Given 브랜드를 등록하고
     * When 브랜드를 수정하면
     * Then 브랜드 목록 조회 시 수정된 브랜드를 찾을 수 있다
     */
    @DisplayName("브랜드를 수정한다")
    @Test
    void 브랜드_수정_테스트() {
        // given
        final var 브랜드_등록_응답 = 브랜드_등록_요청(브랜드이름, 카테고리별_최초_가격);

        // when
        final var 브랜드_수정_응답 = 브랜드_수정_요청(등록_식별자_추출(브랜드_등록_응답), 수정된_브랜드이름);

        // then
        수정요청_성공(브랜드_수정_응답);
        브랜드_목록_조회_시_브랜드를_찾을_수_있다(수정된_브랜드이름);
    }

    /**
     * Given 브랜드를 등록하고
     * When 브랜드를 삭제하면
     * Then 브랜드 목록 조회 시 브랜드가 제거 되어있다
     */
    @DisplayName("브랜드 삭제한다")
    @Test
    void 브랜드_삭제_테스트() {
        // given
        final var 브랜드_등록_응답 = 브랜드_등록_요청(브랜드이름, 카테고리별_최초_가격);

        // when
        final var 브랜드_삭제_응답 = 브랜드_삭제_요청(등록_식별자_추출(브랜드_등록_응답));

        // then
        삭제요청_성공(브랜드_삭제_응답);
        브랜드_목록_조회_시_브랜드를_찾을_수_없다(브랜드이름);
    }

    private void 브랜드_등록_요청이_성공한다(final ExtractableResponse<Response> response) {
        final BrandResponse brandResponse = response.as(BrandResponse.class);
        assertThat(brandResponse.getName()).isEqualTo(브랜드이름);
    }

    private void 브랜드_목록_조회_시_브랜드를_찾을_수_있다(final String name) {
        final List<String> brandNames = 브랜드_목록_조회_요청()
                .jsonPath()
                .getList("name", String.class);


        assertThat(brandNames).containsAnyOf(name);
    }

    private void 브랜드_목록_조회_시_브랜드를_찾을_수_없다(final String name) {
        final List<String> brandNames = 브랜드_목록_조회_요청()
                .jsonPath()
                .getList("name", String.class);

        assertThat(brandNames).doesNotContain(name);
    }

}
