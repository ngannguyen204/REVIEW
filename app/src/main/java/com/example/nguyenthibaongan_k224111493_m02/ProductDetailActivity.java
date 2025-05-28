package com.example.nguyenthibaongan_k224111493_m02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private EditText edtProductCode, edtProductName, edtUnitPrice;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        edtProductCode = findViewById(R.id.edtProductCode);
        edtProductName = findViewById(R.id.edtProductName);
        edtUnitPrice = findViewById(R.id.edtUnitPrice);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {
        String code = edtProductCode.getText().toString();
        String name = edtProductName.getText().toString();
        double price = Double.parseDouble(edtUnitPrice.getText().toString());

        // Create new product (ID will be generated in ListProduct)
        Product product = new Product(0, code, name, price, R.mipmap.ic_launcher);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_product", product);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}