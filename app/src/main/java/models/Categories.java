package models;

import java.io.Serializable;

public class Categories implements Serializable {
    private int id;
    private String CateName;

    public Categories() {
    }

    public Categories(int id, String cateName) {
        this.id = id;
        CateName = cateName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }
}
