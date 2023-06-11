package com.fpt.swp391.service;

import com.fpt.swp391.model.Laptop;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LaptopSpecifications {

    public static Specification<Laptop> hasCategoryAndBrand(String categoryIds, String brandIds, String priceOrder, String sortDirection) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoryIds != null && !categoryIds.isEmpty()) {
                List<Long> categoryIdList = Arrays.stream(categoryIds.split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                predicates.add(root.get("category").get("id").in(categoryIdList));
            }

            if (brandIds != null && !brandIds.isEmpty()) {
                List<Long> brandIdList = Arrays.stream(brandIds.split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                predicates.add(root.get("brand").get("id").in(brandIdList));
            }

            List<javax.persistence.criteria.Order> orders = getSortByField(root, criteriaBuilder, priceOrder, sortDirection);
            query.orderBy(orders.toArray(new javax.persistence.criteria.Order[0]));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    private static List<javax.persistence.criteria.Order> getSortByField(Root<Laptop> root, CriteriaBuilder criteriaBuilder, String priceOrder, String sortDirection) {
        List<javax.persistence.criteria.Order> orders = new ArrayList<>();
        if (priceOrder != null && priceOrder.equalsIgnoreCase("asc")) {
            orders.add(criteriaBuilder.asc(root.get("price")));
        } else if (priceOrder != null && priceOrder.equalsIgnoreCase("desc")) {
            orders.add(criteriaBuilder.desc(root.get("price")));
        }

        if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
            orders.add(criteriaBuilder.asc(root.get("id")));
        } else if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            orders.add(criteriaBuilder.desc(root.get("id")));
        }

        if (orders.isEmpty()) {
            if (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) {
                orders.add(criteriaBuilder.asc(root.get("id")));
            } else {
                orders.add(criteriaBuilder.desc(root.get("id")));
            }
        }

        return orders;
    }


    public static Specification<Laptop> hasPriceBetween(Float minPrice, Float maxPrice) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (minPrice != null) {
                Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
                predicates.add(minPricePredicate);
            }
            if (maxPrice != null) {
                Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
                predicates.add(maxPricePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
