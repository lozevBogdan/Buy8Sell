package com.example.sellbuy.web.interceptor;

import com.example.sellbuy.service.StatisticService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatisticInterceptor implements HandlerInterceptor {

    private final StatisticService statisticService;

    public StatisticInterceptor(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        this.statisticService.onRequest(request.getRequestURI(),request.getUserPrincipal());

        return true;
    }
}
