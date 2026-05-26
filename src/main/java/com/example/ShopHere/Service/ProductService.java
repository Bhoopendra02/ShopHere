package com.example.ShopHere.Service;

import com.example.ShopHere.Dto.CategoryRequest;
import com.example.ShopHere.Dto.ProductRequest;
import com.example.ShopHere.Dto.ProductResponse;
import com.example.ShopHere.Entity.Category;
import com.example.ShopHere.Entity.Product;
import com.example.ShopHere.Exception.ProductAlreadyExitsException;
import com.example.ShopHere.Exception.ResourceNotFoundException;
import com.example.ShopHere.Repository.CategoryRepo;
import com.example.ShopHere.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    // Creating The Product //
    public ProductResponse createProduct(ProductRequest request){
        Category category =categoryRepo.findById(request.getCategoryId()).orElseThrow(()->
                new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Product product = modelMapper.map(request,Product.class);

        Optional<Product> existProduct = productRepo.findByName(product.getName());
        if(existProduct.isPresent()){
            throw new ProductAlreadyExitsException("Product Already exists");
        }

        product.setCategory(category);
        Product saved = productRepo.save(product);
        ProductResponse response = modelMapper.map(saved,ProductResponse.class);
        response.setCategoryId(category.getId());
        return response;
    }


    public List<ProductResponse> getAllProducts(){
        return productRepo.findAll().stream().map(product ->
                modelMapper.map(product,ProductResponse.class)).toList();
    }

    public List<ProductResponse> getAllProductsWithPagination(int page, int size){
        return productRepo.findAll(PageRequest.of(page,size))
                .stream()
                .map(product -> modelMapper.map(product,ProductResponse.class))
                .toList();
    }


    public ProductResponse getProduct(Long id){
        Product product= productRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found at id: " +id));
        return modelMapper.map(product, ProductResponse.class);
    }


    public  ProductResponse updateProduct(Long id, ProductRequest request){

        Product product = productRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found at id: " +id));
        Category category =categoryRepo.findById(request.getCategoryId()).orElseThrow(()->
                new ResourceNotFoundException("Category not found with id: " +request.getCategoryId()));


        modelMapper.map(request,product);

        product.setCategory(category);
        Product updated =productRepo.save(product);

        ProductResponse response= modelMapper.map(updated, ProductResponse.class);
        response.setCategoryId(category.getId());
        return response;
    }


    public void deleteProduct(Long id){
        Product product = productRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found at id: "+id));
        productRepo.delete(product);
    }
}
