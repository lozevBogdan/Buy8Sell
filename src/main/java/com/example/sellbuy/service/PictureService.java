package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.model.entity.ProductEntity;

public interface PictureService {

    void initializePictures();

    PictureEntity addPictureEntity(PictureEntity picture1);

    PictureEntity getFirstPicture();

    PictureEntity addPictureInDb(PictureEntity pictureEntity);
}
