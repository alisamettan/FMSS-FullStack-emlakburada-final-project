package com.patika.emlakburada_package.dto.request;

import com.patika.emlakburada_package.entity.enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackageRequest {
    private String name;
    private PackageType packageType;
    private Double price;
    private Integer listingRights;
}
