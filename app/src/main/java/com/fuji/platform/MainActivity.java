package com.fuji.platform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fuji.fujisdk.FujiSDK;
import com.fuji.fujisdk.core.listener.MessageListener;
import com.fuji.fujisdk.oauth.listener.LoginListener;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button mBtnLogin, mBtnLogout, mBtnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FujiSDK.Instance.initialize(this.getApplication(), "TestSDKCode");

        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FujiSDK.Instance.login(new LoginListener() {
                    @Override
                    public void onLoginSucceed() {
                        Log.d(TAG, "onLoginSucceed");
                        reloadButtonState();
                    }

                    @Override
                    public void onLoginFailed(String message) {
                        Log.d(TAG, "onLoginFailed: " + message);
                        reloadButtonState();
                    }

                    @Override
                    public void onLoginCancelled() {
                        Log.d(TAG, "onLoginCancelled");
                        reloadButtonState();
                    }
                });
            }
        });

        mBtnLogout = (Button) findViewById(R.id.btnLogout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FujiSDK.Instance.logout();
                reloadButtonState();
            }
        });

        mBtnPayment = (Button) findViewById(R.id.btnPayment);
        mBtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FujiSDK.Instance.pay(new MessageListener() {
                    @Override
                    public void onSucceed() {

                    }

                    @Override
                    public void onFailed(String msg) {

                    }
                });
            }
        });

        reloadButtonState();
    }

    private void reloadButtonState() {
        mBtnLogin.setEnabled(!FujiSDK.Instance.isLoggedIn());
        mBtnLogout.setEnabled(FujiSDK.Instance.isLoggedIn());
        mBtnPayment.setEnabled(FujiSDK.Instance.isLoggedIn());
    }
}
