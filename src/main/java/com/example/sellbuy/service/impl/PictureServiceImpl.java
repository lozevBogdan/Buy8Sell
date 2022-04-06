package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.PictureEntity;
import com.example.sellbuy.repository.PictureRepository;
import com.example.sellbuy.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void initializePictures() {

    }

    @Override
    public PictureEntity putInDb(PictureEntity picture1) {

        return pictureRepository.save(picture1);
    }
}
