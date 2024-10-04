package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.service.ICinemaRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.service.ICinemaRoomService;
import com.example.MovieBooking.service.ISeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
* @Account: Nguyen Cong Van
* @BirthDate: 2001/04/01
* @Desc: Controller for handling requests related to cinema rooms.
* This class manages functionalities such as displaying a list of cinema rooms,
* adding a new cinema room, viewing seat details, and updating seat types.
* */
@Controller
@RequestMapping("/cinemaRoom")
public class CinemaRoomController {
    @Autowired
    private ICinemaRoomService cinemaRoomService;

    @Autowired
    private ISeatService seatService;


    /**
     * Displays a paginated list of cinema rooms.
     *
     * The method handles search functionality by either cinema room name or seat quantity.
     *
     * @param valueSearch the search term entered by the user.
     * @param page the page number to be displayed.
     * @param size the number of cinema rooms per page.
     * @param model the model to add attributes to be used in the returned view.
     * @return the view displaying the list of cinema rooms.
     */
    @GetMapping("/listCinemaRoom")
    public String listCinemaRoom(@RequestParam(value = "valueSearch", defaultValue = "", required = false) String valueSearch,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 Model model) {
        String search = "";
        Page<CinemaRoom> listCinemaRoom;

        // Check valueSearch is valid
        if(valueSearch != null && !valueSearch.equals("")) {
            // Assign the search term to the 'search' variable.
            search = valueSearch;
            try{
                // Convert value from String data type to long
                Long valueSearhLong = Long.parseLong(search);
                // If the conversion is successful, get listCinemaRoom by valueSearchLong
                listCinemaRoom = cinemaRoomService.getCinemaRoomsByIdOrBySeatQuantity(valueSearhLong, page, size);
            } catch(NumberFormatException e){
                // If conversion fails , get listCinemaRoom by search.
                listCinemaRoom = cinemaRoomService.getCinemaRoomsByName(search, page, size);
            }
        } else {
            // If valueSearch = null or empty, get listCinemaRoom with search is empty
            listCinemaRoom = cinemaRoomService.getCinemaRoomsByName(search, page, size);
        }

        // Add attributes to the model for use in the view.
        model.addAttribute("listCinemaRoom", listCinemaRoom);
        model.addAttribute("totalPages", listCinemaRoom.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);

        return "cinemaRoom/listCinemaRoom";
    }

    /**
     * Displays the form to add a new cinema room.
     *
     * @param model the model to add attributes to be used in the returned view.
     * @return the view for adding a new cinema room.
     */
    @GetMapping("/formAddCinemaRoom")
    public String formAddCinemaRoom(Model model) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        model.addAttribute("cinemaRoom", cinemaRoom);
        return "cinemaRoom/addCinemaRoom";
    }

    /**
     * Handles the submission of the form to add a new cinema room.
     *
     * This method validates the input data, checks if the cinema room name already exists,
     * and saves the cinema room if valid.
     *
     * @param cinemaRoom the cinema room entity populated from the form data.
     * @param result the binding result for capturing validation errors.
     * @return the view to redirect to, either back to the form or to the cinema room list page.
     */
    @PostMapping("/addCinemaRoom")
    public String addCinemaRoom(@Valid @ModelAttribute("cinemaRoom") CinemaRoom cinemaRoom, BindingResult result) {

        // Check for validation errors
        if (result.hasErrors()) {
            return "cinemaRoom/addCinemaRoom";
        }

        // Check if the cinema room name already exists in the database
        if (cinemaRoomService.existsByCinemaRoomName(cinemaRoom.getCinemaName())) {
            result.rejectValue("cinemaName", "error.cinemaRoom", "Cinema Room Name already exists.");
            return "cinemaRoom/addCinemaRoom";
        }

        // If there are no errors, save CinemaRoom.
        cinemaRoomService.saveCinemaRoom(cinemaRoom);
        return "redirect:/cinemaRoom/listCinemaRoom";
    }

    /**
     * Displays the seat details for a specific cinema room.
     *
     * This method retrieves the cinema room and the list of seats for the specified cinema room ID,
     * and divides the seats into rows of 6 columns for easier display.
     *
     * @param cinemaRoomId the ID of the cinema room whose seats are to be displayed.
     * @param model the model to add attributes to be used in the returned view.
     * @return the view displaying the seat details.
     */
    @GetMapping("/seatDetail")
    public String seatDetailPage(
            @RequestParam("cinemaRoomId") Long cinemaRoomId, 
            Model model) {

        // Get CinemaRoom after getting cinemaRoomId from param
        CinemaRoom cinemaRoom = cinemaRoomService.getCinemaRoomById(cinemaRoomId);

        // Get list of seats with same cinemaRoomId
        List<Seat> listSeat = seatService.listSeatByCinemaRoomId(cinemaRoomId);

        // Create a list to contain the rows of seats
        List<List<Seat>> rows = new ArrayList<>();

        // Divide the seat list into rows
        for (int i = 0; i < listSeat.size(); i += 6) {
            int end = Math.min(i + 6, listSeat.size());
            rows.add(listSeat.subList(i, end));
        }

        // Add attributes to the model for use in the view.
        model.addAttribute("seatRows", rows);
        model.addAttribute("nameCinemaRoom", cinemaRoom.getCinemaName());

        return "cinemaRoom/seatDetail";
    }

    /**
     * Updates the seat types for the selected seats.
     *
     * This method receives the list of selected seat IDs and the new seat type,
     * and updates the seat types accordingly.
     *
     * @param selectedSeat an array of seat IDs to be updated.
     * @param seatType the new seat type to be applied.
     * @return the view to redirect to, the cinema room list page.
     */
    @PostMapping("/updateSeatType")
    public String updateSeatType(@RequestParam("selectedSeat") Long[] selectedSeat,@RequestParam("seatType") String seatType) {

        // Call service to update the seat types
        seatService.updateListSeatType(selectedSeat, seatType);

        return "redirect:/cinemaRoom/listCinemaRoom";
    }

}

