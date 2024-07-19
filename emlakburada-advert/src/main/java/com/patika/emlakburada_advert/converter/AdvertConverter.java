package com.patika.emlakburada_advert.converter;

import com.patika.emlakburada_advert.client.user.response.UserResponse;
import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.entity.Image;
import com.patika.emlakburada_advert.entity.enums.AdvertStatus;

import java.util.ArrayList;
import java.util.List;

public class AdvertConverter {
    public static Advert toAdvert(AdvertRequest request, UserResponse userResponse) {
        Advert advert=new Advert();
        advert.setUserId(request.getUserId());
        advert.setCity(request.getCity());
        advert.setDistrict(request.getDistrict());
        advert.setHomeType(request.getHomeType());
        advert.setAdvertType(request.getAdvertType());
        advert.setTitle(request.getTitle());
        advert.setDescription(request.getDescription());
        advert.setNumberOfRooms(request.getNumberOfRooms());
        advert.setNumberOfBath(request.getNumberOfBath());
        advert.setSquareMeters(request.getSquareMeters());
        advert.setPrice(request.getPrice());
        advert.setAdvertStatus(AdvertStatus.IN_REVIEW);
        advert.setIsPrioritized(userResponse.getIsPrioritized());

        // Convert AdvertRequest's image URLs to Image entities and add to Advert's images list
        List<Image> images = new ArrayList<>();
        for (String imageUrl : request.getImages()) {
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setAdvert(advert); // Set the advert reference
            images.add(image);
        }
        advert.setImages(images);

        return advert;
    }

    public static AdvertResponse toAdvertResponse(Advert advert) {
        AdvertResponse advertResponse = new AdvertResponse();

        advertResponse.setId(advert.getId());
        advertResponse.setUserId(advert.getUserId());
        advertResponse.setCity(advert.getCity());
        advertResponse.setDistrict(advert.getDistrict());
        advertResponse.setHomeType(advert.getHomeType());
        advertResponse.setAdvertType(advert.getAdvertType());
        advertResponse.setTitle(advert.getTitle());
        advertResponse.setDescription(advert.getDescription());
        advertResponse.setNumberOfRooms(advert.getNumberOfRooms());
        advertResponse.setNumberOfBath(advert.getNumberOfBath());
        advertResponse.setSquareMeters(advert.getSquareMeters());
        advertResponse.setPrice(advert.getPrice());
        advertResponse.setAdvertStatus(advert.getAdvertStatus());
        advertResponse.setIsPrioritized(advert.getIsPrioritized());
        advertResponse.setImages(advert.getImages());

        return advertResponse;
    }

    public static Advert toUpdatedAdvert(AdvertRequest request, Advert advert) {
        advert.setCity(request.getCity());
        advert.setDistrict(request.getDistrict());
        advert.setHomeType(request.getHomeType());
        advert.setAdvertType(request.getAdvertType());
        advert.setTitle(request.getTitle());
        advert.setDescription(request.getDescription());
        advert.setNumberOfRooms(request.getNumberOfRooms());
        advert.setNumberOfBath(request.getNumberOfBath());
        advert.setSquareMeters(request.getSquareMeters());
        advert.setPrice(request.getPrice());

        return advert;
    }
}
