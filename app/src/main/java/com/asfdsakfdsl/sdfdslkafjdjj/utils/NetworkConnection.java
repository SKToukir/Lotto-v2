package com.asfdsakfdsl.sdfdslkafjdjj.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

    private Context mContext;

    public NetworkConnection(Context context) {
        this.mContext = context;
    }

    public void checkNetworkConnection(NetkConnection connection){
        if (connection != null)
            connection.connected(isConnected());
    }
    public interface NetkConnection{
        void connected(boolean isConnected);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
