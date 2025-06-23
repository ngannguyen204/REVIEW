package com.example.nguyenthibaongan_k224111493_m02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import connectors.CategoryConnector;
import connectors.ProductConnector;
import models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private EditText edtProductId, edtProductName, edtPrice, edtCategoryName;
    private ImageView imgProduct;
    private Button btnSave, btnRemove;
    private ProductConnector productConnector;
    private CategoryConnector categoryConnector;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        // Initialize views
        edtProductId = findViewById(R.id.edtProductId);
        edtProductName = findViewById(R.id.edtProductName);
        edtPrice = findViewById(R.id.edtPrice);
        edtCategoryName = findViewById(R.id.edtCategoryName);
        imgProduct = findViewById(R.id.imgProduct);
        btnSave = findViewById(R.id.btnSave);
        btnRemove = findViewById(R.id.btnRemove);

        // Initialize connectors
        productConnector = new ProductConnector(this);
        categoryConnector = new CategoryConnector(this);

        // Get product ID from intent
        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        if (productId != -1) {
            // Load product details
            currentProduct = productConnector.getProductById(productId);
            if (currentProduct != null) {
                displayProductDetails(currentProduct);
            } else {
                Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set up button listeners
        btnSave.setOnClickListener(v -> saveProduct());
        btnRemove.setOnClickListener(v -> removeProduct());
    }

    private void displayProductDetails(Product product) {
        edtProductId.setText(String.valueOf(product.getId()));
        edtProductName.setText(product.getProductName());
        edtPrice.setText(String.valueOf(product.getUnitPrice()));

        // Get category name
        String categoryName = categoryConnector.getCategoryNameById(product.getCateid());
        edtCategoryName.setText(categoryName);

        // Load product image
        if (product.getImageLink() != null && !product.getImageLink().isEmpty()) {
            new LoadImageTask(imgProduct).execute(product.getImageLink());
        } else {
            imgProduct.setImageResource(R.mipmap.ic_launcher);
        }
    }

    // Thêm lớp AsyncTask vào trong ProductDetailActivity
    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlString = urls[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private void saveProduct() {
        // Implement save functionality here
        Toast.makeText(this, "Save functionality to be implemented", Toast.LENGTH_SHORT).show();
    }

    private void removeProduct() {
        // Implement remove functionality here
        Toast.makeText(this, "Remove functionality to be implemented", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (productConnector != null) {
            productConnector.close();
        }
        if (categoryConnector != null) {
            categoryConnector.close();
        }
    }
}