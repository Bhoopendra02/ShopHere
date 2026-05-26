package com.example.ShopHere.Service;

import com.example.ShopHere.Dto.LoginRequest;
import com.example.ShopHere.Dto.UserRequest;
import com.example.ShopHere.Dto.UserResponse;
import com.example.ShopHere.Entity.Role;
import com.example.ShopHere.Entity.User;
import com.example.ShopHere.Exception.InvalidCredentialsException;
import com.example.ShopHere.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserResponse register(UserRequest request){

        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email Already exist");
        }

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepo.save(user);

        return modelMapper.map(savedUser,UserResponse.class);
    }

    public UserResponse login(LoginRequest request){
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(()->
                new InvalidCredentialsException("Invalid password or email"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid password or email");
        }
        return modelMapper.map(user, UserResponse.class);
    }
}
