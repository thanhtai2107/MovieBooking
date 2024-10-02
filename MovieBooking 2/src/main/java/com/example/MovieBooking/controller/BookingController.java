package com.example.MovieBooking.controller;


import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.service.IShowDateService;
import com.example.MovieBooking.service.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.repository.MovieScheduleRepository;
import com.example.MovieBooking.repository.ScheduleRepository;
import com.example.MovieBooking.service.IMovieService;
import com.example.MovieBooking.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private IMovieService movieService;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private IShowDateService showDateService;


    @Autowired
    BookingServiceImpl bookingService;

    //find booked ticket - need to update find follow user ID
    @GetMapping("/booked-ticket")
    public String bookedTicket(@RequestParam(value = "page", defaultValue = "0")int page
                               ,@RequestParam(value = "size",defaultValue = "10")int size
                               ,@RequestParam(value = "searchInput", defaultValue = "", required = false)String searchInput
                                ,Model model){
//        List<Integer> entry = new ArrayList<>(10);
        String search = "";
        if(searchInput != null){
            search = searchInput;
        }
        Page<Booking> listBooking = bookingService.getBookingsPagination(search, page, size);
        System.out.println(listBooking.toList().size());
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("totalPages", listBooking.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        return "BookedTicketManagement";
    }
    
//    @GetMapping("/booking-selling")
//    public String bookingSelling(){
//        return "TKS-showtimes"; 
//    }

    @GetMapping("/showtimes")
    public String getMoviesByDay(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        // Nếu không có ngày được chọn, sử dụng ngày hiện tại
        if (date == null) {
            date = LocalDate.now();
        }

        // Lấy danh sách phim theo ngày
        List<Movie> movieList = movieService.getMoviesByDate(date);

        ShowDate showDate = showDateService.findShowDateByDate(date);

        // Kiểm tra nếu showDate là null
        if (showDate == null) {
            model.addAttribute("showDate", null); // Gán giá trị null cho showDate trong model
            return "TKS-showtimes"; // Trả về trang hiển thị thông báo
        }
        
        for (Movie movie : movieList) {
            String schedules = "schedules" + movie.getMovieId();
        // Lay schedule theo phim theo ngay
            List<Schedule> movieScheduleList = scheduleRepository.findScheduleTimesAndMoviesByDate(date, movie.getMovieId());
            List<MovieSchedule> movieScheduleList1 = new ArrayList<>();
            for (Schedule schedule : movieScheduleList) {
                MovieSchedule movieSchedule = new MovieSchedule();
                movieSchedule.setSchedule(schedule);
                movieScheduleList1.add(movieSchedule);
            }
            movie.setMovieScheduleList(movieScheduleList1);
        }
        

        
        // lay danh sach lich chieu phim
//        List<Schedule> scheduleList = scheduleService.getAllSchedulesByMovieID()

        // Truyền dữ liệu vào model để gửi tới view
        model.addAttribute("selectedDate", date);     // Ngày đã chọn
        model.addAttribute("movieList", movieList);   // Danh sách phim theo ngày
        model.addAttribute("showDate", showDate);
//        model.addAttribute("movieScheduleList", movieScheduleList); // Danh sach lich chieu theo phim theo ngay

        // Debug thông tin ngày và danh sách phim (nếu cần)
        System.out.println("Selected date: " + date);
        System.out.println("Movie list: " + movieList);
        System.out.println("Show date: " + showDate.getShowDate());     // Trả về view để hiển thị
        return "TKS-showtimes";  // Hiển thị trang TKS-showtimes
    }


}
