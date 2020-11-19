package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.ChatRoom;

public class LandingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences sharedPreferences;
    private ArrayList<ChatRoom> chatRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        initAttrs();
        initToolbar();
        initDrawer();
        fetchPublicRoomsFromDatabase();
    }

    private void fetchPublicRoomsFromDatabase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("chatrooms").child("public");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatRooms = new ArrayList<>();
                for(DataSnapshot childsnap : snapshot.getChildren()) {
                    ChatRoom chatRoom = childsnap.getValue(ChatRoom.class);
                    chatRooms.add(chatRoom);
                }
                Log.d("NaelsTest", chatRooms.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("NaelsTest", String.valueOf(error));
            }
        };

        reference.addListenerForSingleValueEvent(valueEventListener);
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
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}