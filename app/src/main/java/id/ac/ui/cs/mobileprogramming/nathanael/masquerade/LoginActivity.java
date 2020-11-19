package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText usernameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        if (sharedPreferences.contains("username")) {
            moveToLanding();
        }

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
            moveToLanding();
        };
    }

    private void moveToLanding() {
        Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}