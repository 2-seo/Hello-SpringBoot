
# SpringBoot WebService

## 개요
* SpringBoot를 *처음 공부* 하면서 사용한 Repository 입니다.
* **TDD(Test Driven Development)** 와 **단위 테스트(Unit Test)** 에 대해 학습했습니다.
  * 단위 테스트에는 JUnit과 AssertJ을 사용했습니다.
* **JPA**에 대하여 학습하며 JPA를 이용하여 Database를 다루었습니다.
* Template는 **Mustache**를 사용했습니다.
* Spring Security와 OAuth 2.0을 구현한 구글 로그인을 연동하여 로그인 기능을 구현했습니다.
## 주요 기능
1. 게시물 CRUD

## TDD와 단위 테스트
### 1. TDD Cycle
<img alt="TDD" src="https://user-images.githubusercontent.com/67419004/103828137-cb9c6f00-50bd-11eb-8d57-1924706a0a73.gif" align="center">

1. RED: 항상 실패하는 테스트를 먼저 작성한다.
2. GREEN: 테스트가 통과하는 프로덕션 코드를 작성한다.
3. REFACTOR: 테스트가 통과하면 프로덕션 코드를 리팩토링한다.

### 2. TDD와 단위 테스트는 다른 이야기이다.
- TDD: 테스트가 주도하는 개발 - 테스트 코드를 먼저 작성하는 것으로 시작한다.
- 단위 테스트: TDD의 첫 단계인 **기능 단위의 테스트 코드를 작성**하는 것을 말한다.
  - TDD와 달리 테스트 코드를 꼭 먼저 작성할 필요 없음.
  - 리팩토링도 포함되지 않는다.
  - 순수하게 테스트 코드만 작성하는 것을 말한다.
### 3. 단위 테스트의 이점
- 개발단계 초기에 문제 발견에 도움을 준다.
- 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존 기능이 올바르게 작동하는지 확인 가능하다. (e.g. 회귀 테스트)
- 기능에 대한 불확실성을 감소시킬 수 있다.
- 시스템에 대한 실제 문서를 제공한다. 즉, 단위 테스트 자체가 문서로 사용할 수 있다.

## Template
- Mustache를 사용한 이유
1. JSP와 Velocity는 스프링 부트에서 권장하지 않는다.

> 7.1.9. Template Engines
If possible, JSPs should be avoided. There are several known limitations when using them with embedded servlet containers.

> 7.4.5. JSP Limitations
When running a Spring Boot application that uses an embedded servlet container (and is packaged as an executable archive), there are some limitations in the JSP support.

출처: [SpringBoot Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-spring-mvc-template-engines) (2020.01.07)

2. Freemaker은 템플릿 엔진으로 너무 많은 기능을 지원한다. 기능이 많은 만큼 자유도가 높은데, 숙련도가 낮을 수록 Freemaker 안에 비즈니스 로직이 추가될 확률이 높아진다. 로직이 추가된다면 Spring의 MVC 패턴을 깨게 된다.
1. Thymeleaf는 Spring에서 추천하는 템플릿이지만 문법이 다소 어렵고 RESTful을 학습하려다 템플릿 언어만 공부하는, 배보다 배꼽이 큰 경우가 발생될 수 있다.
1. Mustache는 다른 템플릿 엔진보다 심플하며 로직 코드를 사용할 수 없어 View의 역할과 서버의 역할이 명확하게 분리된다.

## @RestController와 @Controller의 차이
- @Controller은 MVC의 View기술을 사용하여 Template를 반환해준다.
- @RestController은 JSON/XML 타입으로 리턴한다.
- 번외. @Controller에서 객체를 반환하는 방법
  - @ResponseBody를 선언해서 객체를 반환할 수 있다.