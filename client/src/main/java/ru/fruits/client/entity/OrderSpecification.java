package ru.fruits.client.entity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderSpecification {
    public static Specification<Order> equalsName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order_.NAME), name);
    }

    public static Specification<Order> likeName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(Order_.NAME)),
                "%" + name.toLowerCase() + "%"
        );
    }

    public static Specification<Order> equalsPrice(Integer price) {
        if (price == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Order_.PRICE), price);
    }
}
