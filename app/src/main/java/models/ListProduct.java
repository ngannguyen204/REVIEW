package models;



import com.example.nguyenthibaongan_k224111493_m02.R;


import java.util.ArrayList;

import models.Product;

public class ListProduct {
    private ArrayList<Product> products;

    public ListProduct() {
        products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


}