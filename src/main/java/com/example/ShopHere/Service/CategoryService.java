package com.example.ShopHere.Service;

import com.example.ShopHere.Dto.CategoryRequest;
import com.example.ShopHere.Dto.CategoryResponse;
import com.example.ShopHere.Entity.Category;
import com.example.ShopHere.Repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryResponse createCategory(CategoryRequest request){
         if(categoryRepo.findByName(request.getName()).isPresent()){
             throw new RuntimeException("Category already exists");
         }

        Category category =modelMapper.map(request,Category.class);

         Category saved = categoryRepo.save(category);

         return modelMapper.map(saved, CategoryResponse.class);
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepo.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
    }

    public List<CategoryResponse> getAllWithPagination(int page, int size){
        return categoryRepo.findAll(PageRequest.of(page, size))
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
    }
}
