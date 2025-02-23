package com.scrapper.entities;

public class Address {
    private String street;
    private String estate;
    private String district;
    private String postalCode;
    private String cityName;
    private String province;

    public Address() {
    }

    public Address(String province, String cityName, String district) {
        this.province = province;
        this.cityName = cityName;
        this.district = district;
    }

    public Address(String province, String cityName, String district, String estate) {
        this.province = province;
        this.cityName = cityName;
        this.district = district;
        this.estate = estate;
    }

    public Address(String province, String cityName, String district, String estate, String street) {
        this.province = province;
        this.cityName = cityName;
        this.district = district;
        this.estate = estate;
        this.street = street;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }
}
