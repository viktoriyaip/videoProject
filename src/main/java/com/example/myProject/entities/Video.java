package com.example.myProject.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String url;

    @ManyToOne
    private Category category;

    public Video(String url, Category category) {

        this.url = url;
        this.category = category;
    }

    public Video(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
