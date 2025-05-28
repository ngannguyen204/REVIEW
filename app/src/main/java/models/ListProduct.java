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

    public void generate_sample_products() {
        Product p1 = new Product(1, "P001", "Coca Cola", 10.0, R.mipmap.ic_coca);
        Product p2 = new Product(2, "P002", "Pepsi", 9.5, R.mipmap.ic_coca);
        Product p3 = new Product(3, "P003", "7Up", 8.0, R.mipmap.ic_sevenup);
        Product p4 = new Product(4, "P004", "Fanta", 8.5, R.mipmap.ic_fanta);
        Product p5 = new Product(5, "P005", "Sprite", 9.0, R.mipmap.ic_sprite);
        Product p6 = new Product(6, "P006", "Red Bull", 15.0, R.mipmap.ic_redbull);
        Product p7 = new Product(7, "P007", "Mountain Dew", 9.0, R.mipmap.ic_mountaindew);
        Product p8 = new Product(8, "P008", "Dr Pepper", 8.5, R.mipmap.ic_drpepper);
        Product p9 = new Product(9, "P009", "Mirinda", 7.5, R.mipmap.ic_mirinda);
        Product p10 = new Product(10, "P010", "Schweppes", 8.0, R.mipmap.ic_schweppes);
        Product p11 = new Product(11, "P011", "Lipton Ice Tea", 7.0, R.mipmap.ic_lipton);
        Product p12 = new Product(12, "P012", "Nestea", 7.0, R.mipmap.ic_nestea);
        Product p13 = new Product(13, "P013", "Aquafina", 5.0, R.mipmap.ic_aquafina);
        Product p14 = new Product(14, "P014", "Dasani", 5.5, R.mipmap.ic_dasani);
        Product p15 = new Product(15, "P015", "Evian", 12.0, R.mipmap.ic_evian);
        Product p16 = new Product(16, "P016", "Perrier", 14.0, R.mipmap.ic_perrier);
        Product p17 = new Product(17, "P017", "Vitamin Water", 10.0, R.mipmap.ic_vitaminwater);
        Product p18 = new Product(18, "P018", "Gatorade", 9.5, R.mipmap.ic_gatorade);
        Product p19 = new Product(19, "P019", "Powerade", 9.5, R.mipmap.ic_powerade);
        Product p20 = new Product(20, "P020", "Monster Energy", 12.5, R.mipmap.ic_monster);

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
        products.add(p5);
        products.add(p6);
        products.add(p7);
        products.add(p8);
        products.add(p9);
        products.add(p10);
        products.add(p11);
        products.add(p12);
        products.add(p13);
        products.add(p14);
        products.add(p15);
        products.add(p16);
        products.add(p17);
        products.add(p18);
        products.add(p19);
        products.add(p20);
    }
}