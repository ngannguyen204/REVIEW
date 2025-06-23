package connectors;

import android.app.Activity;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import models.Product;

public class ProductConnector {
    private SQLiteConnector sqliteConnector;

    public ProductConnector(Activity context) {
        sqliteConnector = new SQLiteConnector(context);
        sqliteConnector.openDatabase(); // Mở kết nối database
    }

    public void close() {
        if (sqliteConnector.getDatabase() != null) {
            sqliteConnector.getDatabase().close();
        }
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = sqliteConnector.getDatabase().query(
                    "Products",
                    null,
                    "CateID = ?",
                    new String[]{String.valueOf(categoryId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getInt(cursor.getColumnIndexOrThrow("Id")));
                    product.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow("ProductCode")));
                    product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow("ProductName")));
                    product.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("UnitPrice")));
                    product.setImageLink(cursor.getString(cursor.getColumnIndexOrThrow("ImageLink")));
                    product.setCateid(cursor.getInt(cursor.getColumnIndexOrThrow("CateID")));
                    products.add(product);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return products;
    }

    public Product getProductById(int productId) {
        Cursor cursor = null;
        try {
            cursor = sqliteConnector.getDatabase().query(
                    "Products",
                    null,
                    "Id = ?",
                    new String[]{String.valueOf(productId)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow("Id")));
                product.setProductCode(cursor.getString(cursor.getColumnIndexOrThrow("ProductCode")));
                product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow("ProductName")));
                product.setUnitPrice(cursor.getDouble(cursor.getColumnIndexOrThrow("UnitPrice")));
                product.setImageLink(cursor.getString(cursor.getColumnIndexOrThrow("ImageLink")));
                product.setCateid(cursor.getInt(cursor.getColumnIndexOrThrow("CateID")));
                return product;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}