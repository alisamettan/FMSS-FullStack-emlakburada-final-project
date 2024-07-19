package com.patika.emlakburada_advert.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertSearchRequest extends BaseSearchRequest{
    private String title;
    private String sort;
}
