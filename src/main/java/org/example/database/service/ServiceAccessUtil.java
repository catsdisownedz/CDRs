package org.example.database.service;

import org.example.database.config.ApplicationContextProvider;

public class ServiceAccessUtil {

    public static CDRService getCdrService() {
        return ApplicationContextProvider.getApplicationContext().getBean(CDRService.class);
    }

    public static UserService getUserService() {
        return ApplicationContextProvider.getApplicationContext().getBean(UserService.class);
    }
}

