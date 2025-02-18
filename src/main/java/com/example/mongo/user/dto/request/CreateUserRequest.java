package com.example.mongo.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserRequest {
    private String firstName;
    private String lastName;
}
