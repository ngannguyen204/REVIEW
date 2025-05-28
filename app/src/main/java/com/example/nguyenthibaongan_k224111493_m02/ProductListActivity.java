package com.example.nguyenthibaongan_k224111493_m02;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import adapters.ProductAdapter;
import models.ListProduct;
import models.Product;

public class ProductListActivity extends AppCompatActivity {

    private ListView lvProduct;
    private ProductAdapter productAdapter;
    private ListProduct listProduct;
    private ActivityResultLauncher<Intent> addProductLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        setupProductList();
        setupActivityLauncher();
    }

    private void initializeViews() {
        lvProduct = findViewById(R.id.lvProduct);
    }

    private void setupProductList() {
        listProduct = new ListProduct();
        listProduct.generate_sample_products();

        productAdapter = new ProductAdapter(this,
                R.layout.item_product,
                listProduct.getProducts());

        lvProduct.setAdapter(productAdapter);
    }

    private void setupActivityLauncher() {
        addProductLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            Product newProduct = (Product) result.getData().getSerializableExtra("new_product");
                            if (newProduct != null) {
                                // Generate new ID (simple way)
                                newProduct.setId(listProduct.getProducts().size() + 1);
                                listProduct.addProduct(newProduct);
                                refreshProductList();
                                Toast.makeText(ProductListActivity.this,
                                        "Product added successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void refreshProductList() {
        listProduct.generate_sample_products();
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_new_product) {
            openAddProductActivity();
            return true;
        } else if (item.getItemId() == R.id.menu_about) {
            showAboutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddProductActivity() {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        addProductLauncher.launch(intent);
    }

    private void showAboutDialog() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}