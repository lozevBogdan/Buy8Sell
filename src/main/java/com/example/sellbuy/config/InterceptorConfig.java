package com.example.sellbuy.config;

import com.example.sellbuy.web.interceptor.StatisticInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig  implements WebMvcConfigurer {

    private final StatisticInterceptor statisticInterceptor;

    public InterceptorConfig(StatisticInterceptor statisticInterceptor) {
        this.statisticInterceptor = statisticInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.statisticInterceptor);
    }
}
