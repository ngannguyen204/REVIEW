package com.example.nguyenthibaongan_k224111493_m02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import adapters.CategoryAdapter;
import connectors.CategoryConnector;
import models.Categories;

public class CategoryManagementActivity extends AppCompatActivity {
    private ListView lvCategory;
    private CategoryConnector categoryConnector;
    private List<Categories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_management);

        lvCategory = findViewById(R.id.lvCategory);

        // Khởi tạo CategoryConnector (tự động mở kết nối database trong constructor)
        categoryConnector = new CategoryConnector(this);

        // Load categories
        categories = categoryConnector.getAllCategories();

        // Set up adapter
        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.item_category, categories);
        lvCategory.setAdapter(adapter);

        // Set item click listener
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categories selectedCategory = categories.get(position);

                // Start ProductListActivity with category ID
                Intent intent = new Intent(CategoryManagementActivity.this, ProductListActivity.class);
                intent.putExtra("CATEGORY_ID", selectedCategory.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (categoryConnector != null) {
            categoryConnector.close();
        }
    }
}