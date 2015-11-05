package com.finance.projeto.projetofinance.util;

import android.content.Context;

/**
 * Created by Andrea on 26/09/2015.
 */
public class ApplicationUtil {

    private static Context applicationContext;

    private ApplicationUtil(){
        super();
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        ApplicationUtil.applicationContext = applicationContext;
    }
}
