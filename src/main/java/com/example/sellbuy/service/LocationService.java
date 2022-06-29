package com.example.sellbuy.service;

import com.example.sellbuy.model.entity.LocationEntity;
import com.example.sellbuy.model.entity.enums.LocationEnum;

public interface LocationService {

    void initializeLocations();

    LocationEntity findByLocation(LocationEnum sofiaGrad);
}
