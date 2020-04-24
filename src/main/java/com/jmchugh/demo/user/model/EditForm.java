package com.jmchugh.demo.user.model;

import com.google.common.collect.Lists;
import com.jmchugh.demo.user.entity.UserStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EditForm {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private UserStatus status;

    @NotBlank
    private String streetAddress;

    private String secondaryAddress;

    private String buildingNumber;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    private List<UserStatus> statuses = Lists.newArrayList(UserStatus.values());
}
