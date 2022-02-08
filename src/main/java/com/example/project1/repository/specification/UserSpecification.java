package com.example.project1.repository.specification;


import com.example.project1.entity.User;
import com.example.project1.service.UserFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserSpecification implements Specification<User> {

    private final List<String> fields = Arrays.asList("firstName", "lastName", "email");

    private final UserFilter userFilter;

    public UserSpecification(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    private Predicate findById(Root<User> root, CriteriaBuilder cb, Long id) {
        return cb.equal(root.get("id"), id);
    }

//    private Predicate findByAddressStreet(Root<User> root, CriteriaBuilder cb, String field, String name) {
//        return cb.like(root.join("address").get("street"), "%" + name + "%");
//    }

    private Predicate findAllByName(Root<User> root, CriteriaBuilder cb, String field, String value) {
        return cb.like(root.get(field), "%" + value + "%");
    }

    private Predicate findAllByNameLike(Root<User> root, CriteriaBuilder cb, String firstName) {
        return cb.like(root.get("firstName"), "%" + firstName + "%");
    }

    private Predicate findAllByLastNameLike(Root<User> root, CriteriaBuilder cb, String lastName) {
        return cb.like(root.get("lastName"), "%" + lastName + "%");
    }

    private Predicate findAllByEmailLike(Root<User> root, CriteriaBuilder cb, String email) {
        return cb.like(root.get("email"), "%" + email + "%");
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate mainPredicate;

        List<Predicate> andPredicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();

        List<Predicate> orNamePredicates = new ArrayList<>();

        if (Objects.nonNull(userFilter) && Objects.nonNull(userFilter.getFirstName()) && !Objects.equals(userFilter.getFirstName(), "")) {
            orPredicates.add(findAllByNameLike(root, criteriaBuilder, userFilter.getFirstName()));
        }

        if (Objects.nonNull(userFilter) && Objects.nonNull(userFilter.getLastName())) {
            orPredicates.add(findAllByLastNameLike(root, criteriaBuilder, userFilter.getLastName()));
        }

        if (Objects.nonNull(userFilter) && Objects.nonNull(userFilter.getEmail()) && !Objects.equals(userFilter.getEmail(), "")) {
            orPredicates.add(findAllByEmailLike(root, criteriaBuilder, userFilter.getEmail()));
        }

        if (Objects.nonNull(userFilter) && Objects.nonNull(userFilter.getName()) && !Objects.equals(userFilter.getName(), "")) {
            fields.forEach(field -> orNamePredicates.add(findAllByName(root, criteriaBuilder, field,
                    userFilter.getName())));
        }

        if (!orPredicates.isEmpty()) {
            andPredicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
        }
        mainPredicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));

        if (!orNamePredicates.isEmpty()) {
            mainPredicate = criteriaBuilder.and(
                    mainPredicate,
                    criteriaBuilder.or(orNamePredicates.toArray(new Predicate[orNamePredicates.size()]))
            );
        }
        return mainPredicate;
    }
}
