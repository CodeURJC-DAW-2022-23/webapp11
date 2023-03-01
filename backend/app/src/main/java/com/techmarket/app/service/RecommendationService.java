package com.techmarket.app.service;

import com.techmarket.app.Repositories.ProductRepository;
import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;


@Service
public class RecommendationService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getRecommendedProducts(){
        ArrayList<Purchase> purchases = getPurchases();
        List<Product> purchasedItems  = getProductsPurchased(purchases);
        List<String> tags = getTags(purchasedItems);
        List<Product> productsTag = getProductByTags(tags);
        List<Product> recommendedProducts = new ArrayList<>();

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

    public List<Product> getProductByTags(List<String> tags){
        List<Product> productsTag = new ArrayList<>();
        for(int i = 0; i<tags.size();i++){
            productsTag.addAll(productRepository.findAllByTags(tags.get(i)));
        }
        return productsTag;
    }

    public List<String> getTags(List<Product> purchasedItems){
        List<String> tags = new ArrayList<>();
        for(int i = 0; i< purchasedItems.size(); i++){
            for(int j = 0; j< purchasedItems.get(i).getTags().size(); j++){
                if (!tags.contains(purchasedItems.get(i).getTags().get(j))){
                    tags.add(purchasedItems.get(i).getTags().get(j));
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
