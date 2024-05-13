package com.securityLatest.SpringSecurity.Controller;


import com.securityLatest.SpringSecurity.Dto.AuthRequest;
import com.securityLatest.SpringSecurity.Dto.Product;
import com.securityLatest.SpringSecurity.Service.JwtService;
import com.securityLatest.SpringSecurity.Service.ProductService;
import com.securityLatest.SpringSecurity.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController
{

    @Autowired
    private ProductService productService;
    @Autowired
    private JwtService jwtService;
@Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return productService.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return productService.getProduct(id);
    }

@PostMapping("/authenticate")
    public String getToken(@RequestBody AuthRequest authRequest)
{

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));

    if(authentication.isAuthenticated())
    {
        return jwtService.generateToken(authRequest.getUserName());
    }
    else {
        throw new UsernameNotFoundException("invalid credentials/user dooes not exists");
    }

}

}
