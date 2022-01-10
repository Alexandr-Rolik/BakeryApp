package com.baker.bakerapp.service;

import com.baker.bakerapp.model.Product;
import com.baker.bakerapp.model.User;
import com.baker.bakerapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(Long id) {
        return productRepository.getById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateNewProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public String addToBucket(User user, Product product, UserService userService) {
        user.addUserPackage(product);
        userService.updateNewUser(user);
        return "redirect:/";
    }
}
