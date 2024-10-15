package com.min.shopping.brand.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BrandName name;

    public Brand(String name) {
        this.name = new BrandName(name);
    }

    public String getName() {
        return name.getName();
    }

    public void modifyName(final String name) {
        this.name = new BrandName(name);
    }
}
