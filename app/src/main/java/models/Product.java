package models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String productCode;
    private String productName;
    private double unitPrice;
    private String imageLink;
    private int cateid;


    public Product() {
    }

    public Product(int id, String productCode, String productName, double unitPrice, String imageLink, int cateid) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.imageLink = imageLink;
        this.cateid = cateid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getCateid() {
        return cateid;
    }

    public void setCateid(int cateid) {
        this.cateid = cateid;
    }
}
