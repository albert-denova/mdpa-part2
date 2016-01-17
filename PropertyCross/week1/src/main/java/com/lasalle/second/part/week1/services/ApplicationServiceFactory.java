package com.lasalle.second.part.week1.services;

import android.content.Context;

import com.lasalle.second.part.week1.PropertyCrossApplication;
import com.lasalle.second.part.week1.repositories.impl.PropertyRepoWebService;
import com.lasalle.second.part.week1.repositories.impl.SearchHistoryRepoDb;
import com.lasalle.second.part.week1.repositories.impl.PropertyRepoFile;

public class ApplicationServiceFactory {

    protected static ApplicationServiceFactory instance = null;

    private PropertyService propertyService;
    private AuthService authService;
    private Context context;

    protected ApplicationServiceFactory() {
        this.context = PropertyCrossApplication.getContext();
        this.propertyService = new PropertyService(new PropertyRepoWebService(), new SearchHistoryRepoDb(context));
        this.authService = new AuthService();
    }

    public static ApplicationServiceFactory getInstance() {
        if(instance == null) {
            instance = new ApplicationServiceFactory();
        }

        return instance;
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }


    public AuthService getAuthService() {
        return authService;
    }

}
