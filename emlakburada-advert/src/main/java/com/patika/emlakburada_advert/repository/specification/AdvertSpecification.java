package com.patika.emlakburada_advert.repository.specification;

import com.patika.emlakburada_advert.dto.request.AdvertSearchRequest;
import com.patika.emlakburada_advert.entity.Advert;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertSpecification {

    public static Specification<Advert> initProductSpecification(AdvertSearchRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if(request.getTitle()!=null && !request.getTitle().isEmpty()){
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%"+request.getTitle().toLowerCase()+"%"));
            }


            if (request.getSort() != null && !request.getSort().isEmpty()) {
                if (request.getSort().equalsIgnoreCase("price:asc")) {
                    query.orderBy(
                            criteriaBuilder.asc(root.get("price"))
                    );
                } else if (request.getSort().equalsIgnoreCase("price:desc")) {
                    query.orderBy(
                            criteriaBuilder.desc(root.get("price"))
                    );
                }
            }


            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
