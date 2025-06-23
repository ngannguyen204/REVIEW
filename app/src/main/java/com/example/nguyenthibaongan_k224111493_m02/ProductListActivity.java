package com.example.nguyenthibaongan_k224111493_m02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import adapters.ProductAdapter;
import connectors.ProductConnector;
import models.Product;

public class ProductListActivity extends AppCompatActivity {
    private ListView lvProduct;
    private ProductConnector productConnector;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);

        lvProduct = findViewById(R.id.lvProduct);

        // Get category ID from intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        // Khởi tạo ProductConnector
        productConnector = new ProductConnector(this);

        // Load products by category
        products = productConnector.getProductsByCategory(categoryId);

        // Set up adapter
        ProductAdapter adapter = new ProductAdapter(this, R.layout.item_product_list, products);
        lvProduct.setAdapter(adapter);

        // Set item click listener
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = products.get(position);

                // Start ProductDetailActivity with product ID
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("PRODUCT_ID", selectedProduct.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (productConnector != null) {
            productConnector.close();
        }
    }
}