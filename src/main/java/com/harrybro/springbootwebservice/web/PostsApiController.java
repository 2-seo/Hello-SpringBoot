package com.harrybro.springbootwebservice.web;

import com.harrybro.springbootwebservice.domain.posts.Posts;
import com.harrybro.springbootwebservice.service.posts.PostsService;
import com.harrybro.springbootwebservice.web.dto.PostsResponseDto;
import com.harrybro.springbootwebservice.web.dto.PostsSaveRequestDto;
import com.harrybro.springbootwebservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto postsCreateRequestDto) {
        return postsService.save(postsCreateRequestDto);
    }

    @PutMapping("/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        return postsService.delete(id);
    }

}
