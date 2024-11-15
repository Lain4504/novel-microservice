package com.backend.profileservice.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileUpdateRequest {
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String street;
    String ward;
    String district;
    String province;

}