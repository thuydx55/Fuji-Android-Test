package com.fuji.platform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fuji.fujisdk.FujiSDK;
import com.fuji.fujisdk.core.listener.MessageListener;
import com.fuji.fujisdk.oauth.listener.LoginListener;
import com.fuji.fujisdk.payment.listener.PaymentListener;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button mBtnLogin, mBtnLogout, mBtnPayment, mBtnUserInfo, mBtnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FujiSDK.Instance.setDebugMode(true);
        FujiSDK.Instance.initialize(this.getApplication(), "RMReMonster");

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
                FujiSDK.Instance.showPayment(new MessageListener() {
                    @Override
                    public void onSucceed() {

                    }

                    @Override
                    public void onFailed(String msg) {

                    }
                });
            }
        });

        mBtnUserInfo = (Button) findViewById(R.id.btnUserInfo);
        mBtnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FujiSDK.Instance.showUserInfo();
            }
        });

        mBtnTransfer = (Button) findViewById(R.id.btnTransfer);
        mBtnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FujiSDK.Instance.transferCoin("jp.co.alphapolis.games.remon.5", new PaymentListener() {
                    @Override
                    public void onSucceed(int coin) {
                        Log.d(TAG, "Succeed");
                    }

                    @Override
                    public void onFailed(String message) {
                        Log.d(TAG, "Failed " + message);
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
        mBtnUserInfo.setEnabled(FujiSDK.Instance.isLoggedIn());
        mBtnTransfer.setEnabled(FujiSDK.Instance.isLoggedIn());
    }
}
