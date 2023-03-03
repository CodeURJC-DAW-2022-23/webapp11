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

import java.util.List;
import java.util.ArrayList;


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
        List<Product> recommendedProducts = new ArrayList<>();

        // Remove products already purchased
        for(int i = 0; i<productsTag.size(); i++){
            for(int j = 0; j<purchasedItems.size(); j++){
                if(productsTag.get(i).getProductId() == purchasedItems.get(j).getProductId()){
                    productsTag.remove(i);
                }
            }
        }

        // Get 4 products based on tags
        for (int i = 0; i<4; i++){
            if (i < productsTag.size()){
                recommendedProducts.add(productsTag.get(i));
            }
        }
        if ((recommendedProducts.size()<3) && (recommendedProducts.size() != 0)){
            List<Product> productsList = getAllProducts();
            int i = recommendedProducts.size();
            while((i < 4)&&(i<productsList.size())){
                int random = (int) (Math.random()*productsList.size());
                if (!recommendedProducts.contains(productsList.get(random))) {
                    recommendedProducts.add(productsList.get(random));
                    i++;
                }
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User cuarrentUser = userRepository.findByEmail(auth.getName());
        purchases = purchaseRepository.findFirst10Byuser_idOrderByPurchaseIdDesc(cuarrentUser.getId());
        return purchases;
    }
}
