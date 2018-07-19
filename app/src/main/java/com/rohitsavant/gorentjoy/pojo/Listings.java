package com.rohitsavant.gorentjoy.pojo;

/**
 * Created by rohitsavant on 12/07/18.
 */

public class Listings {
    String id_ad,title,published,cf_plan,cf_security,description,price,thumb;
    GalleryImages[] images;

    public GalleryImages[] getImages() {
        return images;
    }

    public void setImages(GalleryImages[] images) {
        this.images = images;
    }

    public String getId_ad() {
        return id_ad;
    }

    public void setId_ad(String id_ad) {
        this.id_ad = id_ad;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getCf_plan() {
        return cf_plan;
    }

    public void setCf_plan(String cf_plan) {
        this.cf_plan = cf_plan;
    }

    public String getCf_security() {
        return cf_security;
    }

    public void setCf_security(String cf_security) {
        this.cf_security = cf_security;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
