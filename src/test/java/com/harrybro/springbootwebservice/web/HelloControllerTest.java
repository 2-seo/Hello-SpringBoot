package com.harrybro.springbootwebservice.web;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// test를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴.
// SpringRunner.class 라는 스프링 실행자를 사용
// 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 함
@WebMvcTest(controllers = HelloController.class)
// 여러 스프릥 테스트 어노테이션 중 Web(Spring MVC)에 집중할 수 있는 어노테이션
// 선언할 경우 @Controller @ControllerAdvice 등 사용 가능
// 단, @Service, @Component, @Repository 등 사용 불가
public class HelloControllerTest {

    @Autowired
    // Spring이 관리하는 Bean을 주입 받음.
    private MockMvc mvc;
    // 웹 API를 테스트할 때 사용
    // 스프링 MVC 테스트의 시작점이 됨.
    // 이 클래스를 통해 HTTP GET, PST 등에 대한 API 테스트를 할 수 있음
    @Test
    public void returnHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void returnHelloDto() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", is(name)))
                .andExpect((ResultMatcher) jsonPath("$.amount", is(amount)));

    }

}
