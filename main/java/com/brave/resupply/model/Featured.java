package com.brave.chardb.model;

import org.springframework.data.annotation.Id;

/**
 * Created by dcohen on 3/4/15.
 */
public class Featured {
    @Id
    private String id;
    private String featured;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

}
