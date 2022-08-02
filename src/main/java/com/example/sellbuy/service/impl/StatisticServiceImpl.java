package com.example.sellbuy.service.impl;

import com.example.sellbuy.event.InitializationEvent;
import com.example.sellbuy.model.entity.StatisticEntity;
import com.example.sellbuy.model.exception.ObjectNotFoundException;
import com.example.sellbuy.model.view.statisticViews.StatisticViewModel;
import com.example.sellbuy.repository.StatisticRepository;
import com.example.sellbuy.service.StatisticService;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;
    private final ModelMapper modelMapper;

    public StatisticServiceImpl(StatisticRepository statisticRepository, ModelMapper modelMapper) {
        this.statisticRepository = statisticRepository;
        this.modelMapper = modelMapper;
    }

    @Order(5)
    @EventListener(InitializationEvent.class)
    @Override
    public void initializeStatistic() {
        if (this.statisticRepository.count() == 0){
            StatisticEntity statistic = new StatisticEntity(1L,0L,0L,
                    0L,0L,0L,0L,
                    0L,0L,0L,0L,
                    0L,0L,0L);
            this.statisticRepository.save(statistic);
        }
    }

    @Override
    public StatisticViewModel getStatisticInfo() {

        StatisticEntity statistic = this.statisticRepository.findById(1L).
                orElseThrow(() -> new ObjectNotFoundException(1L, "Statistic"));
        StatisticViewModel statisticViewModel = this.modelMapper.
                map(statistic,StatisticViewModel.class);

        return statisticViewModel;
    }

    @Override
    public void onRequest(String requestURI, Principal userPrincipal) {

        StatisticEntity statistic = statisticRepository.findById(1L).orElse(null);

        if(statistic!=null) {
            if (userPrincipal == null) {
                statistic.setAnonymousRequests(statistic.getAnonymousRequests()+1);
            } else {
                statistic.setAuthenticatedRequests(statistic.getAuthenticatedRequests()+1);
            }

            switch (requestURI) {
                case "/": statistic.setIndexPageViews(statistic.getIndexPageViews()+1); break;
                case "/products/all": statistic.setAllProductsPageViews(statistic.getAllProductsPageViews()+1); break;
                case "/products/all/promotion": statistic.setPromotionsPageViews(statistic.getPromotionsPageViews()+1); break;
                case "/users/login": statistic.setLoginPageViews(statistic.getLoginPageViews()+1); break;
                case "/users/register": statistic.setRegisterPageViews(statistic.getRegisterPageViews()+1); break;
                case "/admin/users": statistic.setUserInSystemPageViews(statistic.getUserInSystemPageViews()+1); break;
                case "/users/profile": statistic.setMyProfilePageViews(statistic.getMyProfilePageViews()+1); break;
                case "/messages/all": statistic.setMessagesPageViews(statistic.getMessagesPageViews()+1); break;
                case "/users/products": statistic.setMyProductsPageViews(statistic.getMyProductsPageViews()+1); break;
                case "/users/favorites": statistic.setFavoritesPageViews(statistic.getFavoritesPageViews()+1); break;
                case "/products/add": statistic.setAddProductPageViews(statistic.getAddProductPageViews()+1); break;
                case "/admin/statistic": statistic.setStatisticsPageViews(statistic.getStatisticsPageViews()+1); break;

            }
            statistic.setModified(LocalDateTime.now());
            this.statisticRepository.save(statistic);
        }
    }
}
