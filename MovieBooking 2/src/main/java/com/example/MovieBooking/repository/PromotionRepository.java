package com.example.MovieBooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.MovieBooking.entity.Promotion;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	Page<Promotion> findAll(Pageable pageable);
	@Query(value = "SELECT * FROM promotion p WHERE p.title LIKE %:keyword% "
			+ "OR CAST(p.promotion_id as VARCHAR) LIKE %:keyword% "
			+ "OR CAST(p.discount_level as VARCHAR) LIKE %:keyword% "
			+ "OR p.detail LIKE %:keyword% "
			+ "OR CONVERT(VARCHAR, p.start_time, 105) LIKE %:keyword% "
			+ "OR CONVERT(VARCHAR, p.end_time, 105) LIKE %:keyword%",
			nativeQuery = true)
	Page<Promotion> searchPromotions(@Param("keyword") String keyword, Pageable pageable);

	void deletePromotionByPromotionId(Long id);

}
