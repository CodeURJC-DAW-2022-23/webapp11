package com.techmarket.app.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@Entity
@EnableAutoConfiguration
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String encodedPassword;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    // Role
    @ElementCollection(fetch = FetchType.EAGER)  // We want to load the roles when we load the user
    private List<String> roles;

    // The address could be another entity, but for simplicity we'll just store the information in the user entity
    private String phoneNumber;
    private String address;
    private String postcode;
    private String state;
    private String country;
    private String area;
    private String city;

    private Long passwordChangeToken;

    @OneToOne(cascade = CascadeType.ALL)  // If we delete the user, we want to delete the image as well
    private Image profilePicture;

    // Cascade to eliminate some stuff when the user is deleted, fetch lazy to avoid loading all the products when we load the user.
    // Lists are initialized to an empty list, so we can add products to it later without having to check if it's null
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> wishlist = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> shoppingCart = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    List<Product> purchasedProducts = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Message> messages = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> reviews = new ArrayList<>();



    public User(String email, String firstName,  String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address ="";
        this.postcode ="";
        this.state ="";
        this.country ="";
        this.area ="";
        this.city ="";
        this.phoneNumber ="";
        this.roles = new ArrayList<>();
        this.roles.add("USER");
        this.profilePicture = null;
        this.wishlist = new ArrayList<>();
        this.shoppingCart = new ArrayList<>();
        this.purchasedProducts = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.reviews = new ArrayList<>();
        long leftLimit = 10000L;
        long rightLimit = 99999L;
        this.passwordChangeToken = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    public User(){}

    public  String getEncodedPassword() {
        return this.encodedPassword;
    }

    public void setEncodedPassword( String password) {
        this.encodedPassword = password;
    }

    public  String getFirstName() {
        return this.firstName;
    }

    public void setFirstName( String firstName) {
        this.firstName = firstName;
    }

    public  String getLastName() {
        return this.lastName;
    }

    public void setLastName( String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Image getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Product> getWishlist() {
        return this.wishlist;
    }

    public List<Product> getShoppingCart() {
        return this.shoppingCart;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setToken(Long token) {
        this.passwordChangeToken = token;
    }
    public Long getToken() {
        return passwordChangeToken;
    }

    public void setWishlist(List<Product> wishlist) {
        this.wishlist = wishlist;
    }

    public void setShoppingCart(List<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public List<Product> getPurchasedProducts() {
        return this.purchasedProducts;
    }


    public List<Message> getMessages() {
        return this.messages;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public Long getPasswordChangeToken() {
        return passwordChangeToken;
    }

    public void setPasswordChangeToken(Long passwordChangeToken) {
        this.passwordChangeToken = passwordChangeToken;
    }

    public List<Product> getReviews() {
        return reviews;
    }

    public void setReviews(List<Product> reviews) {
        this.reviews = reviews;
    }
}

