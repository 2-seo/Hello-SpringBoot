package com.harrybro.springbootwebservice.service.posts;

import com.harrybro.springbootwebservice.domain.posts.Posts;
import com.harrybro.springbootwebservice.domain.posts.PostsRepository;
import com.harrybro.springbootwebservice.web.PostsApiController;
import com.harrybro.springbootwebservice.web.dto.PostsListResponseDto;
import com.harrybro.springbootwebservice.web.dto.PostsResponseDto;
import com.harrybro.springbootwebservice.web.dto.PostsSaveRequestDto;
import com.harrybro.springbootwebservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

// javax 아니고 springframework 이다.
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequiredArgsConstructor
@Service
public class PostsService {

    /*
        스프링에서 Bean을 주입받는 방식
        1) @Autowired
        2) Setter
        3) 생성자
        이 중 가장 권장하는 방식은 생성자로 Bean을 받는 것임.
     */

    // @RequiredArgsConstructor 에 의해 생성자가 만들어지고 받아오도록 처리됨.
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        /*
            Repository를 이용하여 따로 save를 하는 문을 작성하지 된다.
            이유는 @Transactional 코드 내부(트랜잭션)에서 DB에 접근하여 데이터를 가져오면
            이 데이터는 영속성 컨텍스트가 유지된 상태가 된다.
            - 영속성 컨텍스트: 엔터티를 영구히 저장하는 환경
            따라서 트랜잭션이 끝나는 시점에 해당 테이블에 변경분을 반영하게 된다.

            즉 Entity 객체의 값만 변경하면 별도로 Update 쿼리를 날리지 않아도 된다.
            이 개념을 더티 체킹(Dirty checking)이라고 함.
         */
        Posts posts = postsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id: " + id));

        posts.update(requestDto.getTitle(), requestDto.getTitle());

        return id;
    }

    public PostsResponseDto findById(Long id) {

        Posts post = postsRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id: " + id));

        return new PostsResponseDto(post);

    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllByOrderByIdDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id: " + id));

        postsRepository.delete(posts);

        return id;
    }
}
