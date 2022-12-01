package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    Flux<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<Category> getCategoryById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }
}
