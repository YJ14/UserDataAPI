package com.jeries.reactive.user.data.api.model;//

import lombok.Getter;

@Getter
public class Address {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoLocation geo;

}
