package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.model.UsernameHistory;
import id.ac.ui.cs.mobileprogramming.nathanael.masquerade.helper.viewmodel.UsernameHistoryViewModel;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private SharedPreferences sharedPreferences;
    private EditText usernameField;
    private UsernameHistoryViewModel usernameHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("masq-auth", Context.MODE_PRIVATE);

        if (!checkPermission()) {
            requestPermission();
        }

        if (sharedPreferences.contains("username")) {
            moveToLanding();
        }

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
        startActivity(intent);
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean fine_location_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (fine_location_accepted) {
                    Toast.makeText(this, "You are Masquerade!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Access to location denied! You are Anonymous!", Toast.LENGTH_SHORT).show();
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showMessageOKCancel(
                                (dialog, which) -> {
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                                }
                        );
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage("Masquerade will be pleased to know your location. But we could respect your decisions of anonymity")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}