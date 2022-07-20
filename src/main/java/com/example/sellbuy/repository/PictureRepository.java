package com.example.sellbuy.repository;

import com.example.sellbuy.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity,Long> {


    void deleteByProductId(Long id);

    // THE METHOD BELOW DO NOT WORK ??????
//    @Query(value = "select p from PictureEntity p where p.product.id = :id")
//    PictureEntity findByProductId(@Param("id") Long id);

   Optional<PictureEntity> findByUrl(String urlPicture);
}
