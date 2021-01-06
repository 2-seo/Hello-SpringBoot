package com.harrybro.springbootwebservice.web;

import com.harrybro.springbootwebservice.domain.posts.Posts;
import com.harrybro.springbootwebservice.domain.posts.PostsRepository;
import com.harrybro.springbootwebservice.web.dto.PostsSaveRequestDto;
import com.harrybro.springbootwebservice.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Random으로 Port를 생
public class PostsApiControllerTest {

    // @WebMvcTest를 사용하지 않은 이유
    // - JPA의 기능이 작동하지 않기 때문

    // JPA의 기능도 함께 테스트할 때는
    // @SpringBootTest 와 TestRestTemple 을 사용한다.

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void postsSave() {
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);

        System.out.println(postsList.get(0).getCreatedDate());
        System.out.println(postsList.get(0).getModifiedDate());

    }

    @Test
    public void postUpdate() {

        // posts 한 개를 저장함.
        Posts posts = Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build();

        // 업데이트할 내용
        Long updateId = posts.getId();
        String updateTitle = "update title";
        String updateContent = "update content";

        // Request는 RequestDto에 담겨서 옴
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();


    }

}
