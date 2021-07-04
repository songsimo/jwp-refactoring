## Lombok
- Lombok은 그 강력한 기능만큼 사용상 주의를 요한다.
    - 무분별한 setter 메서드 사용
    - 객체 간에 상호 참조하는 경우 무한 루프에 빠질 가능성
    - [Lombok 사용상 주의점(Pitfall)](https://dooray.com/htmls/guides/markdown_ko_KR.html)
- 이번 과정에서는 Lombok 없이 미션을 진행해 본다.

## 요구사항1
- kitchenpos 패키지의 코드를 보고 키친포스의 요구 사항을 README.md에 작성한다. 미션을 진행함에 있어 아래 문서를 적극 활용한다.
[마크다운(Markdown) - Dooray!](https://dooray.com/htmls/guides/markdown_ko_KR.html)

## 요구사항2
- 정리한 키친포스의 요구 사항을 토대로 테스트 코드를 작성한다. 모든 Business Object에 대한 테스트 코드를 작성한다. @SpringBootTest를 이용한 통합 테스트 코드 또는 @ExtendWith(MockitoExtension.class)를 이용한 단위 테스트 코드를 작성한다.
- Controller에 대한 테스트 코드 작성은 권장하지만 필수는 아니다. 미션을 진행함에 있어 아래 문서를 적극 활용한다.
[Testing in Spring Boot](https://www.baeldung.com/spring-boot-testing)

# 키친포스

## 요구 사항
- 상품
  - [ ] 목록을 조회할 수 있다.
  - [ ] 이름과 가격을 입력받아 등록할 수 있다.
    - [ ] 가격은 0원 이상이어야 한다.

- 메뉴 그룹
  - [ ] 목록을 조회할 수 있다.
  - [ ] 이름을 입력받아 등록할 수 있다.

- 메뉴
  - [ ] 목록을 조회할 수 있다.
  - [ ] 이름, 가격, ```메뉴 그룹```식별자, ```메뉴 상품```목록을 입력받아 등록할 수 있다.
    - [ ] 가격은 0원 이상이어야 한다.
    - [ ] 등록된 ```메뉴 그룹``` 이어야한다.
    - [ ] ```메뉴 상품```목록은 모두 존재하는 ```메뉴 상품```이어야 한다.
    - [ ] 가격은 ```메뉴 상품```목록 가격의 합보다 작거나 같아야 한다.

- 주문
  - [ ] 목록을 조회할 수 있다.
  - [ ] ```주문 테이블```식별자, 상태, 주문시간, ```주문 항목```목록을 입력받아 등록할 수 있다.
    - [ ] ```주문 항목```은 1개 이상이어야 한다.
    - [ ] ```주문 항목```은 ```메뉴```에 존재하고 중복되지않은 ```메뉴```이어야 한다.
    - [ ] ```주문 테이블```은 등록된 ```주문 테이블```이어야 한다.
    - [ ] ```주문```을 생성하면 상태는 '요리중' 이 된다.
  - [ ] ```주문```식별자와 상태를 입력받아 상태를 변경할 수 있다.
    - [ ] 등록된 ```주문```이어야한다.
    - [ ] 상태가 '완료'가 아니어야 한다.

- 단체 지정
  - [ ] 시간과 ```주문 테이블``` 목록을 입력받아 등록할 수 있다.
    - [ ] ```주문 테이블```은 2개 이상이어야 한다.
    - [ ] ```주문 테이블```은 등록되어있고 중복되지않은 ```주문 테이블```어이야 한다.
    - [ ] ```주문 테이블```은 이미 다른 ```단체 지정```에 포함되어있지 않아야 한다.
  - [ ] ```단체 지정```식별자를 입력받아 ```단체 지정```을 해제할 수 있다.
    - [ ] ```주문```의 상태가 ('요리중', '식사중') 이 아니어야 한다.

- 테이블
  - [ ] 목록을 조회할 수 있다.
  - [ ] ```단체 지정```식별자, 손님수, 주문을 등록할 수 없는 주문 테이블 여부를 입력받아 등록할 수 있다.
  - [ ] ```주문 테이블```식별자와 주문을 등록할 수 없는 주문 테이블 여부를 입력받아 주문을 등록할 수 없는 주문 테이블 여부를 변경할 수 있다.
    - [ ] 등록된 ```주문 테이블``` 이어야 한다.
    - [ ] 단체로 지정되지않은 ```주문 테이블``` 이어야 한다.
    - [ ] ```주문``` 상태가 ('요리중', '식사중') 이 아니어야 한다.
  - [ ] ```주문 테이블```식별자와 손님수를 입력받아 손님수를 변경할 수 있다.
    - [ ] 손님수는 0명 이상이어야 한다.
    - [ ] 등록된 ```주문 테이블``` 이어야 한다.
  
## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
