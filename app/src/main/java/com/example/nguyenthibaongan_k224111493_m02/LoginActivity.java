package com.example.nguyenthibaongan_k224111493_m02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import connectors.AccountConnector;
import models.Account;
import models.ListAccount;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox chkSaveLoginInfor;
    private ListAccount listAccount;

    private int backPressCount = 0;
    private static final int MAX_BACK_PRESS_COUNT = 3;
    private long lastBackPressTime = 0;

    String DATABASE_NAME="Review.sql";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Khởi tạo danh sách tài khoản và tạo dữ liệu mẫu
        listAccount = new ListAccount();
        //listAccount.generate_sample_dataset();

        addViews();
        restoreLoginInformation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        processCopy();
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
        AccountConnector accconnector=new AccountConnector();

        String usr = etUsername.getText().toString();
        String pwd = etPassword.getText().toString();

        Account a_login = accconnector.login(this,usr, pwd);
        if (a_login != null) {
            Intent intent = new Intent(this,CategoryManagementActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login failed! Check your account again!", Toast.LENGTH_SHORT).show();
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


    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);

        if (!dbFile.exists())
        {
            try
            {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset()
    {
        try {
            InputStream myInput;

            myInput = getAssets().open(DATABASE_NAME);


            // Path to the just created empty db
            String outFileName = getDatabasePath();

            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}