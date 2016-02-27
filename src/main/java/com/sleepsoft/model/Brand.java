package com.sleepsoft.model;

/**
 * Created by Alejandro on 2/26/16.
 */
public class Brand {
    private int id;
    private String domain;

    public Brand(int id, String domain) {
        this.id = id;
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (id != brand.id) return false;
        return domain != null ? domain.equals(brand.domain) : brand.domain == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        return result;
    }
}
