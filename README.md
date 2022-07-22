# ToyProject-WebBoard-SpringBoot
Springboot + security,JPA + HeidiSQL(MariaDB,MySQL) + BootStrap 웹게시판

<img src="https://user-images.githubusercontent.com/86394597/180389566-60dddd57-7c70-4d13-8027-9b4d729bfd88.JPG">

## ⅰ. 프로젝트 소개 (요구사항)
### 1. 프로젝트 소개
CRUD API 설계는 기본적으로 웹 게시판이 가장 적절하다고 생각하여 웹 게시판 프로젝트를 만들게 되었습니다. 또한 spring을 활용하여 스프링에 대한 이해를 높이고자 시작하게 되었습니다.
Frontend는 BootStrap을 사용한 유튜브를 참고하였고, 이에 따라, Thymeleaf 및 Script, html, css 등은 필요한 부분을 공부하며 추가하였고, Backend의 기능들을 추가하다보니 더욱 흥미를 느껴 관련 문서, 구글링을 통해 원하는 기능을 구현해나갔습니다. OAuth2를 활용한 SNS로그인과 댓글 기능은 아직 진행 중에 있습니다.

<br>

### 2. 프로젝트 기능

[게시판]
- CRUD 기능 (하단의 API 설계 참조)
- 조회수
- 페이징 및 검색 처리
- 페이징 블록처리
- JpaAduiding 작성/수정시간
- FileUpload
- 업로드 파일 다운로드
- 댓글(개발중)
- 작성자 본인, 관리자 권한이 아니라면 수정, 삭제 API 제한

[관리자]
- 회원 전체목록 조회
- 회원 검색
- ROLE_ADMIN, ROLE_USER 권한에 따른 수정, 삭제 API 제한 및 UI 차이

[회원]
- Security 회원가입 및 로그인
- 회원가입시 유효성 @Valid 검사 및 중복 검사
- JavaMailSender 회원가입시 인증번호 메일 발송 및 검사
- 마이페이지(나의회원정보)
- 내 게시글
- 회원정보수정(비밀번호변경)
- 회원탈퇴
- OAuth 2.0 구글, 네이버 로그인 (개발중)

<br>

### 3. 개발 환경
 
[Backend]
- IDE(통합개발환경) : IntelliJ Ultimate Edition
- 개발 언어 : Java 11.0.9
- 프레임워크 : Spring Boot 2.6.7
- Build : Maven
- Spring Security
- OAuth 2.0
 
[DataBase]
- DB : MariaDB 10.6.7, MySQL Workbench 8.0 CE
- DB-GUI-Tool : HeidiSQL 11.3.0
- DB 접근 기술(ORM) : Spring Data JPA

[Frontend]
- Thymeleaf
- Bootstrap
- Html/Css
- JavaScript
 
<br>

