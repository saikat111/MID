package com.codingburg.mid.model;

public class ProductionCompanyData {
    private String logo_path;
    private  String name;
    private String origin_country;
    private String id;

    public ProductionCompanyData(String logo_path, String name, String origin_country, String id) {
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }
}

