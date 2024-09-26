package com.example.MovieBooking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.repository.PromotionRepository;
import com.example.MovieBooking.service.IPromotionService;

@Service
public class PromotionServiceImpl implements IPromotionService {
	 @Autowired
	    private PromotionRepository promotionRepository;

	    public List<Promotion> getAllPromotions() {
	        return promotionRepository.findAll();
	    }
	    public Page<Promotion> getPaginatedPromotions(int page, int size) {
	        return promotionRepository.findAll(PageRequest.of(page, size));
	    }

	@Override
	public Promotion savePromotion(Promotion promotion) {
		return promotionRepository.save(promotion);
	}

	public Promotion findById(Long id){
			Optional<Promotion> optionalPromotion = promotionRepository.findById(id);
			if (optionalPromotion.isPresent()) {
				return optionalPromotion.get(); // Return the Promotion object
			} else {
				throw new RuntimeException("Promotion not found with id " + id);
			}
	}

	// Search Promotion with keyword from input tag
	public Page<Promotion> searchPromotionsByKeyword(String keyword, int page, int size) {
		// Create a Pageable object
		Pageable pageable = PageRequest.of(page, size);

		if (keyword != null && !keyword.trim().isEmpty()) {
			return promotionRepository.searchPromotions(keyword, pageable);
		} else {
			return promotionRepository.findAll(pageable);  // return paginated results if no keyword
		}
	}

	@Override
	public void deletePromotionById(Long id) {
		promotionRepository.deleteById(id);
	}
}
