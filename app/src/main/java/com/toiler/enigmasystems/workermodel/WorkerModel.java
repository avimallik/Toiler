package com.toiler.enigmasystems.workermodel;

/**
 * Created by Arm_AVI on 8/9/2018.
 */

public class WorkerModel {
    String name = null, phone = null, address = null,
            designation = null, area_name = null,
            city = null , latitude = null,
            longitude = null, gender = null,
            experience = null, description = null,
            postcode = null, account_type = null, image = "", status;



    public WorkerModel(String name, String phone, String address, String designation, String area_name, String city,
                       String latitude, String longitude, String gender, String experience, String description,
                       String postcode, String account_type, String image, String status) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.designation = designation;
        this.area_name = area_name;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.gender = gender;
        this.experience = experience;
        this.description = description;

        this.postcode = postcode;
        this.account_type = account_type;
        this.image = image;
        this.status = status;
//        this.status = status;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return  name + " " + phone + " " + address + " " + designation + " " + area_name + " " + city+ " " + latitude+ " " + longitude
                + " " + gender+ " " + experience+ " " + description+ " " + postcode+ " " + account_type +status+ image;
    }
}
