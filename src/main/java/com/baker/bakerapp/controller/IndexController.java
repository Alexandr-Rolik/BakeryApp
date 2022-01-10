package com.baker.bakerapp.controller;

import com.baker.bakerapp.model.User;
import com.baker.bakerapp.service.ProductService;
import com.baker.bakerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public IndexController(@Qualifier("userService") UserService userService,
                           @Qualifier("productService") ProductService productService) {
        this.productService = productService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addAttributes(Model model,
                              Authentication authentication) {
        if (authentication != null) {
            User user = userService.getUserByLogin(authentication.getName());
            model.addAttribute("user", user);
        }
        model.addAttribute("listOfProducts", productService.findAllProducts());
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }
}
