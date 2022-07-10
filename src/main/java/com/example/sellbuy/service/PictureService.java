package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;

import java.util.Optional;

public interface PictureService {

    void initializePictures();

    PictureEntity addPictureEntity(PictureEntity picture1);

    PictureEntity getFirstPicture();

    PictureEntity addPictureInDb(PictureEntity pictureEntity);

    void deleteByProductId(Long id);

    PictureEntity findByProductId(Long id);

   Optional <PictureEntity> findByUrl(String urlPicture);

    void deletePictureById(Long id);
}
