package com.techmarket.app.service;

import com.techmarket.app.Repositories.*;
import com.techmarket.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class SampleDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //@PostConstruct

    public void createSampleData() throws IOException, SQLException {
        // Create users
        User admin = createUser("admin@example.com", "Admin", "admin123456", "ADMIN");
        User user = createUser("user@example.com", "Normal", "user123456", "USER");
        User agent = createUser("agent@example.com", "Agent", "agent123456", "AGENT");

        // Save users
        userRepository.save(admin);
        userRepository.save(agent);
        userRepository.save(user);

        // Create products
        Image mainImage = createImage("mainImage.jpg", "https://i.imgur.com/JAJDWcI.jpg");
        Image additionalImage1 = createImage("additionalImage1.jpg", "https://imgur.com/mopBDFC.jpg");

        List<String> tags = List.of("gpu", "msi", "gaming");
        Product product1 = createProduct("MSI Nvidia 4070Ti", "Very good GPU", tags, mainImage, List.of(additionalImage1));

        mainImage = createImage("mainImage.jpg", "https://imgur.com/sw6CL21.jpg");
        additionalImage1 = createImage("additionalImage1.jpg", "https://imgur.com/4LPMurb.jpg");

        tags = List.of("ryzen", "motherboard", "gaming");
        Product product2 = createProduct("Ryzen 9 + MSI motherboard", "it is very fast!", tags, mainImage, List.of(additionalImage1));

        mainImage = createImage("mainImage.jpg", "https://imgur.com/PYFGKHU.jpg");
        additionalImage1 = createImage("additionalImage1.jpg", "https://imgur.com/7oqGmFI.jpg");

        tags = List.of("Apple", "mac", "laptop");
        Product product3 = createProduct("14\" MacBook Pro - Silver", "A great companion for your everyday needs", tags, mainImage, List.of(additionalImage1));

        // Save products
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);


        // Create purchases
        purchaseRepository.save(new Purchase(1L, product1, "2022-04-20", "123 Main St", user, "Cash on delivery", false));
        purchaseRepository.save(new Purchase(2L, product2, "2022-04-20", "123 Main St", user, "Credit Card", true));
        purchaseRepository.save(new Purchase(3L, product3, "2022-04-20", "123 Main St", user, "Cash on delivery", false));

        // Add a review
        reviewRepository.save(new Review(1L, "Nice product", "This is a great product, I liked it very much", 5, user, product1));
    }

    private User createUser(String email, String firstName, String password, String role) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName("User");
        user.setEncodedPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(role));
        user.setAddress("123 Main St");
        user.setPhoneNumber("123-456-7890");
        user.setCity("City");
        user.setArea("Area");
        user.setCity("City");
        user.setPostcode("12345");
        user.setState("State");
        user.setCountry("Country");
        return user;
    }

    private Blob getImageBlob(String imageUrl) throws IOException, SQLException {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        return new SerialBlob(inputStream.readAllBytes());
    }

    private Image createImage(String fileName, String imageUrl) throws IOException, SQLException{
        Image image = new Image();
        image.setFileName(fileName);
        image.setImageBlob(getImageBlob(imageUrl));
        return image;
    }

    private Product createProduct(String productName, String description, List<String> tags,
                                  Image mainImage, List<Image> additionalImages) {
        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setTags(tags);
        product.setProductPrice(100.0);
        product.setProductStock(10);
        product.setProductPrices(List.of(100.0, 90.0, 80.0));
        product.setMainImage(mainImage);
        imageRepository.save(mainImage);
        product.setImages(additionalImages);
        imageRepository.saveAll(additionalImages);
        return product;
    }
}
