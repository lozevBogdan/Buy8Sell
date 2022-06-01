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

        if(pictureRepository.count() == 0){

            PictureEntity picture1 = new PictureEntity();
            picture1.setUrl("https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FMercedes-Benz_" +
                    "S-Class&psig=AOvVaw2ogX5Lfr43z3Tn7vlNpqdk&ust=1649355177808000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCPChhP-EgPcCFQAAAAAdAAAAABAD");

            pictureRepository.save(picture1);
        }


    }

    @Override
    public PictureEntity putInDb(PictureEntity picture1) {
        return pictureRepository.save(picture1);
    }

    @Override
    public PictureEntity getFirstPicture() {
        return pictureRepository.findAll().get(0);
    }
}