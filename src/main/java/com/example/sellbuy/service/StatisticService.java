package com.example.sellbuy.service;

import com.example.sellbuy.model.view.statisticViews.StatisticViewModel;

import java.security.Principal;

public interface StatisticService {
    void onRequest(String requestURI, Principal userPrincipal);

    void initializeStatistic();

    StatisticViewModel getStatisticInfo();
}
