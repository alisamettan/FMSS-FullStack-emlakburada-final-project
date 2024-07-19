package com.patika.emlakburada_package.entity;


import com.patika.emlakburada_package.entity.enums.PackageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "package",schema = "emlakburada")
public class UserPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type")
    private PackageType type;

    private Double price;

    private Integer listingRights;


}
