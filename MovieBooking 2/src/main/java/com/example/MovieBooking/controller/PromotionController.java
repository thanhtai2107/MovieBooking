package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.PromotionDTO;
import com.example.MovieBooking.service.impl.UploadImageImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.MovieBooking.entity.Promotion;
import com.example.MovieBooking.service.impl.PromotionServiceImpl;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * author: Nguyễn Văn Tuấn_DN24_FRF_FJW_03
 * Controller responsible for handling all promotion-related requests,
 * such as listing, searching, adding, editing, and deleting promotions.
 */
@Controller
public class PromotionController {

	@Autowired
	private PromotionServiceImpl promotionService;

	@Autowired
	private UploadImageImpl uploadImageService;

	/**
	 * Displays a paginated list of promotions.
	 *
	 * @param page  the current page to display, defaults to 0.
	 * @param size  the number of items per page, defaults to 5.
	 * @param model the Model object to pass data to the view.
	 * @return the name of the view to render the list of promotions.
	 */
	@GetMapping("/listPromotion")
	public String viewPromotions(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			Model model) {

		Page<Promotion> promotionPage = promotionService.getPaginatedPromotions(page, size);
		model.addAttribute("listPromotions", promotionPage.toList());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", promotionPage.getTotalPages());

		return "listPromotion";
	}

	/**
	 * Searches for promotions based on the given keyword and displays a paginated list of search results.
	 *
	 * @param keyword the search keyword to filter promotions.
	 * @param page    the current page to display, defaults to 0.
	 * @param size    the number of items per page, defaults to 5.
	 * @param model   the Model object to pass data to the view.
	 * @return the name of the view displaying the search results.
	 */
	@GetMapping("/listPromotion/search")
	public String searchPromotion(@RequestParam("keyword") String keyword,
								  @RequestParam(defaultValue = "0") int page,
								  @RequestParam(defaultValue = "5") int size,
								  Model model) {

		// Handle date formats
		if (keyword.matches("\\d{2}/\\d{2}/\\d{4}")) {
			keyword = keyword.replace("/", "-");
		} else if (keyword.matches("\\d{2}/\\d{2}")) {
			keyword = keyword.replace("/", "-");
		} else if (keyword.matches("\\d{2}/\\d{4}")) {
			keyword = keyword.replace("/", "-");
		}

		Page<Promotion> searchResults = promotionService.searchPromotionsByKeyword(keyword, page, size);
		model.addAttribute("listPromotions", searchResults.toList());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		model.addAttribute("totalPages", searchResults.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "listPromotion";
	}

	/**
	 * Displays the form for adding a new promotion.
	 *
	 * @param model the Model object to pass data to the view.
	 * @return the name of the view containing the promotion form.
	 */
	@GetMapping("/addPromotion")
	public String addPromotion(Model model) {
		PromotionDTO promotionDTO = new PromotionDTO();
		model.addAttribute("promotion", promotionDTO);
		return "addPromotion";
	}

	/**
	 * Handles the submission of a new promotion form and saves the promotion to the database.
	 *
	 * @param promotionDTO       the DTO containing promotion data from the form.
	 * @param bindingResult      the result of the form validation.
	 * @param model              the Model object to pass data to the view.
	 * @param redirectAttributes attributes for redirecting after form submission.
	 * @return the view name to redirect to after a successful or unsuccessful form submission.
	 * @throws IOException if an error occurs during image upload.
	 */
	@PostMapping("/addPromotion")
	public String addPromotion(
			@Valid @ModelAttribute("promotion") PromotionDTO promotionDTO,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("message", "Unsuccessfully add.");
			return "addPromotion";
		}
		String imageLink = uploadImageService.uploadImage(promotionDTO.getImage());

		// Convert DTO to entity and save
		Promotion promotion = new Promotion();
		promotion.setTitle(promotionDTO.getTitle());
		promotion.setStartTime(promotionDTO.getStartTime());
		promotion.setEndTime(promotionDTO.getEndTime());
		promotion.setDiscountLevel(promotionDTO.getDiscountLevel());
		promotion.setDetail(promotionDTO.getDetail());
		promotion.setImage(imageLink);
		promotionService.savePromotion(promotion);

		redirectAttributes.addFlashAttribute("message", "Successfully add.");
		return "redirect:/listPromotion";
	}

	/**
	 * Displays the form for editing an existing promotion.
	 *
	 * @param id    the ID of the promotion to be edited.
	 * @param model the Model object to pass data to the view.
	 * @return the name of the view containing the promotion edit form.
	 */
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
		model.addAttribute("promotion", promotionDTO);
		return "editPromotion";
	}

	/**
	 * Handles the submission of the promotion edit form and updates the promotion in the database.
	 *
	 * @param promotionDTO       the DTO containing the updated promotion data.
	 * @param file               the updated image file, if provided.
	 * @param bindingResult      the result of the form validation.
	 * @param model              the Model object to pass data to the view.
	 * @param redirectAttributes attributes for redirecting after form submission.
	 * @return the view name to redirect to after successfully updating the promotion.
	 * @throws IOException if an error occurs during image upload.
	 */
	@PostMapping("/editPromotion")
	public String updatePromotion(
			@Valid @ModelAttribute("promotion") PromotionDTO promotionDTO,
			@RequestParam("image") MultipartFile file,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) throws IOException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("promotion", promotionDTO);
			model.addAttribute("promotionID", promotionDTO.getPromotionId());
			model.addAttribute("message", "Unsuccessfully update.");
			return "editPromotion";
		}

		Promotion promotion = new Promotion();
		promotion.setPromotionId(promotionDTO.getPromotionId());
		promotion.setTitle(promotionDTO.getTitle());
		promotion.setStartTime(promotionDTO.getStartTime());
		promotion.setEndTime(promotionDTO.getEndTime());
		promotion.setDiscountLevel(promotionDTO.getDiscountLevel());
		promotion.setDetail(promotionDTO.getDetail());
		if (!file.isEmpty()) {
			String imageLink = uploadImageService.uploadImage(file);
			promotion.setImage(imageLink);
		} else {
			promotion.setImage(promotionDTO.getImageLink());
		}
		promotionService.savePromotion(promotion);
		redirectAttributes.addFlashAttribute("message", "Successfully update.");
		return "redirect:/listPromotion";
	}

	/**
	 * Handles the deletion of a promotion by its ID.
	 *
	 * @param id                 the ID of the promotion to be deleted.
	 * @param redirectAttributes attributes for redirecting after deletion.
	 * @param model              the Model object to pass data to the view.
	 * @return the view name to redirect to after successful deletion.
	 */
	@PostMapping("/deletePromotion/{id}")
	public String deletePromotion(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
		try {
			promotionService.deletePromotionById(id);
			redirectAttributes.addFlashAttribute("message", "Successfully delete.");
			return "redirect:/listPromotion";
		} catch (Exception e) {
			model.addAttribute("message", "Fail to delete this promotion.");
			return "listPromotion";
		}
	}
}