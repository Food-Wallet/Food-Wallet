# 리뷰 도메인 기능 목록 명세서

- [ ] 회원은 본인의 주문 건에 대해 리뷰를 작성할 수 있다.
  - [ ] 작성할 주문 식별키, 평점, 내용, 첨부 이미지를 입력 받는다.
  - [ ] [예외] 입력 받은 평점이 1이상 5이하의 정수가 아니라면 예외가 발생한다.
- [ ] 사업자 회원은 본인의 매장에 작성된 리뷰에 대해 답글을 작성할 수 있다.
  - [ ] 작성할 답글 내용을 입력 받는다.
  - [ ] [예외] 입력 받은 답글 내용이 빈 문자열이면 예외가 발생한다.
- [ ] 회원은 작성한 리뷰를 조회할 수 있다.
  - [ ] 페이지 번호로 검색한다.
  - [ ] [예외] 페이지 번호가 0 또는 음수라면 예외가 발생한다.
- [ ] 회원은 작성한 리뷰를 삭제할 수 있다.