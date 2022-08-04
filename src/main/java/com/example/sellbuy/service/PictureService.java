package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.PictureEntity;

import java.util.Optional;

public interface PictureService {



    PictureEntity addPictureEntity(PictureEntity picture1);

    PictureEntity getFirstPicture();

    PictureEntity addPictureInDb(PictureEntity pictureEntity);

    void deleteByProductId(Long id);

   Optional <PictureEntity> findByUrl(String urlPicture);

    void deleteOldPictureById(Long id);
}
