package com.finance.projeto.projetofinance.managerApplication;

import android.app.Application;
import com.finance.projeto.projetofinance.util.ApplicationUtil;

public class ManagerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setApplicationContext(getApplicationContext());
    }

}
