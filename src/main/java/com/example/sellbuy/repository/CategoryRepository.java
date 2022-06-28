package com.example.sellbuy.repository;

import com.example.sellbuy.model.entity.CategoryEntity;
import com.example.sellbuy.model.entity.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByCategory(CategoryEnum category);
}
