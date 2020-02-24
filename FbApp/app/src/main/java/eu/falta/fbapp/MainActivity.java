package eu.falta.fbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback < LoginResult > () {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Uspešne prihlásený",
                                Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancel() {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Zrušené", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Context context = getApplicationContext();
                        Toast.makeText(context, "chyba", Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
