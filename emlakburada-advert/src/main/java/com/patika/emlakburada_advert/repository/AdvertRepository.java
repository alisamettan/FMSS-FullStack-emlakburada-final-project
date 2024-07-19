package com.patika.emlakburada_advert.repository;

import com.patika.emlakburada_advert.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long>, JpaSpecificationExecutor<Advert> {

    @Query("SELECT a FROM Advert a WHERE a.userId=:userId")
    List<Optional<Advert>> findByUserId(Long userId);
}
