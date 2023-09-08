package com.example.repository;

import com.example.entity.CategoryEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Test
    public void itShouldCreateCategory() {
        // given
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCreatedDate(LocalDateTime.now());
        categoryEntity.setName("null");
        // when
        categoryRepository.save(categoryEntity);
        // then
        Optional<CategoryEntity> optional = categoryRepository.findById(categoryEntity.getId());
        Assertions.assertThat(optional.isPresent()).isTrue();
    }

    @Test
    public void itShouldNotSaveCategory() {
        // given
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCreatedDate(LocalDateTime.now());
        categoryEntity.setName(null);
        // when
        // then
        Assertions.assertThatThrownBy(() -> {
            categoryRepository.save(categoryEntity);
        });
    }

    @Test
    public void itShouldThrowDataBaseException() {
        // given
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCreatedDate(LocalDateTime.now());

        // when
        // then
        Assertions.assertThatThrownBy(() -> {
            categoryRepository.save(categoryEntity);
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

//    @Test
//    public void itShouldFindCategoryByName() {
//        // given
//        CategoryEntity categoryEntity = new CategoryEntity();
//        categoryEntity.setCreatedDate(LocalDateTime.now());
//        categoryEntity.setName("Category_uz 1");
//        // when
//        categoryRepository.save(categoryEntity);
//        // then
//        Optional<CategoryEntity> optional = categoryRepository.("Category_uz 1");
//        Assertions.assertThat(optional).isPresent();
//    }
}
