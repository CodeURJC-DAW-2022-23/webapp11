package com.techmarket.app.service;

import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email not found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEncodedPassword(), roles);
    }

    public User getUserName(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUserByRole(String role) {
        return userRepository.findByRoles(role);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getCurrentUser(HttpServletRequest request) {
        String email = request.getUserPrincipal().getName();
        return getUserName(email);
    }

    public Page<Purchase> getPurchaseHistory(User user, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Purchase> purchaseHistory = purchaseRepository.findByUserIdOrderByTimestampDesc(user.getId(), pageable);

        // Just return the purchased product, the timestamp and the payment method
        return purchaseHistory.map(purchase -> {  // .map is used to transform the elements of a stream, handy to filter out some fields
            Purchase filteredPurchase = new Purchase();
            // Remove the review from the product
            purchase.getProduct().setReviews(Collections.emptyList());
            filteredPurchase.setProduct(purchase.getProduct());
            filteredPurchase.setPurchaseId(purchase.getPurchaseId());
            filteredPurchase.setTimestamp(purchase.getTimestamp());
            filteredPurchase.setPaymentMethod(purchase.getPaymentMethod());
            filteredPurchase.setCancelled(purchase.isCancelled());
            filteredPurchase.setAddress(purchase.getAddress());
            return filteredPurchase;
        });
    }
}
