package com.patika.emlakburada_advert.dto.response;

import com.patika.emlakburada_advert.entity.Image;
import com.patika.emlakburada_advert.entity.enums.AdvertStatus;
import com.patika.emlakburada_advert.entity.enums.AdvertType;
import com.patika.emlakburada_advert.entity.enums.HomeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AdvertResponse {
    private Long userId;
    private String city;
    private String district;
    private HomeType homeType;
    private AdvertType advertType;
    private String title;
    private String description;
    private Integer numberOfRooms;
    private Integer numberOfBath;
    private Double squareMeters;
    private Double price;
    private AdvertStatus advertStatus;
    private List<Image> images;
}
