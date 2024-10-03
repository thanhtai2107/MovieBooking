package com.example.MovieBooking.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.MovieBooking.entity.Promotion;

public interface IPromotionService {
	List<Promotion> getAllPromotions();

	Page<Promotion> getPaginatedPromotions(int page, int size);

	Promotion savePromotion(Promotion promotion);

	Promotion findById(Long id);

	Page<Promotion> searchPromotionsByKeyword(String keyword, int page, int size);

	void deletePromotionById(Long id);
}