package com.patika.emlakburada_advert.dto.request;

import com.patika.emlakburada_advert.entity.enums.AdvertStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertStatusRequest {

    private Long id;
    private AdvertStatus advertStatus;
}
