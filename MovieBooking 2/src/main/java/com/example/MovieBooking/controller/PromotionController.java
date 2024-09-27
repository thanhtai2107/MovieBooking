package com.example.MovieBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PromotionController {
	@Autowired
	private PromotionServiceImpl promotionService;

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
		}else if (keyword.matches("\\d{2}/\\d{2}")) { // kiểm tra định dạng "dd/MM"
			keyword = keyword.replace("/", "-");
		}else if (keyword.matches("\\d{2}/\\d{4}")) { // kiểm tra định dạng "MM/yyyy"
			keyword = keyword.replace("/", "-");
		}
		System.out.println(keyword);
        Page<Promotion> searchResults = promotionService.searchPromotionsByKeyword(keyword, page, size);
        model.addAttribute("listPromotions", searchResults.toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", searchResults.getTotalPages());
        return "listPromotion";
    }

	@GetMapping("/addPromotion")
	public String addPromotion(){
		return "addPromotion";
	}

	@PostMapping("/addPromotion")
	public String addPromotion(
			@RequestParam String title,
			@RequestParam String startTimeString,
			@RequestParam String endTimeString,
			@RequestParam String discountLevelString,
			@RequestParam String detail,
			@RequestParam String imageLink,
			Model model
	){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTime = LocalDate.parse(startTimeString, formatter);
		LocalDate endTime = LocalDate.parse(endTimeString, formatter);
		BigDecimal discountLevel = new BigDecimal(discountLevelString);

		Promotion promotion = new Promotion();
		promotion.setTitle(title);
		promotion.setStartTime(startTime);
		promotion.setEndTime(endTime);
		promotion.setDiscountLevel(discountLevel);
		promotion.setDetail(detail);
		promotion.setImage(imageLink);
		try {
			promotionService.savePromotion(promotion);
			return "redirect:/listPromotion";
		} catch (Exception e) {
			System.out.println("Error saving promotion: " + e.getMessage());
			// Stay on the addPromotion page and show error message
			model.addAttribute("errorMessage", "Unsuccessfully add");
			// Optionally, re-add the input values to the form so the user doesn't have to re-enter them
			model.addAttribute("promotion", promotion);
			return "addPromotion";
		}
	}

	@GetMapping("/editPromotion/{id}")
	public String editPromotion(@PathVariable("id") Long id, Model model) {
		Promotion promotion = promotionService.findById(id);
		model.addAttribute("promotion", promotion);
		return "editPromotion";
	}

	@PostMapping("/editPromotion")
	public String updatePromotion(
			@RequestParam("id") Long id,
			@RequestParam String title,
			@RequestParam String startTimeString,
			@RequestParam String endTimeString,
			@RequestParam String discountLevelString,
			@RequestParam String detail,
			@RequestParam String imageLink,
            Model model
	){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTime = LocalDate.parse(startTimeString, formatter);
		LocalDate endTime = LocalDate.parse(endTimeString, formatter);
		BigDecimal discountLevel = new BigDecimal(discountLevelString);

        try {
            Promotion promotion = promotionService.findById(id);
            promotion.setTitle(title);
            promotion.setStartTime(startTime);
            promotion.setEndTime(endTime);
            promotion.setDiscountLevel(discountLevel);
            promotion.setDetail(detail);
            promotion.setImage(imageLink);
            try{
                promotionService.savePromotion(promotion);
                model.addAttribute("message", "Update successfully!");
                return "redirect:/listPromotion";
            } catch (Exception e){
                model.addAttribute("message", "Update fail!");
                return "editPromotion";
            }
        } catch (Exception e) {
            model.addAttribute("message", "Can't not found promotion!");
            return "redirect:/listPromotion";
        }
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
