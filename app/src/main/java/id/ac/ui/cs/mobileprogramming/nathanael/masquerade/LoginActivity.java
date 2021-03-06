package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.UsernameHistoryViewModel;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText usernameField;
    private UsernameHistoryViewModel usernameHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        usernameHistoryViewModel = new ViewModelProvider(this).get(UsernameHistoryViewModel.class);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username_field);

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(do_login());
    }

    private View.OnClickListener do_login() {
        return v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", usernameField.getText().toString());
            editor.apply();

            usernameHistoryViewModel.insert(
                    new UsernameHistory(
                            usernameField.getText().toString()
                    )
            );

            moveToLanding();
        };
    }

    private void moveToLanding() {
        Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        intent.putExtra("online_mode", activeNetwork != null);

        startActivity(intent);
    }

}