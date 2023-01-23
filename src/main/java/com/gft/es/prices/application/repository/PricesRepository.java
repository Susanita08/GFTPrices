package com.gft.es.prices.application.repository;

import com.gft.es.prices.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public interface PricesRepository extends JpaRepository<Price, Serializable> {

    @Query(value= "SELECT p FROM Price p WHERE ((?1 BETWEEN p.startDate AND p.endDate) " +
            "AND (p.productId=?2) AND (p.brandId=?3)) order by p.priority DESC")
    List<Price> findTopByProductIdAndBrandIdAndDateBetweenStartDateAndEndDate(
            Timestamp dateToFound,
            Long productId,
            Long brandId);
}

