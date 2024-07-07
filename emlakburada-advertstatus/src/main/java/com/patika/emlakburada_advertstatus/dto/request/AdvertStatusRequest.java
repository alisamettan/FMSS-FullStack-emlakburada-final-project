package com.patika.emlakburada_advertstatus.dto.request;


import com.patika.emlakburada_advertstatus.entity.enums.AdvertStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertStatusRequest {

    private Long id;
    private AdvertStatus advertStatus;
}
