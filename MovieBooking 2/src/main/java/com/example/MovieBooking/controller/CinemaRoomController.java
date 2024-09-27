package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.CinemaRoom;
import com.example.MovieBooking.entity.Seat;
import com.example.MovieBooking.entity.SeatType;
import com.example.MovieBooking.service.ISeatService;
import com.example.MovieBooking.service.impl.CinemaRoomServiceImpl;
import com.example.MovieBooking.service.impl.SeatServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cinemaRoom")
public class CinemaRoomController {

    @Autowired
    private CinemaRoomServiceImpl cinemaRoomService;

    @Autowired
    private SeatServiceImpl seatService;

    @GetMapping("/listCinemaRoom")
    public String listCinemaRoom(@RequestParam(value = "valueSearch", defaultValue = "", required = false) String valueSearch,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "1") int size,
                                 Model model) {
        String search = "";
        Page<CinemaRoom> listCinemaRoom;
        if(valueSearch != null && !valueSearch.equals("")) {
            search = valueSearch;
            try{
                Long valueSearhLong = Long.parseLong(search);
                listCinemaRoom = cinemaRoomService.getCinemaRoomsByIdOrBySeatQuantity(valueSearhLong, page, size);
            } catch(NumberFormatException e){
                listCinemaRoom = cinemaRoomService.getCinemaRoomsByName(search, page, size);
            }
        } else {
            listCinemaRoom = cinemaRoomService.getCinemaRoomsByName(search, page, size);
        }

        model.addAttribute("listCinemaRoom", listCinemaRoom);
        model.addAttribute("totalPages", listCinemaRoom.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);

        return "cinemaRoom/listCinemaRoom";
    }

    @GetMapping("/formAddCinemaRoom")
    public String formAddCinemaRoom(Model model) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        model.addAttribute("cinemaRoom", cinemaRoom);
        return "cinemaRoom/addCinemaRoom";
    }

    @PostMapping("/addCinemaRoom")
    public String addCinemaRoom(@Valid @ModelAttribute("cinemaRoom") CinemaRoom cinemaRoom, BindingResult result) {
        if (result.hasErrors()) {
            return "cinemaRoom/addCinemaRoom";
        }
        cinemaRoomService.saveCinemaRoom(cinemaRoom);
        return "redirect:/cinemaRoom/listCinemaRoom";
    }

    @GetMapping("/seatDetail")
    public String seatDetailPage(@RequestParam("cinemaRoomId") Long cinemaRoomId, Model model) {
        CinemaRoom cinemaRoom = cinemaRoomService.getCinemaRoomById(cinemaRoomId);
        List<Seat> listSeat = seatService.listSeatByCinemaRoomId(cinemaRoomId);
        // Tạo danh sách để chứa các hàng ghế
        List<List<Seat>> rows = new ArrayList<>();

        // Chia danh sách ghế thành các hàng
        for (int i = 0; i < listSeat.size(); i += 6) {
            int end = Math.min(i + 6, listSeat.size());
            rows.add(listSeat.subList(i, end));
        }

        model.addAttribute("seatRows", rows); // Thêm danh sách hàng vào model
        model.addAttribute("nameCinemaRoom", cinemaRoom.getCinemaName());
        return "cinemaRoom/seatDetail";
    }

    //Cập nhật nhiều seat
    @PostMapping("/updateSeatType")
    public String updateSeatType(@RequestParam("selectedSeat") Long[] selectedSeat,@RequestParam("seatType") String seatType) {

        // Gọi service để cập nhật loại ghế
        seatService.updateListSeatType(selectedSeat, seatType);

        return "redirect:/cinemaRoom/listCinemaRoom";
    }


}
