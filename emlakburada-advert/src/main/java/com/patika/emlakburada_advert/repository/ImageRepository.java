package com.patika.emlakburada_advert.repository;

import com.patika.emlakburada_advert.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {

    @Query("SELECT i FROM Image i WHERE i.advert.id=:advertId")
    Optional<Image> findByAdvertId(Long advertId);
}
