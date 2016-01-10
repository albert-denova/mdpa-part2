package com.lasalle.second.part.week1;

import android.app.Application;
import android.content.Context;

import com.lasalle.second.part.week1.services.ApplicationServiceFactory;
import com.lasalle.second.part.week1.util.VolleyRequestHandler;

/**
 * Created by albert.denova on 09/01/16.
 */
public class PropertyCrossApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        VolleyRequestHandler.getInstance();
        ApplicationServiceFactory.getInstance(this).getAuthService().requestAccessToken();
    }

    public static Context getContext() {
        return context;
    }
}
