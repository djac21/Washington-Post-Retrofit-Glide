package com.djac21.washingtonpostnews.CustomTabs;

import android.support.customtabs.CustomTabsClient;

public interface ServiceConnectionCallback {

    void onServiceConnected(CustomTabsClient client);

    void onServiceDisconnected();
}
