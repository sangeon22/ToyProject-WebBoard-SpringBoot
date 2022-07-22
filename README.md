# ToyProject-WebBoard-SpringBoot
Springboot + security,JPA + HeidiSQL(MariaDB,MySQL) + BootStrap 웹게시판

<img src="https://user-images.githubusercontent.com/86394597/180389566-60dddd57-7c70-4d13-8027-9b4d729bfd88.JPG">

## ⅰ. 프로젝트 소개 (요구사항)
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

## ⅲ. 실행 화면 (구현)
<summary> 메인화면</summary>

![1](https://user-images.githubusercontent.com/86394597/180411549-042e6585-917c-4919-aa68-f01b6a7a42cd.JPG)

![녹화_2022_07_22_22_21_03_170](https://user-images.githubusercontent.com/86394597/180447971-c04a0f1f-41c0-49ec-bd9d-2836827a0f15.gif)



<details>
<summary> 관련 보기</summary>
</details>

<details>
<summary> 관련 보기</summary>
</details>


<details>
<summary> 관련 보기</summary>
</details>


<details>
<summary> 관련 보기</summary>
</details>


<details>
<summary> 관련 보기</summary>
</details>


<details>
<summary> 관련 보기</summary>
</details>


![2](https://user-images.githubusercontent.com/86394597/180411554-e0750447-42c2-406c-a2a3-aa177a6206af.JPG)
![3](https://user-images.githubusercontent.com/86394597/180411556-192f0f75-364d-4f72-9ac1-1b69b6bbe0fd.JPG)
![4-1](https://user-images.githubusercontent.com/86394597/180411559-9403cdbe-4f7c-4bb6-b260-9cc25a968f90.JPG)
![4-2](https://user-images.githubusercontent.com/86394597/180411561-753d9601-76ee-498c-b37a-7089af62d706.JPG)
![5](https://user-images.githubusercontent.com/86394597/180411541-659bf1b6-66bd-477a-9070-78e6f68d27c2.JPG)
![6](https://user-images.githubusercontent.com/86394597/180411613-3f2f139e-6087-4a99-811f-3ffdd8476832.JPG)
![7-1](https://user-images.githubusercontent.com/86394597/180411614-a91bca76-6dab-4ba1-a217-f289fbe31a68.JPG)
![8](https://user-images.githubusercontent.com/86394597/180411617-65f833d3-2aaa-4bac-b8d0-b9ec90b6a11a.JPG)
![9](https://user-images.githubusercontent.com/86394597/180411623-386d7982-31b2-4850-b544-17092ee16ace.JPG)
![9-2](https://user-images.githubusercontent.com/86394597/180411627-8e9c738d-231d-4b31-8db6-e259f7596581.JPG)
![10](https://user-images.githubusercontent.com/86394597/180411599-7ded26b1-0ca6-4ed7-b229-5ab1cc37e814.JPG)
![11](https://user-images.githubusercontent.com/86394597/180411696-bb21f001-ff6b-426a-ac25-21e3fbf22d97.JPG)
![12](https://user-images.githubusercontent.com/86394597/180411704-e9d5232e-5bb4-4ca4-9f68-1b772df3795f.JPG)
![13](https://user-images.githubusercontent.com/86394597/180411757-ada1856a-5f81-4cb3-93d9-452ac250ed68.JPG)
![14](https://user-images.githubusercontent.com/86394597/180411759-c949c20d-d41e-4f24-9389-cd4d5da6048e.JPG)
![15](https://user-images.githubusercontent.com/86394597/180411762-54bfb1f1-bbed-474d-95c2-6e3892e80653.JPG)
![16](https://user-images.githubusercontent.com/86394597/180411747-200f453e-e55e-4ffe-af82-e707bde5fb48.JPG)
![16-1](https://user-images.githubusercontent.com/86394597/180411794-2a261b63-32e3-4d53-b9a7-24f70753e411.JPG)
![16-2](https://user-images.githubusercontent.com/86394597/180411796-7f6fadaf-e6eb-4a44-b4f7-2432f2a5d606.JPG)
![17](https://user-images.githubusercontent.com/86394597/180411797-7afee4d5-dedc-4377-b39f-1e410e091d98.JPG)
![18](https://user-images.githubusercontent.com/86394597/180411790-980a81b5-4345-48cd-b4ec-4e368e940874.JPG)
![19](https://user-images.githubusercontent.com/86394597/180411845-a865bb84-2262-4c0e-9d8d-0c72d18801b0.JPG)
![20](https://user-images.githubusercontent.com/86394597/180411843-621a0b50-c005-4254-b12b-26e8a7bc226c.JPG)
![20-2](https://user-images.githubusercontent.com/86394597/180411898-f06dfc41-01a5-4c6e-8f6e-dee2dc06be0a.JPG)
![21](https://user-images.githubusercontent.com/86394597/180411893-c54ab065-d0aa-4120-aa70-bdb64a7b3b17.JPG)
ㅡㅡㅡㅡㅡㅡㅡㅡ
![24](https://user-images.githubusercontent.com/86394597/180411952-04b50264-f559-4d89-88fc-ddfcfbf789b9.JPG)
![24-1](https://user-images.githubusercontent.com/86394597/180411954-c782a4a5-0639-4b8e-ae9a-01ff8dae89f7.JPG)
![25](https://user-images.githubusercontent.com/86394597/180411957-9275934e-0796-4704-92c1-1630e5074c97.JPG)
![26](https://user-images.githubusercontent.com/86394597/180411960-398c5ec1-635c-45ae-9fe3-3b606bc19e9c.JPG)
![27](https://user-images.githubusercontent.com/86394597/180411965-6b4f8b1b-eddd-4040-a07c-915442359286.JPG)
![28](https://user-images.githubusercontent.com/86394597/180411968-9a73dd9b-6d72-400c-96c3-768e76c61941.JPG)
![29](https://user-images.githubusercontent.com/86394597/180411970-7a289a68-7c7b-40d5-891c-d5d865d6d42e.JPG)
![30](https://user-images.githubusercontent.com/86394597/180411971-e334bf09-5040-4a21-becc-0c030df1113c.JPG)
![31](https://user-images.githubusercontent.com/86394597/180412024-ded43511-c74a-4098-a77a-f2af352f9126.JPG)
![32](https://user-images.githubusercontent.com/86394597/180412030-ed054da1-cf99-4db2-bd0a-ac8b992c47d4.JPG)
![33](https://user-images.githubusercontent.com/86394597/180412033-ace8680c-4884-4f9d-a92a-995808454e5f.JPG)
![34](https://user-images.githubusercontent.com/86394597/180412035-37c740ef-26d0-492e-af94-032f90619070.JPG)
![35](https://user-images.githubusercontent.com/86394597/180412038-92645013-4e73-4b6b-b9ab-93a40039ed11.JPG)
![36](https://user-images.githubusercontent.com/86394597/180412040-4ab78c6a-858a-4e51-9c64-8d63bab26749.JPG)
![37-1](https://user-images.githubusercontent.com/86394597/180412079-4780bb7d-8098-4985-b44f-a4f55427fd1c.JPG)
![37-2](https://user-images.githubusercontent.com/86394597/180412081-72b0691f-5d81-4e9f-85f4-ac5bc023678d.JPG)
![37-3](https://user-images.githubusercontent.com/86394597/180412085-66e598a5-9247-4bcc-9fed-277051f23781.JPG)
![37-4](https://user-images.githubusercontent.com/86394597/180412087-4dfe605b-ac35-4231-af81-98cb5c83f950.JPG)
![38-1](https://user-images.githubusercontent.com/86394597/180412089-6661e1d0-cf63-4daf-9957-bd70afce7f1c.JPG)
![38-2](https://user-images.githubusercontent.com/86394597/180412073-f1c3d3e3-29a9-4aeb-a66d-d3fed7b995c2.JPG)

 
</details>

<br>

## ⅳ. 테스트 (테스트)
- 정리 후 업데이트 예정


<br>

## ⅴ. 후기

### 1. 프로젝트 보완사항
### 2. 느낀 점 











