package com.example.myProject.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
public class Male implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany
    List<Category> categories;

    public Male() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
