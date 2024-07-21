package com.patika.emlakburada_package.dto.response;


import com.patika.emlakburada_package.entity.enums.PackageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackageResponse {
    private Long id;
    private String name;
    private PackageType packageType;
    private Double price;
    private Integer listingRights;
}
