package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.PictureEntity;

public interface PictureService {

    void initializePictures();

    PictureEntity putInDb(PictureEntity picture1);

    PictureEntity getFirstPicture();
}
