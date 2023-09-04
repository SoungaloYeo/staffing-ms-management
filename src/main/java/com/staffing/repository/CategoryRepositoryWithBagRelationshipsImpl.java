package com.staffing.repository;

import com.staffing.domain.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CategoryRepositoryWithBagRelationshipsImpl implements CategoryRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Category> fetchBagRelationships(Optional<Category> category) {
        return category.map(this::fetchProducts);
    }

    @Override
    public Page<Category> fetchBagRelationships(Page<Category> categories) {
        return new PageImpl<>(fetchBagRelationships(categories.getContent()), categories.getPageable(), categories.getTotalElements());
    }

    @Override
    public List<Category> fetchBagRelationships(List<Category> categories) {
        return Optional.of(categories).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    Category fetchProducts(Category result) {
        return entityManager
            .createQuery(
                "select category from Category category left join fetch category.products where category is :category",
                Category.class
            )
            .setParameter("category", result)
            .setHint(QueryHints.NATIVE_SPACES, false)
            .getSingleResult();
    }

    List<Category> fetchProducts(List<Category> categories) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, categories.size()).forEach(index -> order.put(categories.get(index).getId(), index));
        List<Category> result = entityManager
            .createQuery(
                "select distinct category from Category category left join fetch category.products where category in :categories",
                Category.class
            )
            .setParameter("categories", categories)
            .setHint(QueryHints.NATIVE_SPACES, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
