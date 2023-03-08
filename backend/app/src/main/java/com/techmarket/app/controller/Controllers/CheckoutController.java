package com.techmarket.app.controller.Controllers;

import com.techmarket.app.Repositories.PurchaseRepository;
import com.techmarket.app.model.Product;
import com.techmarket.app.model.Purchase;
import com.techmarket.app.model.User;
import com.techmarket.app.Repositories.UserRepository;
import com.techmarket.app.service.PurchaseService;
import com.techmarket.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Controller
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        User user = userService.getUserName(authentication.getName());
        List<Product> cart = user.getShoppingCart();
        model.addAttribute("items", cart);

        double price = 0;
        for (Product product : cart) {
            price += product.getProductPrice();
        }
        model.addAttribute("totalPrice", price);


        return "checkout";

    }

    @PostMapping("/payout")
    public String payout(@RequestParam String address, Principal principal) {

        Authentication authentication = (Authentication) principal;
        User user = userService.getUserName(authentication.getName());
        List<Product> cart = user.getShoppingCart();
        if (cart.stream().allMatch(product -> product.getProductStock() > 0)) {
            for (Product product : cart) {
                if (!user.getPurchasedProducts().contains(product)) {
                    user.getPurchasedProducts().add(product);

                }
                product.setProductStock(product.getProductStock() - 1);
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();
                Purchase purchase = new Purchase();
                purchase.setProduct(product);
                purchase.setUser(user);
                purchase.setAddress(address);
                purchase.setCancelled(false);
                purchase.setPaymentMethod("Cash on delivery");
                purchase.setTimestamp(year + "-" + month + "-" + day);
                purchaseService.savePurchase(purchase);
            }

            user.getShoppingCart().clear();
            userService.saveUser(user);


        } else {
            return "redirect:/cart";
        }

        return "redirect:/purchases";
    }


}