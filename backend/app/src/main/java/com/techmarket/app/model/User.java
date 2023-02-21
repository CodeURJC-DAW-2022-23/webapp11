package com.techmarket.app.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

@Entity
@EnableAutoConfiguration
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String encodedPassword;
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String email;

    // Role
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    private String phoneNumber;
    private String address;
    private String postcode;
    private String state;
    private String country;
    private String area;
    private String city;
    //type of user
    private String userType;
    @OneToOne
    private Image profilePicture;

    @OneToMany
    private List<Product> wishlist;
    @OneToMany
    private List<Product> shoppingCart;
    @OneToMany
    List<Purchase> purchasedProducts;
    @OneToMany
    List<Message> messages;

    public User() {
    }

    public User(Long id, @NotNull String encodedPassword, @NotNull String firstName, @NotNull String lastName, @NotNull String email, String userType) {
        this.id = id;
        this.encodedPassword = encodedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userType = userType;
    }

    // To sign up and login
    public User(@NotNull String email, @NotNull String encodedPassword, @NotNull String firstName, @NotNull String lastName, String... roles) {
        this.encodedPassword = encodedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        // Default role is USER
        this.roles = List.of(roles);
    }

    public @NotNull String getEncodedPassword() {
        return this.encodedPassword;
    }

    public void setEncodedPassword(@NotNull String password) {
        this.encodedPassword = password;
    }

    public @NotNull String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public @NotNull String getLastName() {
        return this.lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public @NotNull String getEmail() {
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setWishlist(List<Product> wishlist) {
        this.wishlist = wishlist;
    }

    public void setShoppingCart(List<Product> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void setPurchasedProducts(List<Purchase> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public List<Purchase> getPurchasedProducts() {
        return this.purchasedProducts;
    }


    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

