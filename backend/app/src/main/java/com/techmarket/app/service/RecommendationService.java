package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RecommendationService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Product> getRecommendedProducts(){
        ArrayList<Purchase> purchases = getPurchases();
        List<Product> purchasedItems  = getProductsPurchased(purchases);
        List<String> tags = getTags(purchasedItems);
        List<Product> productsTag = getProductByTags(tags);
        List<Product> recommendedProducts = getAllProducts().stream()
                // Filter out products that don't have any of the tags
                .filter(product -> product.getTags().stream().anyMatch(tags::contains))
                // Filter out products that have already been purchased
                .filter(product -> !purchasedItems.contains(product))
                .limit(4)
                .collect(Collectors.toList());

        if (recommendedProducts.size() < 4) {
            // If we don't have enough products, fill the rest with random products
            // Use a set because it is faster to check if an item is in the set
            Set<Long> purchasedProductIds = purchasedItems.stream()
                    // Get the product IDs of the purchased items, so we can filter them out
                    .map(Product::getProductId)
                    .collect(Collectors.toSet());

            List<Product> remainingProducts = getAllProducts().stream()
                    // Filter out products that have already been purchased
                    .filter(product -> !purchasedProductIds.contains(product.getProductId()))
                    // Filter out products that are already in the recommended list
                    .filter(product -> !recommendedProducts.contains(product))
                    .limit(4 - recommendedProducts.size())
                    .toList();
            // Add the remaining products to the list
            recommendedProducts.addAll(remainingProducts);
        }
        return recommendedProducts;
    }

    public List<Product> getProductByTags(List<String> tags){
        List<Product> productsTag = new ArrayList<>();
        for (String tag : tags) {
            productsTag.addAll(productRepository.findAllByTags(tag));
        }
        return productsTag;
    }

    public List<String> getTags(List<Product> purchasedItems){
        List<String> tags = new ArrayList<>();
        for (Product purchasedItem : purchasedItems) {
            for (int j = 0; j < purchasedItem.getTags().size(); j++) {
                if (!tags.contains(purchasedItem.getTags().get(j))) {
                    tags.add(purchasedItem.getTags().get(j));
                }
            }
        }
        return tags;
    }
    public List<Product> getAllProducts(){
        List<Product> products;
        products = productRepository.findAll();
        return products;
    }
    public List<Product> getProductsPurchased(List<Purchase> purchases){
        List<Product> productsBought = new ArrayList<>();
        for (Purchase purchase : purchases) {
            productsBought.add(productRepository.findByProductId(purchase.getProduct().getProductId()));
        }
        return productsBought;
    }
    public ArrayList<Purchase> getPurchases (){
        ArrayList<Purchase> purchases;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User cuarrentUser = userRepository.findByEmail(auth.getName());
        purchases = purchaseRepository.findFirst10ByUserIdOrderByPurchaseIdDesc(cuarrentUser.getId());
        return purchases;
    }
}
