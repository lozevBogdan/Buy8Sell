package com.example.sellbuy.init;

import com.example.sellbuy.event.InitializationEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final ApplicationEventPublisher eventPublisher;

    public DBInit(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    @Override
    public void run(String... args) throws Exception {

        InitializationEvent initializationEvent = new InitializationEvent(DBInit.class);
        eventPublisher.publishEvent(initializationEvent);
    }

}
