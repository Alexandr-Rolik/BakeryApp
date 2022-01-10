package com.baker.bakerapp.controller;

import com.baker.bakerapp.model.User;
import com.baker.bakerapp.service.ProductService;
import com.baker.bakerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService,
                          @Qualifier("productService") ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @ModelAttribute
    public void addAttributes(Model model,
                              Authentication authentication) {
        User user = userService.getUserByLogin(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("listOfUserProducts", user.getProducts());
    }

    @GetMapping
    public String getUserPurchasePage() {
        return "bucketPage";
    }

    @PostMapping("/add")
    public String addToBucketProduct(@RequestParam("product_id") Long productId, @RequestParam("user_id") Long userId) {
        return productService.addToBucket(userService.findById(userId), productService.findProductById(productId), userService);
    }

    @PostMapping("/buy")
    public String buyProduct(@RequestParam("product_id") Long productId, @RequestParam("user_id") Long userId) {
        return userService.buyProduct(userService.findById(userId), productService.findProductById(productId));
    }

    @PostMapping("/donate")
    public String makeDonation(@RequestParam("user_id") Long userId, @RequestParam("sum") int sum) {
        return userService.donate(userService.findById(userId), sum);
    }

    @GetMapping("/depositPage")
    public String getDonatePage() {
        return "depositPage";
    }
}
