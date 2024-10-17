# Shopping

## 요구사항 정리

다음 8개의 카테고리에서 상품을 하나씩 구매하여, 코디를 완성하는 서비스

1. 상의
2. 아우터
3. 바지
4. 스니커즈
5. 가방
6. 모자
7. 양말
8. 액세서리

- 구매 가격 외의 추가 비용은 계산하지 않는다.
- 브랜드의 카테고리에는 최소 1개의 상품은 존재한다.
    - 카테고리를 추가할 수 있다면, 이미 등록된 브랜드들의 카테고리별 가격을 셋팅해줘야 하는 문제가 있다.
    - 카테고리를 추가 / 업데이트 / 삭제 할 수 있다는 요구사항은 없다.
    - 복잡해지는 부분을 간단히 하고 요구사항을 빠르게 만족시키기 위해 카테고리는 고정적이라 가정하고 구현한다.
  ```java
  public enum Category {
      TOP, OUTER, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORY;
  }
- 쇼핑몰에 상품 품절은 없다.
    - 재고 동시성은 다루지 않는다.
- 브랜드를 추가 / 업데이트 / 삭제 할 수 있다
    - 브랜드 추가 시 브랜드 이름을 받는다.
        - 이름은 최대 100자이며 유니크해야한다.
    - 브랜드 업데이트 시 브랜드 이름만 바꿀 수 있다.
- 상품을 추가 / 업데이트 / 삭제 할 수 있다.
    - 상품 추가 시 가격과 어떤 브랜드의 어떤 카테고리인지를 받는다.
    - 상품은 가격과 카테고리를 변경할 수 있다.
        - 가격은 0 미만이 될 수 없다.
    - 상품을 삭제할 수 있다.
        - 해당 브랜드의 카테고리에 상품이 0개가 될 수 없다.
    - 상품 추가 / 업데이트 / 삭제 시 브랜드가 카테고리별 상품을 최소 1개 이상을 가지고 있다면 ACTIVE 상태로, 아니면 INACTIVE 상태로 바꾼다.
    - 브랜드 및 카테고리별 상품은 여러개가 될 수 있다. (ex. A 브랜드의 바지 종류가 여러개)
- 집계
    - 집계는 브랜드가 카테고리별 상품을 최소 1개이상 가지고 있을 때만 포함한다. (ACTIVE 상태)
    - 카테고리 별 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있다.
    - 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있다.
    - 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있다.

### 브랜드

- Feature: 브랜드 등록

> Scenario: 신규 브랜드를 등록함<br>
> When 브랜드를 등록하면<br>
> Then 브랜드 목록 조회 시 등록한 브랜드를 찾을 수 있다<br>

```java
// POST /brands
public class BrandCreateRequest {
    private String name;
    private Map<Category, Long> productPrices;
}
```

- Feature: 브랜드 수정

> Scenario: 브랜드를 수정함<br>
> Given 브랜드를 등록하고<br>
> When 브랜드를 수정하면<br>
> Then 브랜드의 목록 조회 시 해당 브랜드가 수정되어 있다<br>

```java
// PUT /brands/{id}
public class BrandModifyRequest {
    private String name;
}
```

- Feature: 브랜드 삭제

> Scenario: 브랜드를 삭제함<br>
> Given 브랜드를 등록하고<br>
> When 브랜드를 삭제하면<br>
> Then 브랜드의 목록 조회 시 해당 브랜드를 찾을 수 없다<br>

```java
// DELETE /brands/{id}
```

### 상품

- Feature: 상품 등록

> Scenario: 상품을 등록함<br>
> When 상품을 등록하면<br>
> Then 상품 상세 조회 시 등록한 상품이 조회된다<br>

```java
// POST /products
public class ProductCreateRequest {
    private Long brandId;
    private Category category;
    private BigDecimal price;
}
```

- Feature: 상품 상세 조회

> Scenario: 상품 상세를 조회함<br>
> Given 상품을 등록하고<br>
> When 상품을 상세 조회하면<br>
> Then 등록된 상품의 상세가 조회된다<br>

```java
// get /products/{id}
```

- Feature: 상품 수정

> Scenario: 상품을 수정함<br>
> Given 상품을 등록하고<br>
> When 상품을 수정하면<br>
> Then 상품 상세 조회 시 수정 된 상품이 조회된다<br>

```java
// PUT /products/{id}
public class ProductModifyRequest {
    private Category category;
    private BigDecimal price;
}
```

- Feature: 브랜드 삭제

> Scenario: 상품을 삭제함<br>
> Given 상품을 등록하고<br>
> When 상품을 삭제하면<br>
> Then 상품 상세 조회 시 조회할 수 없다는 에러가 발생한다<br>

```java
// DELETE /products/{id}
```

- Feature: 카테고리별 최저가격 브랜드 상품 조회

> Scenario: 카테고리별 최저가격 브랜드 상품 조회을 조회함<br>
> Given 초기 데이터를 셋팅하고<br>
> When 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 조회하면<br>
> Then 결과를 확인할 수 있다<br>

```java
// GET /products/lowest-prices
```

- Feature: 단일 브랜드 전체 카테고리 최저가격 조회

> Scenario: 단일 브랜드 전체 카테고리 최저가격 조회을 조회함<br>
> Given 초기 데이터를 셋팅하고<br>
> When 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 조회하면<br>
> Then 결과를 확인할 수 있다<br>

```java
// GET /products/lowest-prices-for-single-brand
```

- Feature: 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회

> Scenario: 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회함<br>
> Given 초기 데이터를 셋팅하고<br>
> When 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 조회하면<br>
> Then 결과를 확인할 수 있다<br>

```java
// GET /products/price-range/{category}
```

## 코드 테스트

- 브랜드 등록/수정/삭제 관련 테스트 -> BrandAcceptanceTest
- 상품 등록/수정/삭제 관련 테스트 -> ProductAcceptanceTest
- 상품 집계 관련 테스트 -> ProductPriceAcceptanceTest

## 실행 방법

- ShoppingApplication 를 실행합니다.
- 요구사항 1번 -> http://localhost:8080/products/lowest-prices
- 요구사항 2번 -> http://localhost:8080/products/lowest-prices-for-single-brand
- 요구사항 3번 -> http://localhost:8080/products/price-range/TOP
    - `TOP` 은 다른 카테고리로 변경가능합니다.

## 추가정보

### 구현 시 고려 했던 점

> 단, 구매 가격 외의 추가적인 비용(예, 배송비 등)은 고려하지 않고,
> 브랜드의 카테고리에는 1개의 상품은 존재하고,
> 구매를 고려하는 모든 쇼핑몰에 상품 품절은 없다고 가정합니다.

`브랜드의 카테고리에는 1개의 상품은 존재하고` 가 고민이 들었습니다.
저는 구현 시 참조흐름의 방향을 고민하고 단방향 흐름을 지향합니다. 그래서 설계를 브랜드는 상품을 모른다고 가정했습니다.
그 이유로는 브랜드를 조회할때 상품을 조회하는 케이스보단, 상품을 조회할때 어떤 브랜드의 것인지 조회하는 경우가 많다고 생각했습니다.
또한 브랜드는 여러 상품을 가질 수 있을 것 같은데, 브랜드가 상품을 LIST 로 가지고 있게 되면 성능상 이점도 많이 떨어진다 생각했습니다.

그런데 요구사항 중 브랜드를 추가 할 수 있었고 새로 추가된 브랜드는 상품이 등록되어있지 않습니다. 그럴때, 요구사항 2번
`단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회` 가 마음에 걸렸습니다.
만약 등록된 브랜드가 상품을 모든 카테고리별로 가지지 않는다면, 어떻게 하지? 라는 고민이 이어졌습니다.

그래서 요구사항에 있는 `브랜드의 카테고리에는 1개의 상품은 존재하고` 를 좀 더 중요하게 보고, 브랜드가 카테고리별 상품을 최소 하나이상
가지고 있지 않는다면, 집계쪽에서 아에 제외를 하는 방향으로 잡았습니다.

다른 고민은 쿼리 성능이었습니다. JPA 로는 다양한 기능을 통한 쿼리를 만들기 어렵고, 만들더라도 성능의 이점을 가지기 어렵습니다.
그래서 집계쿼리쪽은 `JdbcTemplate` 를 이용해 원하는 결과를 뽑게끔 만들었습니다.
약간은 복잡해진다고 생각이 들면 With 를 이용하거나, 어플리케이션에서 조합하는 형식으로 만들어보았습니다.

Product 에 (brandId, category) 복합인덱스와 (category, price) 복합인덱스를 걸었습니다.
(brandId, category) 복합인덱스로 설정한 것은 브랜드별, 카테고리의 필터이나 그룹핑이 자주 일어나기 때문입니다.
(category, price) 복합인덱스는 카테고리별로 그룹화하고 가격별로 정렬하는 케이스가 있어 걸었습니다.

마지막으로, 코드를 읽기 쉽게 깔끔하게 적고 싶었습니다. 근데 집계쪽과 `브랜드의 카테고리에는 1개의 상품은 존재` 라는 요구사항으로
인해 조금은 이해하기 어려워지지 않았나 생각이 듭니다. 집계관련으로 인해 의존방향이 조금은 제가 생각했던 단방향 깔끔한 흐름이
아니라고 느껴집니다.

### 추가하고 싶거나 고민이 드는점

상품을 등록,수정,삭제 할때 브랜드의 활성화 여부 부분에 동시성 이슈가 발생할 수 있을 것 같습니다. 해당 문제를 해결하기 위해 Lock 을
고려해봐야 할 것 같습니다.

실패케이스 테스트 작성을 소홀히 했습니다. 더 많은 테스트 케이스를 작성해야 할 것 같습니다.

상품을 대량으로 넣어서 성능테스트를 해보고 싶습니다.
