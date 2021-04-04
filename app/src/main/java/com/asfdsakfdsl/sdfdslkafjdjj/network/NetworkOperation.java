package com.asfdsakfdsl.sdfdslkafjdjj.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.asfdsakfdsl.sdfdslkafjdjj.model.AppData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class NetworkOperation {

    private static final String TAG = "NetworkOperation";
    private static final int FETCH_DATA_FAILURE = 0;
    private static final int FETCH_DATA_SUCCESS = 1;

    private Context mContext;
    private AppData appData;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseRemoteConfigSettings firebaseRemoteConfigSettings;


    private Thread threadFetch;

    private Handler handler;

    public NetworkOperation(Context context) {
        this.mContext = context;

    }


    @SuppressLint("HandlerLeak")
    public void fetchAppData(final FireData fireData) {
        Log.d(TAG, "onComplete: call method");
        if (fireData != null) {

            threadFetch = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 2; i++) {

                        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                        firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder().build();
                        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings);
                        firebaseRemoteConfig.fetchAndActivate();

                        firebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: called");

                                    appData = new AppData();

                                    String appMode = firebaseRemoteConfig.getString("APP_MODE");
                                    boolean isWebView = firebaseRemoteConfig.getBoolean("IS_WEBVIEW");
                                    boolean isShowSplash = firebaseRemoteConfig.getBoolean("SHOW_SPLASH");
                                    String url_0 = firebaseRemoteConfig.getString("URL_0");
                                    String url_1 = firebaseRemoteConfig.getString("URL_1");

                                    appData.setApp_mode(appMode);
                                    appData.setWebView(isWebView);
                                    appData.setShowSplash(isShowSplash);
                                    appData.setUrl_0(url_0);
                                    appData.setUrl_1(url_1);

                                    Log.d(TAG, "onComplete: " + isShowSplash);
                                }
                            }
                        });

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessage(NetworkOperation.FETCH_DATA_SUCCESS);
                }
            });

            threadFetch.start();

            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                   if (msg.what == NetworkOperation.FETCH_DATA_SUCCESS){
                       stopThread();
                       fireData.onResponse(appData);
                   }
                }
            };
        }
    }

    private void stopThread(){
        if(threadFetch != null){
            if(threadFetch.isAlive()){
                threadFetch.interrupt();
            }
        }
    }


    public interface FireData {
        void onResponse(AppData appData);

        void onFailure(String errorMessage);
    }
}
