package com.example.sellbuy.repository;

import com.example.sellbuy.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    Set<ProductEntity> findProductsBySellerId(Long id);


    @Transactional
    @Modifying
    @Query("delete from ProductEntity p where p.id = ?1")
    void deleteById(Long id);
}
