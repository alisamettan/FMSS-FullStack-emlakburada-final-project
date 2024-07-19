package com.patika.emlakburada_package.converter;

import com.patika.emlakburada_package.dto.request.PackageRequest;
import com.patika.emlakburada_package.entity.UserPackage;

public class PackageConverter {
    public static UserPackage toPackage(PackageRequest request) {
        UserPackage userPackage=new UserPackage();
        userPackage.setName(request.getName());
        userPackage.setType(request.getPackageType());
        userPackage.setPrice(request.getPrice());
        userPackage.setListingRights(request.getListingRights());

        return userPackage;
    }
}
