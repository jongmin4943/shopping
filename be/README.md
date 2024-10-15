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
- 브랜드의 카테고리에는 1개의 상품은 존재한다.
    - 카테고리를 추가할 수 있다면, 이미 등록되어 있는 브랜드들의 카테고리 별 가격을 셋팅해줘야 하는 문제가 있다.
    - 카테고리를 추가 / 업데이트 / 삭제 할 수 있다는 요구사항은 없다.
    - 복잡해지는 부분을 간단히 하고 요구사항을 빠르게 만족시키기 위해 카테고리는 고정적이라 가정하고 구현한다.
  ```java
  public enum Category {
      TOP, OUTER, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORY;
  }
- 쇼핑몰에 상품 품절은 없다.
    - 재고 동시성은 다루지 않는다.
- 브랜드를 추가 / 업데이트 / 삭제 할 수 있다
    - 브랜드 추가 시 브랜드 이름과 8가지 카테고리별 상품을 받는다.
        - 이름은 최대 100자이며 유니크해야한다.
        - 상품은 가격만 받는다.
    - 브랜드 업데이트 시 브랜드 이름만 바꿀 수 있다.
    - 브랜드 삭제 시 해당 브랜드와 연관된 상품들을 같이 삭제한다.
    - 브랜드 및 카테고리별 상품은 여러개가 될 수 있다. (ex. A 브랜드의 바지 종류가 여러개)
- 상품을 추가 / 업데이트 / 삭제 할 수 있다.
    - 상품 추가 시 가격과 어떤 브랜드의 어떤 카테고리인지를 받는다.
    - 상품은 가격과 카테고리를 변경할 수 있다.
        - 가격은 0 미만이 될 수 없다.
        - 카테고리 변경 시 해당 브랜드의 카테고리에 상품이 0개가 될 수 없다.
    - 상품을 삭제할 수 있다.
        - 해당 브랜드의 카테고리에 상품이 0개가 될 수 없다.
- 카테고리 별 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있다.
    - 각 브랜드별로 카테고리의 상품은 여러개이다. 그 중에 최저 가격인 상품을 찾아야 한다.
- 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있다.
    - 해당 브랜드가 가진 카테고리별 상품들의 최저가격을 구해야 한다.
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
    private long price;
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
    private long price;
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
