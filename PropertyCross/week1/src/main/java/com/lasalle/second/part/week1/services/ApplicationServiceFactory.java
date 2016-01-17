package com.lasalle.second.part.week1.services;

import android.content.Context;

import com.lasalle.second.part.week1.repositories.impl.DbSearchHistoryRepo;
import com.lasalle.second.part.week1.repositories.impl.FilePropertyRepo;

public class ApplicationServiceFactory {

    protected static ApplicationServiceFactory instance = null;

    private PropertyService propertyService;
    private AuthService authService;
    private Context context;

    protected ApplicationServiceFactory(Context context) {
        this.context = context;
        this.propertyService = new PropertyService(new FilePropertyRepo(context), new DbSearchHistoryRepo(context));
        this.authService = new AuthService();
    }

    public static ApplicationServiceFactory getInstance(Context context) {
        if(instance == null) {
            instance = new ApplicationServiceFactory(context);
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
