package com.example.ecommerce.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("user")
public class Users {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private String role;
    private Boolean isOauth;

    @DBRef
    private ArrayList<Address> listAddress = new ArrayList<>();

    @DBRef
    private ArrayList<Payment> listPayment;

    @DBRef
    private ArrayList<Rating> listRating;

    @DBRef
    private ArrayList<Review> listReview;

    private LocalDateTime createAt;

    // contructor
    public Users(String firstName, String lastName, String password, String email, String role,
            ArrayList<Address> listAddress, ArrayList<Payment> listPayment, ArrayList<Rating> listRating,
            ArrayList<Review> listReview, LocalDateTime createAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.listAddress = listAddress;
        this.listPayment = listPayment;
        this.listRating = listRating;
        this.listReview = listReview;
        this.createAt = createAt;
    }
    
    public Users() {
    }

    // getter and setter
    public String getId() {
        return id;
    }

    public String getUsername(){
        return username;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<Address> getListAddress() {
        return listAddress;
    }

    public void setListAddress(ArrayList<Address> listAddress) {
        this.listAddress = listAddress;
    }

    public ArrayList<Payment> getListPayment() {
        return listPayment;
    }

    public void setListPayment(ArrayList<Payment> listPayment) {
        this.listPayment = listPayment;
    }

    public ArrayList<Rating> getListRating() {
        return listRating;
    }

    public void setListRating(ArrayList<Rating> listRating) {
        this.listRating = listRating;
    }

    public ArrayList<Review> getListReview() {
        return listReview;
    }

    public void setListReview(ArrayList<Review> listReview) {
        this.listReview = listReview;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Boolean getIsOauth() {
        return isOauth;
    }

    public void setIsOauth(Boolean isOauth) {
        this.isOauth = isOauth;
    }

    
    
}
