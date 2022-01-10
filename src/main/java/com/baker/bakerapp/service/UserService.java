package com.baker.bakerapp.service;

import com.baker.bakerapp.model.Product;
import com.baker.bakerapp.model.Role;
import com.baker.bakerapp.model.User;
import com.baker.bakerapp.repository.UserRepository;
import com.baker.bakerapp.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProductService productService;

    @Autowired
    public UserService(UserRepository userRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return SecurityUser.fromUser(userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists")));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
    }

    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public User updateNewUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUserById(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User createNewUser(User user, String password) {
        user.setRole(Role.USER);
        user.setBalance(0);
        user.setPassword(password);
        return updateNewUser(user);
    }

    public String buyProduct(User user, Product product) {
        if (user.getBalance() >= product.getPrice()) {
            user.setBalance(user.getBalance() - product.getPrice());
            user.removeUserPackage(product);
            product.setAmount(product.getAmount() - 1);
            if (product.getAmount() == 0) {
                productService.deleteProductById(product.getId());
                updateNewUser(user);
                return "redirect:/user";
            }
            productService.updateNewProduct(product);
            updateNewUser(user);
        }
        return "redirect:/user";
    }

    public String donate(User user, int sum) {
        user.setBalance(user.getBalance() + sum);
        updateNewUser(user);
        return "redirect:/";
    }
}
