package com.example.sellbuy.service;

import java.security.Principal;

public interface StatisticService {
    void onRequest(String requestURI, Principal userPrincipal);
}
