package com.example.mongo.user.controller;


import com.example.mongo.user.dto.request.CreateUserRequest;
import com.example.mongo.user.dto.response.UserResponse;
import com.example.mongo.user.entity.UserDoc;
import com.example.mongo.user.repository.UserRepository;
import com.example.mongo.user.routes.UserRoutes;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserApiController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public UserDoc test(){
        List<UserDoc> findTest = userRepository.findByFirstName("Test");
        List<UserDoc> findTestList = userRepository.findByLastName("Test");

        UserDoc test1 = UserDoc.builder()
                .id(new ObjectId())
                .firstName("Test")
                .lastName("Test")
                .build();
        userRepository.save(test1);
        return test1;
    }

    @PostMapping(UserRoutes.CREATE)
    public UserResponse create(@RequestBody CreateUserRequest request){
        UserDoc userDoc = UserDoc.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();

        userDoc = userRepository.save(userDoc);
        return UserResponse.of(userDoc);
    }
}
