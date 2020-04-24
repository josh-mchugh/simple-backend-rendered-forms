package com.jmchugh.demo.user.entity;

import com.jmchugh.demo.config.database.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "address")
public class AddressEntity extends AbstractEntity {

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "secondary_address")
    private String secondaryAddress;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;
}
