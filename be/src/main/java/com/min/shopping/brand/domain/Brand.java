package com.min.shopping.brand.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    public enum Status {
        ACTIVE, INACTIVE
    }

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BrandName name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Brand(final String name) {
        this.name = new BrandName(name);
        this.status = Status.INACTIVE;
    }

    public String getName() {
        return name.getName();
    }

    public void modifyName(final String name) {
        this.name = new BrandName(name);
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void inactivate() {
        this.status = Status.INACTIVE;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

}
