package com.starwars.demo.model.generic;

import lombok.Data;

import java.util.Map;

@Data
public class Support {

    private String contact;
    private String donate;
    private PartnerDiscount partnerDiscounts;

}
