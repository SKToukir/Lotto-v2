package com.asfdsakfdsl.sdfdslkafjdjj.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.asfdsakfdsl.sdfdslkafjdjj.MainActivity;
import com.asfdsakfdsl.sdfdslkafjdjj.R;
import com.asfdsakfdsl.sdfdslkafjdjj.model.AppData;
import com.asfdsakfdsl.sdfdslkafjdjj.network.NetworkOperation;
import com.asfdsakfdsl.sdfdslkafjdjj.utils.NetworkConnection;
import com.facebook.LoggingBehavior;
import com.onesignal.OneSignal;

import com.facebook.FacebookSdk;

import static com.facebook.FacebookSdk.setAutoLogAppEventsEnabled;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    private static final int SPLASHTIME = 2000;
    // u can change the value here for smoother effect
    private static final long UPDATEINTERVAL = 1000;
    private Thread threadSplash;
    private ProgressBar progressBar;
    private LinearLayout splashContainer;
    private RelativeLayout rlLoadingContainer;
    private boolean isShowSplash, isWebView;
    private String app_mode, url_0, url_1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            // get the current progress
            int progress = message.what;
            if (progress <= SPLASHTIME) {
                progressBar.setProgress(progress);
            } else {
                if (isWebView) {
                    if (app_mode.equals("0")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                        intent.putExtra("URL", url_0);
                        startActivity(intent);
                    } else if (app_mode.equals("1")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                        intent.putExtra("URL", url_1);
                        startActivity(intent);
                    } else if (app_mode.equals("4")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    if (app_mode.equals("0")) {
                        finish();
                        openUrlBrowser(url_0);
                    }else if (app_mode.equals("1")) {
                        finish();
                        openUrlBrowser(url_1);
                    }else if (app_mode.equals("4")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        OneSignal.initWithContext(this);

        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        setAutoLogAppEventsEnabled(true);

        rlLoadingContainer = findViewById(R.id.rlLoadingContainer);
        splashContainer = findViewById(R.id.splashContainer);
        progressBar = (ProgressBar) findViewById(R.id.progression);

        new NetworkConnection(this).checkNetworkConnection(new NetworkConnection.NetkConnection() {
            @Override
            public void connected(boolean isConnected) {
                if (!isConnected) {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }
        });

        // Splash Screen
        threadSplash = new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (true) {
                    // send current progress to handler
                    mHandler.sendEmptyMessage(progress);
                    // break from the loop after SPLASHSCREEN millis
                    if (progress > 2000)
                        break;
                    // increase the progress
                    progress = (int) (progress + UPDATEINTERVAL);
                    // sleep the worker thread
                    try {
                        Thread.sleep(UPDATEINTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openUrlBrowser(String urlString) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
        startActivity(browserIntent);
    }

    @Override
    protected void onResume() {

        new NetworkOperation(this).fetchAppData(new NetworkOperation.FireData() {
            @Override
            public void onResponse(AppData appData) {
                isShowSplash = appData.isShowSplash();
                isWebView = appData.isWebView();
                app_mode = appData.getApp_mode();
                url_0 = appData.getUrl_0();
                url_1 = appData.getUrl_1();

                if (isShowSplash) {
                    rlLoadingContainer.setVisibility(View.GONE);
                    splashContainer.setVisibility(View.VISIBLE);
                    threadSplash.start();
                } else if (isWebView) {
                    if (appData.getApp_mode().equals("0")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                        intent.putExtra("URL", url_0);
                        startActivity(intent);
                    } else if (appData.getApp_mode().equals("1")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, WebViewActivity.class);
                        intent.putExtra("URL", url_1);
                        startActivity(intent);
                    } else if (appData.getApp_mode().equals("4")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (appData.getApp_mode().equals("0")) {
                        finish();
                        openUrlBrowser(url_0);
                    } else if (appData.getApp_mode().equals("1")) {
                        finish();
                        openUrlBrowser(url_1);
                    } else if (appData.getApp_mode().equals("4")) {
                        finish();
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        super.onResume();
    }
}