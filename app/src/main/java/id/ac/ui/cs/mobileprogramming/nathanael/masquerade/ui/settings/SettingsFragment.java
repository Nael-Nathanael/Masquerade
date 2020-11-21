package id.ac.ui.cs.mobileprogramming.nathanael.masquerade.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.LoginActivity;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.R;

public class SettingsFragment extends Fragment {

    Button logout_button;
    TextView username_settings;
    TextView subscribed_chatroom_settings;
    TextView message_sent_settings;
    private SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        username_settings = view.findViewById(R.id.username_settings);
        subscribed_chatroom_settings = view.findViewById(R.id.subscribed_chatroom_settings);
        message_sent_settings = view.findViewById(R.id.message_sent_settings);
        logout_button = view.findViewById(R.id.logout_button);

        username_settings.setText(
                sharedPreferences.getString("username", null)
        );

        logout_button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.apply();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}