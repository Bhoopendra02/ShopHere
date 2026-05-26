package com.example.ShopHere.Controller;

import com.example.ShopHere.Dto.CategoryRequest;
import com.example.ShopHere.Dto.CategoryResponse;
import com.example.ShopHere.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request){

        CategoryResponse response =categoryService.createCategory(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get/page")
    public ResponseEntity<List<CategoryResponse>> getAllPagination(@RequestParam int page,
                                                                   @RequestParam int size){
        return ResponseEntity.ok(categoryService.getAllWithPagination(page,size));
    }
}
