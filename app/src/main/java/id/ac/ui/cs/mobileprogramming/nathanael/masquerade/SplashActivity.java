package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
        setContentView(glSurfaceView);

        SharedPreferences sharedPreferences = getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (sharedPreferences.contains("username")) {
                moveToLanding();
            } else {
                moveTologin();
            }
        }, 2000);
    }

    private void moveToLanding() {

        Intent intent = new Intent(SplashActivity.this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        intent.putExtra("online_mode", activeNetwork != null);

        startActivity(intent);
    }

    private void moveTologin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
