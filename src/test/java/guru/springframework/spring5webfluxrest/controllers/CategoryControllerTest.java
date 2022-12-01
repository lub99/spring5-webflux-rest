package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class CategoryControllerTest {

    WebTestClient webTestClient;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAllCategories() {
        //given
        given(categoryRepository.findAll()).willReturn(
                Flux.just(
                        Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()
                ));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getCategoryById() {
        given(categoryRepository.findById("someid")).willReturn(
               Mono.just(Category.builder().description("Cat1").build())
        );

        webTestClient.get().uri("/api/v1/categories/someid")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> category = Mono.just(Category.builder().build());

        webTestClient
                .post()
                .uri("/api/v1/categories")
                .body(category, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }
}