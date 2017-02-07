package com.fuji.platform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fuji.fujisdk.FujiSDK;
import com.fuji.fujisdk.oauth.listener.LoginListener;
import com.fuji.fujisdk.payment.Transaction;
import com.fuji.fujisdk.payment.listener.TransactionListener;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Button mBtnLogin, mBtnLogout, mBtnUserInfo, mBtnTransfer;

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
                FujiSDK.Instance.transferCoin("jp.co.alphapolis.games.remon.5", new TransactionListener() {
                    @Override
                    public void onSucceed(Transaction transaction) {
                        Log.d(TAG, "Succeed: " + transaction.packageCode + " - " + transaction.fCoin);
                    }

                    @Override
                    public void onFailed(String message) {
                        Log.d(TAG, "Failed " + message);
                    }

                    @Override
                    public void onCancelled() {
                        Log.d(TAG, "Cancelled");
                    }
                });
            }
        });

        reloadButtonState();

//        Log.d(TAG, Utils.calculateHashParam(new HashMap<String, String>(){{ put("bce", "3"); put("def", "1"); put("abc", "2"); }}));
    }

    private void reloadButtonState() {
        mBtnLogin.setEnabled(!FujiSDK.Instance.isLoggedIn());
        mBtnLogout.setEnabled(FujiSDK.Instance.isLoggedIn());
        mBtnUserInfo.setEnabled(FujiSDK.Instance.isLoggedIn());
        mBtnTransfer.setEnabled(FujiSDK.Instance.isLoggedIn());
    }
}
