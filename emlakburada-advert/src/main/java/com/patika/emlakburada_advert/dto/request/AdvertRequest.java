package com.patika.emlakburada_advert.dto.request;

import com.patika.emlakburada_advert.entity.Image;
import com.patika.emlakburada_advert.entity.enums.AdvertType;
import com.patika.emlakburada_advert.entity.enums.HomeType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdvertRequest {
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
    private List<String> images;
}
