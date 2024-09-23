package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.entity.Schedule;
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
import java.util.List;

@Controller
public class BookingController {
    @Autowired
    private IMovieService movieService;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;


    @GetMapping("/booked-ticket")
    public String bookedTicket(){
        return "BookedTicketManagement";
    }
    
//    @GetMapping("/booking-selling")
//    public String bookingSelling(){
//        return "TKS-showtimes"; 
//    }

    @GetMapping("/movies")
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
//        model.addAttribute("movieScheduleList", movieScheduleList); // Danh sach lich chieu theo phim theo ngay

        // Debug thông tin ngày và danh sách phim (nếu cần)
        System.out.println("Selected date: " + date);
        System.out.println("Movie list: " + movieList);

        // Trả về view để hiển thị
        return "TKS-showtimes";  // Hiển thị trang TKS-showtimes
    }


}
