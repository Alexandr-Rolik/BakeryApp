package com.baker.bakerapp.controller;

import com.baker.bakerapp.model.Product;
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
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public AdminController(@Qualifier("productService") ProductService productService,
                           @Qualifier("userService") UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        User user = userService.getUserByLogin(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("listOfProducts", productService.findAllProducts());
    }

    @GetMapping
    public String getAdminPage() {
        return "adminPage";
    }

    @GetMapping("/addPage")
    public String getAddPage() {
        return "addPage";
    }


    @PostMapping("/add")
    public String addNewProduct(Product product) {
        productService.updateNewProduct(product);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("product_id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/admin";
    }

    @GetMapping("/editPage")
    public String getEditPage(@RequestParam("product_id") Long id, Model model) {
        model.addAttribute("product", productService.findProductById(id));
        return "editPage";
    }

    @PostMapping("/edit")
    public String editProduct(Product product) {
        productService.updateNewProduct(product);
        return "redirect:/admin";
    }
}
