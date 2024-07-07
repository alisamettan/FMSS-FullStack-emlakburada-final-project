package com.patika.emlakburada_package.dto.request;

import com.patika.emlakburada_package.entity.enums.PackageType;
import lombok.Getter;


@Getter
public class PackageRequest {
    private String name;
    private PackageType packageType;
    private Double price;
}
