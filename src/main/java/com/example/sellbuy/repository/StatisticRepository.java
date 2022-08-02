package com.example.sellbuy.repository;

import com.example.sellbuy.model.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity,Long> {

}
