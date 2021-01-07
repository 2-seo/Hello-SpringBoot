package com.harrybro.springbootwebservice.domain.posts;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void boardCreateAndDelete() {

        String title = "테스트 게시글";
        String content = "테스트 본문";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author("likeharrybro@gmail.com")
                .build();

        postsRepository.save(posts);

        List<Posts> postsList = postsRepository.findAll();

        Posts posts1 = postsList.get(0);
        assertThat(posts1.getTitle()).isEqualTo(title);
        assertThat(posts1.getContent()).isEqualTo(content);

    }

    @Test
    public void baseTimeEntity_check() {
        /*
            Auditing 기능 Test
         */
        LocalDateTime now = LocalDateTime.now().minusMinutes(5);

        Posts savedPosts = postsRepository.save(
                Posts.builder()
                        .title("test title")
                        .content("test content")
                        .build()
        );

        assertThat(savedPosts.getCreatedDate()).isAfter(now);
        assertThat(savedPosts.getModifiedDate()).isAfter(now);

    }
}
