package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class RecommendationService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    public ArrayList<Product> getRecommendedProducts(){
        ArrayList<Purchase> purchases = getPurchases();
        ArrayList<Product> purchasedItems  = getProductsPurchased(purchases);
        ArrayList<String> tags = getTags(purchasedItems);
        ArrayList<Product> productsTag = getProductByTags(tags);
        ArrayList<Product> recommendedProducts = new ArrayList<>();

        int z = 0;
        int number;
        for(int i = 0; i<4; i++){
            if (z < productsTag.size()){
                number = (int)(Math.random()*productsTag.size());
                recommendedProducts.add(productsTag.get(number));
            }
        }
        return recommendedProducts;
    }

    public ArrayList<Product> getProductByTags(ArrayList<String> tags){
        ArrayList<Product> productsTag = new ArrayList<>();
        for(int i = 0; i<tags.size();i++){
            productsTag.addAll(productRepository.findAllByTags(tags.get(i)));
        }
        return productsTag;
    }

    public ArrayList<String> getTags(ArrayList<Product> purchasedItems){
        ArrayList<String> tags = new ArrayList<>();
        for(int i = 0; i< purchasedItems.size(); i++){
            for(int j = 0; j< purchasedItems.get(i).getTags().size(); j++){
                if (!tags.contains(purchasedItems.get(i).getTags().get(j))){
                    tags.add(purchasedItems.get(i).getTags().get(j));
                }
            }
        }
        return tags;
    }
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> products;
        products = productRepository.findAll();
        return products;
    }
    public ArrayList<Product> getProductsPurchased(ArrayList<Purchase> purchases){
        ArrayList<Product> productsBought = new ArrayList<>();
        for(int i = 0;i<purchases.size();i++){
            productsBought.add(productRepository.findByProductId(purchases.get(i).getProduct().getProductId()));
        }
        return productsBought;
    }
    public ArrayList<Purchase> getPurchases (){
        ArrayList<Purchase> purchases;
        purchases = purchaseRepository.findFirst10ByOrderByPurchaseIdDesc();
        return purchases;
    }
}
