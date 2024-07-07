package com.patika.emlakburada_advert.converter;

import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.entity.Image;
import com.patika.emlakburada_advert.entity.enums.AdvertStatus;

import java.util.ArrayList;
import java.util.List;

public class AdvertConverter {
    public static Advert toAdvert(AdvertRequest request) {
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
        AdvertResponse advertResponse=new AdvertResponse(advert.getUserId(),advert.getCity(),
                advert.getDistrict(),
                advert.getHomeType(),
                advert.getAdvertType(),
                advert.getTitle(),
                advert.getDescription(),
                advert.getNumberOfRooms(),
                advert.getNumberOfBath(),
                advert.getSquareMeters(),
                advert.getPrice(),
                advert.getAdvertStatus(),
                advert.getImages());

        return advertResponse;
    }
}
