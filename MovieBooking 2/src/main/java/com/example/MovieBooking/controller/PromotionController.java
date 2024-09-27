package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.PromotionDTO;
import com.example.MovieBooking.service.impl.UploadImageImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PromotionController {
	@Autowired
	private PromotionServiceImpl promotionService;

	@Autowired
	private UploadImageImpl uploadImageService;


	@GetMapping("/listPromotion")
	public String viewPromotions(
			@RequestParam(defaultValue = "0") int page,  // current page
			@RequestParam(defaultValue = "5") int size,  // size per page
			Model model) {

		Page<Promotion> promotionPage = promotionService.getPaginatedPromotions(page, size);
		System.out.println(promotionPage.toList());
		// Add paginated data and metadata to the model
		model.addAttribute("listPromotions", promotionPage.toList());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", promotionPage.getTotalPages());

		return "listPromotion"; // refers to the HTML page "promotions.html"
	}

	@GetMapping("/listPromotion/search")
	public String searchPromotion(@RequestParam("keyword") String keyword,
								  @RequestParam(defaultValue = "0") int page,  // current page
								  @RequestParam(defaultValue = "5") int size,  // size per page
								  Model model) {
		// Xử lý keyword để thay thế ngày tháng theo định dạng mới
		if (keyword.matches("\\d{2}/\\d{2}/\\d{4}")) { // kiểm tra định dạng "dd/MM/yyyy"
			keyword = keyword.replace("/", "-"); // thay thế "/" bằng "-"
		} else if (keyword.matches("\\d{2}/\\d{2}")) { // kiểm tra định dạng "dd/MM"
			keyword = keyword.replace("/", "-");
		} else if (keyword.matches("\\d{2}/\\d{4}")) { // kiểm tra định dạng "MM/yyyy"
			keyword = keyword.replace("/", "-");
		}
//		System.out.println(keyword);
		Page<Promotion> searchResults = promotionService.searchPromotionsByKeyword(keyword, page, size);
		model.addAttribute("listPromotions", searchResults.toList());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", searchResults.getTotalPages());
		model.addAttribute("keyword", keyword);
		return "listPromotion";
	}

	@GetMapping("/addPromotion")
	public String addPromotion(Model model) {
		PromotionDTO promotionDTO = new PromotionDTO();
		model.addAttribute("promotion", promotionDTO);
		return "addPromotion";
	}

	@PostMapping("/addPromotion")
	public String addPromotion(
			@Valid @ModelAttribute("promotion") PromotionDTO promotionDTO,
			BindingResult bindingResult,
			Model model
	) throws IOException {
		if (bindingResult.hasErrors()) {
			return "addPromotion";
		}
		String imageLink = uploadImageService.uploadImage(promotionDTO.getImage());
		// Save the promotion (convert DTO to entity before saving)
		Promotion promotion = new Promotion();
		promotion.setTitle(promotionDTO.getTitle());
		promotion.setStartTime(promotionDTO.getStartTime());
		promotion.setEndTime(promotionDTO.getEndTime());
		promotion.setDiscountLevel(promotionDTO.getDiscountLevel());
		promotion.setDetail(promotionDTO.getDetail());
		promotion.setImage(imageLink);
		promotionService.savePromotion(promotion);
		return "redirect:/listPromotion";
	}

	@GetMapping("/editPromotion/{id}")
	public String editPromotion(@PathVariable("id") Long id, Model model) {
		Promotion promotion = promotionService.findById(id);
		PromotionDTO promotionDTO = new PromotionDTO();
		promotionDTO.setPromotionId(promotion.getPromotionId());
		promotionDTO.setDetail(promotion.getDetail());
		promotionDTO.setTitle(promotion.getTitle());
		promotionDTO.setStartTime(promotion.getStartTime());
		promotionDTO.setEndTime(promotion.getEndTime());
		promotionDTO.setDiscountLevel(promotion.getDiscountLevel());
		promotionDTO.setImageLink(promotion.getImage());
		System.out.println(promotion.getImage());
		model.addAttribute("promotion", promotionDTO);
		return "editPromotion";
	}

	@PostMapping("/editPromotion")
	public String updatePromotion(
			@Valid @ModelAttribute("promotion") PromotionDTO promotionDTO,
			@RequestParam("image") MultipartFile file,
			BindingResult bindingResult,
			Model model
	) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("promotion", promotionDTO);
			model.addAttribute("promotionID", promotionDTO.getPromotionId());
			System.out.println(promotionDTO.toString());
			return "editPromotion";
		}

		// Save the promotion (convert DTO to entity before saving)
		Promotion promotion = new Promotion();
		promotion.setPromotionId(promotionDTO.getPromotionId());
		promotion.setTitle(promotionDTO.getTitle());
		promotion.setStartTime(promotionDTO.getStartTime());
		promotion.setEndTime(promotionDTO.getEndTime());
		promotion.setDiscountLevel(promotionDTO.getDiscountLevel());
		promotion.setDetail(promotionDTO.getDetail());
		if (!file.isEmpty()){
			String imageLink = uploadImageService.uploadImage(file);
			promotion.setImage(imageLink);
		} else {
			promotion.setImage(promotionDTO.getImageLink());
		}
		promotionService.savePromotion(promotion);
		return "redirect:/listPromotion";
}

	@PostMapping("/deletePromotion/{id}")
	public String deletePromotion(@PathVariable Long id) {
        System.out.println(id);
		try {
			promotionService.deletePromotionById(id);
			return "redirect:/listPromotion";
		} catch (Exception e) {
			return "listPromotion";
		}
	}

}
