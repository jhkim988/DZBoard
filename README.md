# DZBoard
기간: 2022.12.05 ~ 2022.12.12

## 회원 기능
- 회원 가입, 상세보기, 정보 수정, 탈퇴
- 로그인, 로그아웃, 회원 권한 처리

## 게시판 기능
- 게시글 작성, 상세보기, 수정, 삭제, 추천/비추천 기능, 게시글 검색
- 카테고리 (공지, 일반, Q&A)

## 관리자
- 회원 목록 조회, 권한 수정, URL 접근 권한, 회원 검색
- 회원 권한 0으로 설정 시 로그인 제한
- URL 권한 설정 -> 서버 재시작 없이도 적용 가능하도록 구현

## 채팅
- 웹 소캣을 이용한 채팅방/채팅 기능

## DB
- 회원/게시글 삭제 시 임시 테이블로 이동 시키는 프로시저 구현(트랜잭션 처리)
- 페이징 쿼리 작성(회원 목록 조회, 게시글 목록 조회)

## 리스너
- ServletContext 생성 시, Repository 에 DataSource 주입

## 필터
- 권한 확인 필터
- 인코딩 필터

### 페이징 기능
1. 게시판 페이징 - 커버링 인덱스를 이용
2. 회원관리 페이징 - no offset 방식
- 수행 속도 차이 측정(게시글 40만건, 약 5배 이상)
