package com.example.nguyenthibaongan_k224111493_m02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.ListAccount;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox chkSaveLoginInfor;
    private ListAccount listAccount;

    private int backPressCount = 0;
    private static final int MAX_BACK_PRESS_COUNT = 3;
    private long lastBackPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Khởi tạo danh sách tài khoản và tạo dữ liệu mẫu
        listAccount = new ListAccount();
        listAccount.generate_sample_dataset();

        addViews();
        restoreLoginInformation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        chkSaveLoginInfor = findViewById(R.id.chkSaveLoginInfor);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastBackPressTime > 2000) {
            backPressCount = 1;
            Toast.makeText(this, "Press back " + (MAX_BACK_PRESS_COUNT - backPressCount) + " more times to exit", Toast.LENGTH_SHORT).show();
        } else {
            backPressCount++;
            if (backPressCount >= MAX_BACK_PRESS_COUNT) {
                showExitConfirmationDialog();
            } else {
                Toast.makeText(this, "Press back " + (MAX_BACK_PRESS_COUNT - backPressCount) + " more times to exit", Toast.LENGTH_SHORT).show();
            }
        }
        lastBackPressTime = currentTime;
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        Resources res = getResources();

        builder.setTitle(res.getText(R.string.title_confirm_exit_title));
        builder.setMessage(res.getText(R.string.title_confirm_exit_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(res.getText(R.string.title_confirm_exit_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton(res.getText(R.string.title_confirm_exit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                backPressCount = 0;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void do_login(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra với danh sách tài khoản
        boolean loginSuccess = false;
        for (models.Account account : listAccount.getAccounts()) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                loginSuccess = true;
                break;
            }
        }

        if (loginSuccess) {
            saveLoginInformation();
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Login failed! Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void do_exit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        Resources res = getResources();

        builder.setTitle(res.getText(R.string.title_confirm_exit_title));
        builder.setMessage(res.getText(R.string.title_confirm_exit_message));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(res.getText(R.string.title_confirm_exit_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        });

        builder.setNegativeButton(res.getText(R.string.title_confirm_exit_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void saveLoginInformation() {
        SharedPreferences preferences = getSharedPreferences("LOGIN_PREFERENCE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String usr = etUsername.getText().toString();
        String pwd = etPassword.getText().toString();
        boolean isSave = chkSaveLoginInfor.isChecked();

        if (isSave) {
            editor.putString("USER_NAME", usr);
            editor.putString("PASSWORD", pwd);
        } else {
            editor.remove("USER_NAME");
            editor.remove("PASSWORD");
        }
        editor.putBoolean("SAVED", isSave);
        editor.apply();
    }

    public void restoreLoginInformation() {
        SharedPreferences preferences = getSharedPreferences("LOGIN_PREFERENCE", MODE_PRIVATE);
        boolean isSave = preferences.getBoolean("SAVED", false);

        if (isSave) {
            String usr = preferences.getString("USER_NAME", "");
            String pwd = preferences.getString("PASSWORD", "");
            etUsername.setText(usr);
            etPassword.setText(pwd);
        }
        chkSaveLoginInfor.setChecked(isSave);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveLoginInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreLoginInformation();
    }
}