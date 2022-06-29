package com.example.sellbuy.repository;

import com.example.sellbuy.model.entity.LocationEntity;
import com.example.sellbuy.model.entity.enums.LocationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {


    Optional<LocationEntity> findByLocation(LocationEnum locationEnum);
}
