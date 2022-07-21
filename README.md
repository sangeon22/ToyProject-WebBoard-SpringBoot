# ToyProject-WebBoard-SpringBoot
Springboot + security,JPA + HeidiSQL(MariaDB,MySQL) + BootStrap 웹게시판 


### 실행 화면

<details>
<summary>게시글 관련 보기</summary>

![메인화면](https://user-images.githubusercontent.com/86394597/180235451-f6fdabd3-3e91-409c-bad3-2c666613e588.JPG)

</details>



<details>
<summary>API 설계 보기</summary>

![메인_화면_API_(HomeController)](https://user-images.githubusercontent.com/86394597/179543694-c9df5462-9472-43ab-8704-eaddade81205.JPG)


![계정_관련_API_(AccountController)](https://user-images.githubusercontent.com/86394597/179543697-4e9c4c32-4f52-419f-b773-eaec2695310e.JPG)


![회원_관련_API_(UserController)](https://user-images.githubusercontent.com/86394597/179543701-f8b36d4e-e731-47eb-bf3b-ef56157f34c9.JPG)


![회원_관련_API_(UserApiController)](https://user-images.githubusercontent.com/86394597/179543704-8619a487-11af-4bd8-a5b6-338bd18ca03f.JPG)


![게시판_관련_API_(BoardController)](https://user-images.githubusercontent.com/86394597/179543709-f106088d-94ad-4046-983e-51cca69ed2e4.JPG)


![게시판_관련_API_(BoardApiController)](https://user-images.githubusercontent.com/86394597/179543711-26b04c7e-7655-464b-9749-16b41df65aec.JPG)


</details>




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
