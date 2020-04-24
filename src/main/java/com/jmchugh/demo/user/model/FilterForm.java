package com.jmchugh.demo.user.model;

import com.jmchugh.demo.user.entity.UserStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class FilterForm {

    private String search;
    private List<UserStatus> selectedStatuses = new ArrayList<>();

    private List<UserStatus> userStatuses = Arrays.asList(UserStatus.values());

    public boolean isStatusChecked(UserStatus status) {

        return selectedStatuses.contains(status);
    }
}
