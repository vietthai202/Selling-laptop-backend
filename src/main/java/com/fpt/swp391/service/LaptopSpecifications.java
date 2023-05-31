package com.fpt.swp391.service;

import com.fpt.swp391.model.Laptop;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class LaptopSpecifications {
    public static Specification<Laptop> hasCategoryAndBrand(String categoryName, String brandName, String sortDirection) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (categoryName != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("name"), categoryName));
            }
            if (brandName != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("brand").get("name"), brandName));
            }
            query.orderBy(getSortExpression(root, criteriaBuilder, sortDirection));
            return predicate;
        };
    }

    private static javax.persistence.criteria.Order getSortExpression(Root<Laptop> root, CriteriaBuilder criteriaBuilder, String sortDirection) {
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            return criteriaBuilder.desc(root.get("price"));
        } else {
            return criteriaBuilder.asc(root.get("price"));
        }
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
