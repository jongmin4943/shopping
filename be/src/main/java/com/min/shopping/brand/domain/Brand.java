package com.min.shopping.brand.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BrandName name;

    protected Brand() {
    }

    public Brand(String name) {
        this.name = new BrandName(name);
    }

    public String getName() {
        return name.getName();
    }
}
