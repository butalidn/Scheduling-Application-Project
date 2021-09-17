package Model;

import javafx.scene.control.cell.PropertyValueFactory;

public class Customer {
        private int id;
        private String name;
        private String address;
        private String postal;
        private String phone;
        private String firstLevel;
        private String country;

    public Customer(int id, String name, String address, String postal, String phone, String firstLevel, String country) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.postal = postal;
            this.phone = phone;
            this.firstLevel = firstLevel;
            this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostal() {
        return postal;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public String getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
