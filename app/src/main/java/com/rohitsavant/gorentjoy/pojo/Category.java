package com.rohitsavant.gorentjoy.pojo;

/**
 * Created by rohitsavant on 20/06/18.
 */

public class Category {
    String id_category,name,id_category_parent;

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_category_parent() {
        return id_category_parent;
    }

    public void setId_category_parent(String id_category_parent) {
        this.id_category_parent = id_category_parent;
    }
}
