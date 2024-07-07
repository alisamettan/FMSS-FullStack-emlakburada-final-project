package com.patika.emlakburada_package.repository;

import com.patika.emlakburada_package.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<UserPackage,Long> {

}
