package com.techmarket.app.controller.RestControllers;


import com.techmarket.app.model.Product;
import com.techmarket.app.model.User;
import com.techmarket.app.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import com.techmarket.app.service.UserService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping(value="/cart" ,params = {"page", "size"})
    public ResponseEntity<Page<Product>> getCartProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> cartProducts = cartService.getCartProducts(pageable,user);

        return ResponseEntity.ok(cartProducts);

    }

    @GetMapping("/cart")
    public ResponseEntity<Page<Product>> getWishlistProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return null;
    }




}
