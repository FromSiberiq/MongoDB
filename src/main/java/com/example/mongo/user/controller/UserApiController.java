package com.example.mongo.user.controller;


import com.example.mongo.user.dto.request.CreateUserRequest;
import com.example.mongo.user.dto.response.UserResponse;
import com.example.mongo.user.entity.UserDoc;
import com.example.mongo.user.exception.ObjectParseException;
import com.example.mongo.user.exception.UserNotFoundException;
import com.example.mongo.user.repository.UserRepository;
import com.example.mongo.user.routes.UserRoutes;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserApiController {
    private final UserRepository userRepository;

    @GetMapping("/")
    public UserDoc test() {
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
    public UserResponse create(@RequestBody CreateUserRequest request) {
        UserDoc userDoc = UserDoc.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .build();

        userDoc = userRepository.save(userDoc);
        return UserResponse.of(userDoc);
    }

    @GetMapping(UserRoutes.BY_ID)
    public UserResponse findById(@PathVariable String id) throws UserNotFoundException, ObjectParseException {
        if (!ObjectId.isValid(id)) throw new ObjectParseException();

        UserDoc userDoc = userRepository.findById(new ObjectId(id)).orElseThrow(UserNotFoundException::new);
        return UserResponse.of(userDoc);
    }

    @GetMapping(UserRoutes.SEARCH)
    public List<UserResponse> search(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDoc> users = userRepository.findAll(pageable);

        return users.getContent().stream().map(UserResponse::of).collect(Collectors.toList());
    }
}
