package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.PublicChatroomPagerNavigationViewModel;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.services.ChatRoomSubscriptionNotificationService;

import static id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.Constant.PERMISSION_REQUEST_CODE;

public class LandingActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences sharedPreferences;
    private boolean canExit;
    private PublicChatroomPagerNavigationViewModel navigationModel;

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String combineFromJNI(String message1, String message2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initAttrs();
        initToolbar();
        initDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(LandingActivity.this, ChatRoomSubscriptionNotificationService.class));
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_public_chatroom,
                R.id.nav_private_chatroom,
                R.id.nav_subscribed_chatroom,
                R.id.nav_notes
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView sidebar_username = navigationView.getHeaderView(0).findViewById(R.id.sidebar_username);
        sidebar_username.setText(
                sharedPreferences.getString("username", null)
        );
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initAttrs() {
        sharedPreferences = getSharedPreferences("masq-auth", Context.MODE_PRIVATE);
        navigationModel = new ViewModelProvider(this).get(PublicChatroomPagerNavigationViewModel.class);

        final Observer<Integer> navigationObserver = targetPage -> {
            canExit = targetPage == 0;
        };

        navigationModel.getCurrentPage().observe(LandingActivity.this, navigationObserver);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.nav_public_chatroom) {
            if (!canExit) {
                navigationModel.getCurrentPage().setValue(0);
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(LandingActivity.this, ChatRoomSubscriptionNotificationService.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean fine_location_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarse_location_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (fine_location_accepted && coarse_location_accepted) {
                    Toast.makeText(this, "You are Masquerade!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Access to location denied! You are Anonymous!", Toast.LENGTH_SHORT).show();
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        showMessageOKCancel(
                                (dialog, which) -> {
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
                                }
                        );
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("Masquerade will be pleased to know your location. But we could respect your decisions of anonymity")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}