## ⅱ. 구조 및 설계 (설계)
### 1. 패키지 구조
<details>
<summary>패키지 구조 보기</summary>   
 

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂springboard
 ┃ ┃ ┃ ┃ ┗ 📂webboard
 ┃ ┃ ┃ ┃ ┃ ┣ 📂adapter
 ┃ ┃ ┃ ┃ ┃ ┃  ┗ 📜UserAdapter.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetails.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MethodSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SessionUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AccountController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardApiController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HomeController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserApiController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardViewDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PasswordForm.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Board.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Role.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TimeEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomizedUserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomizedUserRepositoryImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂serivce
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ConsoleMailSender.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂validator
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BoardValidator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserValidator.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜WebboardApplication.java
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┣ 📜join.css
 ┃ ┃ ┃ ┃ ┣ 📜signin.css
 ┃ ┃ ┃ ┃ ┗ 📜stater-template.css
 ┃ ┃ ┃ ┣ 📂files
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┣ 📜checked-email.html
 ┃ ┃ ┃ ┃ ┣ 📜login.html
 ┃ ┃ ┃ ┃ ┗ 📜register.html
 ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┣ 📜boardview.html
 ┃ ┃ ┃ ┃ ┣ 📜form.html
 ┃ ┃ ┃ ┃ ┣ 📜list.html
 ┃ ┃ ┃ ┃ ┗ 📜modify.html
 ┃ ┃ ┃ ┣ 📂fragment
 ┃ ┃ ┃ ┃ ┗ 📜common.html
 ┃ ┃ ┃ ┣ 📂message
 ┃ ┃ ┃ ┃ ┗ 📜message.html
 ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┣ 📜myboardlist.html
 ┃ ┃ ┃ ┃ ┣ 📜mylogin.html
 ┃ ┃ ┃ ┃ ┣ 📜mypage.html
 ┃ ┃ ┃ ┃ ┣ 📜password.html
 ┃ ┃ ┃ ┃ ┗ 📜userlist.html
 ┃ ┃ ┃ ┗ 📜index.html
 ┃ ┃ ┗ 📜application.properties
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂springboard
 ┃ ┃ ┃ ┃ ┗ 📂webboard
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AccountControllerTest.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂dd
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityConfigTest.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜dd.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜BoardApplicationTests.java
 ```

 </details>   
<br>

### 2. API 설계

![메인_화면_API_(HomeController)](https://user-images.githubusercontent.com/86394597/179543694-c9df5462-9472-43ab-8704-eaddade81205.JPG)


![계정_관련_API_(AccountController)](https://user-images.githubusercontent.com/86394597/179543697-4e9c4c32-4f52-419f-b773-eaec2695310e.JPG)


![회원_관련_API_(UserController)](https://user-images.githubusercontent.com/86394597/179543701-f8b36d4e-e731-47eb-bf3b-ef56157f34c9.JPG)


![회원_관련_API_(UserApiController)](https://user-images.githubusercontent.com/86394597/179543704-8619a487-11af-4bd8-a5b6-338bd18ca03f.JPG)


![게시판_관련_API_(BoardController)](https://user-images.githubusercontent.com/86394597/179543709-f106088d-94ad-4046-983e-51cca69ed2e4.JPG)


![게시판_관련_API_(BoardApiController)](https://user-images.githubusercontent.com/86394597/179543711-26b04c7e-7655-464b-9749-16b41df65aec.JPG)

<br>

### 3. DB 설계

![스프링 웹프로젝트 DB ERD](https://user-images.githubusercontent.com/86394597/180385082-70d1b417-332c-41f3-bfce-143752372d9d.JPG)

![Board](https://user-images.githubusercontent.com/86394597/180385062-17a4724a-a58b-4e4a-8888-98bc7ea55581.JPG)

![User](https://user-images.githubusercontent.com/86394597/180385070-4dc0c9db-f839-4fc6-8f05-adfbc2b90ca1.JPG)

![Role](https://user-images.githubusercontent.com/86394597/180385069-e73badab-e55e-4f7e-b627-7a3dd4897f18.JPG)

![User_Role](https://user-images.githubusercontent.com/86394597/180385071-a6916e27-1a07-45fa-9e45-e68aa728b76d.JPG)

![Reply](https://user-images.githubusercontent.com/86394597/180385065-da5a28b0-725b-443a-86de-6db83e0a31ef.JPG)

<br>

## ⅲ. 실행 화면 (구현)

### 메인화면

![1](https://user-images.githubusercontent.com/86394597/180411549-042e6585-917c-4919-aa68-f01b6a7a42cd.JPG)

### 기능 GIF (아래 보기 클릭)
<details>
<summary>로그인 로그아웃 GIF 보기</summary>
 
![1 로그인, 로그아웃](https://user-images.githubusercontent.com/86394597/180460125-dcff68fe-7418-4d53-adc6-cfa824763396.gif)
- SpringSecurity 로그인, 로그아웃
- OAuth2 SNS Naver, Kakao 로그인 (개발중)

 <br>

 ![관리자](https://user-images.githubusercontent.com/86394597/180470092-241aa1e7-d540-403d-8fa0-7733e028d8e0.JPG)

 ![유저](https://user-images.githubusercontent.com/86394597/180470111-b424aeaf-f7a8-478a-9c0d-4312eb08028e.jpg)

- ROLE_ADMIN, ROLE_USER 권한 차이 설정 (API제한, UI)
- Header 부분에 권한 표시
- 관리자의 경우, Header 상단 메뉴에 회원목록
</details>

<br>

<details>
<summary>회원가입 + 메일 인증 GIF 보기</summary>
 
![2](https://user-images.githubusercontent.com/86394597/180460135-eeb480b2-3889-4545-953c-4aaadc3d3e2f.gif)
- @Valid를 이용한 Form 검증
- 입력 규칙이 틀리면, Error를 화면에 표시 
- JavaMailSender를 이용한 이메일 인증
- 사용자가 입력한 이메일로 5자리 인증코드 전송
- 메일 정상발송 시, 알림창 표시 후 인증번호 입력칸 활성화
- 인증코드 일치 시, 알림창 표시 후 입력한 이메일 수정 불가능 하도록 비활성화
- 인증코드 일치 시, 가입하기 버튼 활성화
- 회원가입 성공 시, 알림창 
</details>

<br>

<details>
<summary>회원가입 @Valid 검사 GIF 보기</summary>

![3](https://user-images.githubusercontent.com/86394597/180460139-4d0770ed-115c-41c9-8d1d-809f5687002d.gif)
- @Valid를 이용한 Form 검증
- 입력 규칙이 틀리면, Error를 화면에 표시  
</details>

<br>

<details>
<summary>게시판 리스트 페이징 및 검색 GIF 보기</summary>
 
![4  게시판리스트 화면](https://user-images.githubusercontent.com/86394597/180460144-f75fde25-302b-47a4-b5bf-b4adfc982033.gif)
- 제목이나 내용을 포함하는 키워드로 게시물 검색 기능
- 게시글 조회수 기능
- 게시판 리스트 작성시간 수정시간 기능 추가 JpaAuditing
- DB에 등록된 게시글을 최근 작성시간/수정시간 기준 정렬
- 아래 페이징처리된 버튼을 통해 분할처리된 페이지들에 등록된 게시글을 확인 가능, 현재 페이지는 회색 표기(게시글 리스트 페이징처리 기능)
- 게시글 리스트 페이지 Paging 처리
- Paging 블록 처리 5단위로 넘어가도록 처리
</details>
 
<br>

<details>
<summary>게시글 조회수 증가 GIF 보기</summary>
 
![5,6  게시글 조회수 증가](https://user-images.githubusercontent.com/86394597/180460148-2288f103-2261-43f3-b125-7d0ca5b1dd61.gif)
- 게시글 클릭 시, 조회수 증가
</details>

<br>

<details>
<summary>게시글 상세 페이지 GIF 보기</summary>
 
![5,6  게시글상세](https://user-images.githubusercontent.com/86394597/180460149-52736384-04e2-4ade-b352-522b2865ec5e.gif)
- 수정폼, 상세글, 작성폼 모듈 분리 및 JpaAuditing(작성,수정시간)
- FileUpload 기능
- 조회수 기능
- 업로드된 파일 상세페이지에 출력
- 업로드한 이미지 출력 및 다운
- 댓글 기능 (개발중)
 
<br>
 
- 파일다운 버튼 클릭 시, 이미지를 저장 가능한 페이지로 이동
- 뒤로가기 버튼 클릭 시, 게시글 리스트 페이지로 이동
 
<br>
 
- 수정 버튼 클릭 시, 수정권한체크(작성자 본인 OR 관리자 권한)
- 수정 성공 시, 완료 알림창을 띄운 후 게시글 리스트 페이지로 이동
- 수정 실패 시, 권한 에러 알림창을 띄운 후 해당 게시글로 리다이렉트
 
<br>
 
- 삭제 버튼 클릭 시, 삭제권한체크(작성자 본인 OR 관리자 권한)
- 삭제 성공 시, 완료 알림창을 띄운 후 게시글 리스트 페이지로 이동
- 삭제 실패 시, 권한 에러 알림창을 띄운 후 해당 게시글로 리다이렉트
</details>
 
<br>

<details>
<summary>게시글 작성 @Valid 및 파일 업로드 GIF 보기</summary>
 
![7 작성](https://user-images.githubusercontent.com/86394597/180460152-f335cfa4-3002-48f8-a8c6-df0da2bbb848.gif)
- Validator를 이용한 Form 검증
- 입력 규칙이 틀리면, Error를 화면에 표시 
- 파일 선택 버튼 클릭 시, 파일 업로드 가능 (파일 업로드 기능)
- 확인 버튼 클릭 시, 등록완료 알림창을 띄운 후 게시글 리스트 페이지로 이동
- 취소 버튼 클릭 시, 게시글 리스트로 이동
</details>
 
<br>

<details>
<summary>게시글 작성 결과 GIF 보기</summary>
 
![7 작성결과](https://user-images.githubusercontent.com/86394597/180460154-36c8a4c3-9157-422f-8804-af4dace08d06.gif)
 
</details>

<br>

<details>
<summary>게시글 수정 @Valid 검사 및 성공 GIF 보기</summary>
 
![8](https://user-images.githubusercontent.com/86394597/180460155-30ec2444-f4d1-4571-be88-edd25ef431f3.gif)
- Validator를 이용한 Form 검증
- 입력 규칙이 틀리면, Error를 화면에 표시 
- 수정 버튼을 클릭하여 수정폼에 들어오면, 원문 게시글의 내용을 미리 수정폼에 위치시켜둔다.
- 파일 업로드 기능
- 확인 버튼 클릭 시, 수정완료 알림창을 띄운 후 게시글 리스트 페이지로 이동 (게시글 수정메세지 기능)
- 취소 버튼 클릭 시, 이전 게시글로 재이동
</details>
 
<br>

<details>
<summary>게시글 수정 실패 (관리자 권한 OR 작성자 본인이 아니면 수정 불가) GIF 보기</summary>
 
![9](https://user-images.githubusercontent.com/86394597/180460156-e881948a-482f-462d-b0fb-610efc091462.gif)
 
</details>
 
<br>

<details>
<summary>게시글 삭제 성공 GIF 보기</summary>
 
![10](https://user-images.githubusercontent.com/86394597/180460158-37ce9a6a-6a18-4d99-ba14-d8febd3a01d2.gif)
- 삭제 버튼 클릭 시, 삭제완료 알림창을 띄운 후 게시글 리스트 페이지로 이동 
</details>
 
<br>

<details>
<summary>게시글 삭제 실패 (관리자 권한 OR 작성자 본인이 아니면 삭제 불가) GIF 보기</summary>
 
![11](https://user-images.githubusercontent.com/86394597/180460160-40d5e5dd-1181-42d9-8cf5-3b48a8c5d0eb.gif)

</details>
 
<br>

<details>
<summary>관리자 메뉴 - 회원 목록 및 회원 검색 GIF 보기</summary>
 
![12](https://user-images.githubusercontent.com/86394597/180460162-e0886e37-344e-4ce1-b39e-ef50dac9069c.gif)
- 관리자 메뉴
- 키워드를 포함하는 아이디를 가진 회원 검색 기능
- 전체 회원 수 표시
- 회원 가입일 JpaAuditing 표시
</details>
 
<br>

<details>
<summary>마이페이지 입장 전 로그인 재검증 GIF 보기</summary>

![13](https://user-images.githubusercontent.com/86394597/180460164-f7d5c5bb-4392-49f6-a83d-664ef14deeda.gif)
- 현재 로그인한 사용자의 비밀번호를 재검증
- 현재 로그인한 사용자의 회원 정보
- 해당 로그인한 사용자가 작성한 게시글 리스트
- 비밀번호 변경기능
- 회원 탈퇴 기능
</details>
 
<br>

<details>
<summary>마이페이지 - 내 작성글 GIF 보기</summary>
 
![14](https://user-images.githubusercontent.com/86394597/180460167-96207d0d-9680-4227-b297-a35c15bd625b.gif)
- 제목이나 내용을 포함하는 키워드로 게시물 검색 기능
- 전체 게시물 수 표시
- 게시글 조회수 기능
- 게시판 리스트 작성시간 수정시간 기능 추가 JpaAuditing
- DB에 등록된 게시글을 최근 작성시간/수정시간 기준 정렬
- 아래 페이징처리된 버튼을 통해 분할처리된 페이지들에 등록된 게시글을 확인 가능, 현재 페이지는 회색 표기(게시글 리스트 페이징처리 기능)
- 게시글 리스트 페이지 Paging 처리
- Paging 블록 처리 5단위로 넘어가도록 처리
</details>
 
<br>

<details>
<summary>비밀번호 변경 @Valid 검증 GIF 보기</summary>
 
![15](https://user-images.githubusercontent.com/86394597/180460169-f9113220-eab0-46a9-bc81-9cd879a1af4c.gif)
- @Valid를 이용한 Form 검증
- 입력 규칙이 틀리면, Error를 화면에 표시 
</details>
 
<br>

<details>
<summary>비밀번호 변경 - 현재 로그인 계정 비밀번호 불일치 에러 GIF 보기</summary>
- 현재 로그인한 사용자의 비밀번호가 불일치 시 에러 알림 
![16](https://user-images.githubusercontent.com/86394597/180460173-be8034ac-368e-4378-85ec-1cce9b1b6a31.gif)
 
</details>
  
<br>

<details>
<summary>비밀번호 변경 - 현재 비밀번호와 새 비밀번호 일치 에러 GIF 보기</summary>
 
![17](https://user-images.githubusercontent.com/86394597/180460174-087d7dc8-cafd-435c-b2ae-bf3912d64cba.gif)
- 현재 로그인한 사용자의 비밀번호와 새 비밀번호가 일치 시 에러 알림
</details>
  
<br>

<details>
<summary>비밀번호 변경 - 새 비밀번호 재확인 불일치 GIF 보기</summary>
- 새 비밀번호와 새 비밀번호 재확인이 불일치 시 에러 알림
![18](https://user-images.githubusercontent.com/86394597/180460176-57564c98-4a94-4013-ae2f-475af3e9b6ed.gif)
 
</details>
  
<br>

<details>
<summary>비밀번호 변경 완료 GIF 보기</summary>
 
![19](https://user-images.githubusercontent.com/86394597/180460178-512a5f06-e219-4755-b9be-98ed82f04957.gif)
- 로그아웃 후 변경된 비밀번호로 정상 로그인이 된다.
</details>
 
<br>

<details>
<summary>회원 탈퇴 및 자동 로그아웃 GIF 보기</summary>

![20](https://user-images.githubusercontent.com/86394597/180460181-fc9c3f41-a846-4eb0-b6c9-691172d9b3f9.gif)
- 회원 탈퇴 시, 작성한 게시글 전체 삭제
- 회원 탈퇴 시, 그 즉시 해당 아이디 자동 로그아웃
</details>
 
<br>

## ⅳ. 테스트 (테스트)
- 정리 후 업데이트 예정


<br>

## ⅴ. 후기

### 1. 프로젝트 보완사항

### 2. 느낀 점 











