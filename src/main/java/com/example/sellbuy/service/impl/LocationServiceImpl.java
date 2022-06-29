package com.example.sellbuy.service.impl;

import com.example.sellbuy.model.entity.LocationEntity;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import com.example.sellbuy.repository.LocationRepository;
import com.example.sellbuy.service.LocationService;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void initializeLocations() {

        if(this.locationRepository.count() == 0){

            for (LocationEnum locationEnum : LocationEnum.values()) {

                LocationEntity newLocation = new LocationEntity();

                newLocation.setLocation(locationEnum);

                this.locationRepository.save(newLocation);

            }

        }

    }

    @Override
    public LocationEntity findByLocation(LocationEnum locationEnum) {
        return this.locationRepository.findByLocation(locationEnum).get();
    }
}
