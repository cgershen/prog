package com.unam.alex.pumaride;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    String TAG = getClass().getName();

    @Override
    public void onTokenRefresh() {
        // Put code to refresh token
    }
}
