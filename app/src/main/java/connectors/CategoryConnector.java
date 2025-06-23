package connectors;

import android.app.Activity;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import models.Categories;

public class CategoryConnector {
    private SQLiteConnector sqliteConnector;

    public CategoryConnector(Activity context) {
        sqliteConnector = new SQLiteConnector(context);
        sqliteConnector.openDatabase(); // Mở kết nối database
    }

    public void close() {
        if (sqliteConnector.getDatabase() != null) {
            sqliteConnector.getDatabase().close();
        }
    }

    public List<Categories> getAllCategories() {
        List<Categories> categories = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = sqliteConnector.getDatabase().query(
                    "Categories",
                    null, // columns (null để lấy tất cả)
                    null, // selection
                    null, // selectionArgs
                    null, // groupBy
                    null, // having
                    null  // orderBy
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Categories category = new Categories();
                    category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    category.setCateName(cursor.getString(cursor.getColumnIndexOrThrow("CateName")));
                    categories.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return categories;
    }

    public String getCategoryNameById(int id) {
        Cursor cursor = null;
        try {
            cursor = sqliteConnector.getDatabase().query(
                    "Categories",
                    new String[]{"CateName"},
                    "ID = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }
}