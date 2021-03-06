# 키친포스

## 요구 사항 (엔드 포인트 별)

### MenuGroupRestController
* 메뉴 그룹 생성 기능
* 메뉴 그룹 조회 기능

### MenuRestController
* 메뉴 생성 기능
  * 가격은 0원 이하일 수 없음
  * 메뉴 그룹이 존재하지 않으면 등록할 수 없음 (특정 메뉴 그룹에 속해 있어야 함)
  * 메뉴 내의 각 제품 가격 * 수량을 모두 합산한 것 보다 메뉴의 가격이 크다면 등록할 수 없음
* 메뉴 조회 기능
* 제품들을 저장, 해당 메뉴와 매핑

### OrderRestController
* 주문 생성 기능
  * 생성 요청시 상품을 같이 주문하지 않는다면 생성 불가
  * 요청한 모든 주문 상품들이 메뉴에 모두 등록되어 있지 않다면 생성 불가
  * 존재하지 않는(비어있는) 주문 테이블이라면 생성 불가
* 주문 조회 기능
* 주문 상태 변경 기능
  * 해당 주문이 존재하지 않는 경우 변경 불가
  * 이미 완료된 상태라면 변경 불가

### ProductRestController
* 상품 생성 기능
  * 상품의 가격이 0원 이하라면 제품 생성 불가
* 상품 조회 기능

### TableGroupRestController
* 테이블 그룹 생성(단체 지정) 기능
  * 생성 요청시 주문 테이블 크기가 2이하라면 생성 불가
  * 생성 요청한 주문 테이블이 다른 그룹 테이블에 존재한다면(중복으로 단체 지정) 생성 불가
* 테이블 그룹 해제(단체 지정 해지) 기능
  * 해당 주문 테이블의 주문 상태가 조리중, 식사중인 경우 해지 불가

### TableRestController
* 주문 테이블 생성 기능
* 주문 테이블 조회 기능
* 비어있는 테이블 설정 기능
  * 유효하지 않은 주문 테이블이라면 설정 불가
  * 해당 주문 테이블이 테이블 그룹에 속해있다면 설정 불가
  * 주문 테이블이 조리, 식사중인 경우 해지 불가
* 게스트의 수 변경(또는 등록) 기능
  * 요청한 게스트의 수가 0 이하라면 변경 불가
  * 요청한 주문 테이블이 존재하지 않는(빈 테이블) 경우 변경 불가

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | kitchenpos.application.product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | kitchenpos.application.menu group | 메뉴 묶음, 분류 |
| 메뉴 | kitchenpos.application.menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | kitchenpos.application.menu kitchenpos.application.product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | kitchenpos.application.order kitchenpos.application.table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty kitchenpos.application.table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | kitchenpos.application.order | 매장에서 발생하는 주문 |
| 주문 상태 | kitchenpos.application.order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | kitchenpos.application.table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | kitchenpos.application.order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
