package com.example.ShopHere.Controller;

import com.example.ShopHere.Dto.ProductRequest;
import com.example.ShopHere.Dto.ProductResponse;
import com.example.ShopHere.Entity.Product;
import com.example.ShopHere.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    // API for Creating the Product //
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request){
        ProductResponse response =productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // API for getting the Product //
    @GetMapping
    public List<ProductResponse> getAll(){
        return productService.getAllProducts();
    }


    @GetMapping
    public List<ProductResponse> getAllWithPagination(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        return productService.getAllProductsWithPagination(page, size);
    }


    // API for Getting the Product by Id //
    @GetMapping("/products/{id}")
    public ProductResponse getById(@PathVariable Long id){
        return productService.getProduct(id);
    }


    // API to Update Product //
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
                          @RequestBody ProductRequest request){
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    
    // API to Delete Product //
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Successfully Deleted");
    }

}
