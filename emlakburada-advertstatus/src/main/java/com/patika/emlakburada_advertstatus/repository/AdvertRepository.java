package com.patika.emlakburada_advertstatus.repository;

import com.patika.emlakburada_advertstatus.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
}
