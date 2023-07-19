package com.example.jsonexercises.repositories;

import com.example.jsonexercises.domain.entities.Category;
import com.example.jsonexercises.domain.models.category.CategorySummaryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select * from `json-xml-exercise`.categories order by  RAND() LIMIT 1", nativeQuery = true)
    Optional<Category> getRandomEntity();

    @Query(value = "SELECT new com.example.jsonexercises.domain.models.category.CategorySummaryModel(c.name, count(p.id), avg(p.price), sum(p.price))" +
            " FROM Product p " +
            "join p.categories" +
            " c group by c.id")
    List<CategorySummaryModel> getCategorySummary();
}