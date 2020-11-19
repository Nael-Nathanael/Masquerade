package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.publicChatroom.viewmodel.PublicChatroomPagerNavigationViewModel;

public class LandingActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences sharedPreferences;
    private boolean canExit;
    private PublicChatroomPagerNavigationViewModel navigationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initAttrs();
        initToolbar();
        initDrawer();
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_public_chatroom, R.id.nav_private_chatroom, R.id.nav_notes, R.id.nav_settings)
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
        if (navController.getCurrentDestination().getId() == R.id.nav_public_chatroom) {
            if (!canExit) {
                navigationModel.getCurrentPage().setValue(0);
                return;
            }
        }

        super.onBackPressed();
    }
}