# ToyProject-WebBoard-SpringBoot
Springboot + security,JPA + HeidiSQL(MariaDB,MySQL) + BootStrap 웹게시판

<img src="https://user-images.githubusercontent.com/86394597/180389566-60dddd57-7c70-4d13-8027-9b4d729bfd88.JPG">

## 프로젝트 소개
### 1. 프로젝트 소개
CRUD API 설계는 기본적으로 웹 게시판이 가장 적절하다고 생각하여 웹 게시판 프로젝트를 만들게 되었습니다. 또한 spring을 활용하여 스프링에 대한 이해를 높이고자 시작하게 되었습니다.
Frontend는 BootStrap을 사용한 유튜브를 참고하였고, 이에 따라, Thymeleaf 및 Script, html, css 등은 필요한 부분을 공부하며 추가하였고, Backend의 기능들을 추가하다보니 더욱 흥미를 느껴 관련 문서, 구글링을 통해 원하는 기능을 구현해나갔습니다. OAuth2를 활용한 SNS로그인과 댓글 기능은 아직 진행 중에 있습니다.

<br>

### 2. 프로젝트 기능
<details>
<summary>프로젝트 기능 보기</summary>   
 
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

</details>

<br>

### 3. 개발 환경
<details>
<summary>개발환경 보기</summary>   
 
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
</details>
 
<br>

## 구조 및 설계
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
<details>
<summary>API 설계 보기</summary>

![메인_화면_API_(HomeController)](https://user-images.githubusercontent.com/86394597/179543694-c9df5462-9472-43ab-8704-eaddade81205.JPG)


![계정_관련_API_(AccountController)](https://user-images.githubusercontent.com/86394597/179543697-4e9c4c32-4f52-419f-b773-eaec2695310e.JPG)


![회원_관련_API_(UserController)](https://user-images.githubusercontent.com/86394597/179543701-f8b36d4e-e731-47eb-bf3b-ef56157f34c9.JPG)


![회원_관련_API_(UserApiController)](https://user-images.githubusercontent.com/86394597/179543704-8619a487-11af-4bd8-a5b6-338bd18ca03f.JPG)


![게시판_관련_API_(BoardController)](https://user-images.githubusercontent.com/86394597/179543709-f106088d-94ad-4046-983e-51cca69ed2e4.JPG)


![게시판_관련_API_(BoardApiController)](https://user-images.githubusercontent.com/86394597/179543711-26b04c7e-7655-464b-9749-16b41df65aec.JPG)

</details>
<br>

### 3. DB 설계
<details>
<summary>DB 설계 보기</summary>

![스프링 웹프로젝트 DB ERD](https://user-images.githubusercontent.com/86394597/180385082-70d1b417-332c-41f3-bfce-143752372d9d.JPG)

![Board](https://user-images.githubusercontent.com/86394597/180385062-17a4724a-a58b-4e4a-8888-98bc7ea55581.JPG)

![User](https://user-images.githubusercontent.com/86394597/180385070-4dc0c9db-f839-4fc6-8f05-adfbc2b90ca1.JPG)

![Role](https://user-images.githubusercontent.com/86394597/180385069-e73badab-e55e-4f7e-b627-7a3dd4897f18.JPG)

![User_Role](https://user-images.githubusercontent.com/86394597/180385071-a6916e27-1a07-45fa-9e45-e68aa728b76d.JPG)

![Reply](https://user-images.githubusercontent.com/86394597/180385065-da5a28b0-725b-443a-86de-6db83e0a31ef.JPG)
</details>

<br>

## 실행 화면
<details>
<summary>게시글 관련 보기</summary>

![메인화면](https://user-images.githubusercontent.com/86394597/180235451-f6fdabd3-3e91-409c-bad3-2c666613e588.JPG)

</details>


## 후기
### 1. 프로젝트 보완사항
### 2. 느낀 점 











