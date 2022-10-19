package ru.fruits.client.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {

    private List<Predicate> predicates = new ArrayList<>();

    /**
     * Added new predicate to predicates list.
     *
     * @param object what corresponds with entity field
     * @param function what applies to object, like '.containsIgnoreCase'
     * @return this
     * @param <T>
     */
    public <T> QPredicates add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public static QPredicates builder() {
        return new QPredicates();
    }

    /**
     * Build predicate where all conditions must match.
     *
     * @return predicate
     */
    public Predicate buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    /**
     * Build predicate where some conditions must match.
     *
     * @return predicate
     */
    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
