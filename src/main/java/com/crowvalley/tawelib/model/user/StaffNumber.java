package com.crowvalley.tawelib.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StaffNumber {

    @Id
    @GeneratedValue
    private Long staffNum;

    public Long getStaffNum() {
        return staffNum;
    }

}
