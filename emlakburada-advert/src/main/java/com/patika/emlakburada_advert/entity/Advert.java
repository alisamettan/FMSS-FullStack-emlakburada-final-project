package com.patika.emlakburada_advert.entity;

import com.patika.emlakburada_advert.entity.enums.AdvertStatus;
import com.patika.emlakburada_advert.entity.enums.AdvertType;
import com.patika.emlakburada_advert.entity.enums.HomeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "advert",schema = "emlakburada")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Enumerated(EnumType.STRING)
    @Column(name = "home_type")
    private HomeType homeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "advert_type")
    private AdvertType advertType;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "number_of_bath")
    private Integer numberOfBath;

    @Column(name = "square_meters")
    private Double squareMeters;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_prioritized")
    private Boolean isPrioritized;

    @Enumerated(EnumType.STRING)
    @Column(name = "advert_status")
    private AdvertStatus advertStatus;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.ALL)
    private List<Image> images=new ArrayList<>();
}
