package com.example.sellbuy.service.impl;

import com.example.sellbuy.repository.StatisticRepository;
import com.example.sellbuy.service.StatisticService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public void onRequest(String requestURI, Principal userPrincipal) {

        String line = "------------------------------------------------------------------------";

        System.out.println(line + requestURI + line);
        System.out.println(line + userPrincipal + line);
    }
}
