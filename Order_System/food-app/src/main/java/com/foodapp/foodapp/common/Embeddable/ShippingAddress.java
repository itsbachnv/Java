package com.foodapp.foodapp.common.Embeddable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ShippingAddress {
    private String fullName, phone, street, ward, district, province;
}
