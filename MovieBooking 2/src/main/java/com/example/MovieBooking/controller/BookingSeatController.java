package com.example.MovieBooking.controller;


import com.example.MovieBooking.dto.req.AccountDTO;
import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.service.IMemberService;
import com.example.MovieBooking.service.IShowDateService;
import com.example.MovieBooking.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingSeatController{
    @Autowired 
    private CinemaRoomServiceImpl cinemaRoomService;
    
    @Autowired
    private SeatServiceImpl seatService;
    
    @Autowired
    private MovieServiceImpl movieService;
    
    @Autowired
    private BookingSeatServiceImpl bookingSeatService;
    
    @Autowired
    private BookingServiceImpl bookingService;
    
    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Autowired
    private AccountServiceImpl accountService;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IShowDateService showDateService;

    @GetMapping("/select-seat")
    public String showSelectSeat(
            @RequestParam("movieId") Long movieId,
            @RequestParam("scheduleId") Long scheduleId,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSelect, // Thêm tham số date
            Model model) {

        // Lấy phòng chiếu từ movieId
        Movie movie = movieService.getMovieById(movieId);
        Long cinemaRoomId = movie.getCinemaRoom().getCinemaRoomId();

        // Lấy danh sách ghế từ cinemaRoomId
        List<Seat> seats = seatService.listSeatByCinemaRoomId(cinemaRoomId);

        // Lấy danh sách ghế đã được đặt dựa trên movieId và scheduleId
        List<BookingSeat> bookingSeats = bookingSeatService.getBookedSeats(movieId, scheduleId,dateSelect);

        // Đánh dấu các ghế đã được đặt (ví dụ, đặt trạng thái 'sold' hoặc 1)
        for (BookingSeat bookingSeat : bookingSeats) {
            for(Seat seat : seats) {
                if(seat.getSeatId().equals(bookingSeat.getSeat().getSeatId())) {
                    seat.setSeatStatus(1); // Giả sử trạng thái 1 là ghế đã được đặt
                }
            }
        }

        // Đếm số ghế còn trống
        int availableSeatsCount = (int) seats.stream().filter(seat -> seat.getSeatStatus() == 0).count();

        // Tạo danh sách số lượng ghế có thể chọn
        List<Integer> seatQuantities = new ArrayList<>();
        for (int i = 1; i <= availableSeatsCount; i++) {
            seatQuantities.add(i);
        }

        // Tạo danh sách hàng ghế
        List<List<Seat>> rows = new ArrayList<>();
        for (int i = 0; i < seats.size(); i += 6) {
            int end = Math.min(i + 6, seats.size());
            rows.add(seats.subList(i, end));
        }

        System.out.println("date: showSelectSeat : " + dateSelect);
        model.addAttribute("seats", bookingSeats); // Tất cả ghế
        model.addAttribute("availableSeatsCount", availableSeatsCount); // Số ghế trống
        model.addAttribute("seatQuantities", seatQuantities); // Danh sách số lượng ghế
        model.addAttribute("seatRows", rows);
        model.addAttribute("movieId", movieId);
        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("date", dateSelect); // Thêm date vào model nếu cần thiết

        return "TKS-selecttingseat";
    }
    
    
    @GetMapping("/confirm")
    public String confirmBooking(@RequestParam("selectedSeat") Long[] selectedSeats,
                                 @RequestParam("movieId") Long movieId,
                                 @RequestParam("scheduleId") Long scheduleId,
                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateconfirm,
                                 // Đặt kiểu LocalDate cho date
                                 Model model) {
        Movie movie = movieService.getMovieById(movieId);
       
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
//        Schedule schedule = scheduleService.getShowDateById(scheduleId);
        Long cinemaRoomId = movie.getCinemaRoom().getCinemaRoomId();

        System.out.println("date: confirmBooking : " + dateconfirm);
        
        List<Seat> seatDetails = new ArrayList<Seat>();
        for (Long seatID: selectedSeats){
            Seat seat = seatService.getSeatById(seatID);
            seatDetails.add(seat);
        }
        
        // Assuming seatDetails is a List<Seat>
        int sum = 0; // Declare sum outside the loop
        for (Seat seat : seatDetails) {
            sum += seat.getSeatType().getPrice(); // Accumulate the total price
        }

        
        
//        Account findAccountByIdentityCard = accountService.findUserByIdCardOrMemberID();
        model.addAttribute("total", sum); // Add the total to the model
        // Xử lý dữ liệu từ form gửi lên (danh sách ghế đã chọn)
        model.addAttribute("selectedSeats", selectedSeats);
        System.out.println(selectedSeats);
        model.addAttribute("movie", movie);
        model.addAttribute("date", dateconfirm);
        model.addAttribute("schedule", schedule);
        model.addAttribute("seatDetails", seatDetails);
        
        

        return "TKS-confirmbookingticket";
    }
    
    @PostMapping("/infor")
    public String showInforTicket(
            @RequestParam("movieId") Long movieId,
            @RequestParam(value = "memberId", required = false) Long memberId, // memberId không bắt buộc
            @RequestParam("scheduleId") Long scheduleId,
            @RequestParam("seat") List<Long> listSeats,
            @RequestParam("sum") Long totalMoney,
            @RequestParam("date")  String dateconfirm,
            @RequestParam(value = "agree", defaultValue = "false") boolean isAgree,  // Đặt giá trị mặc định là false
            Model model
            ) {
        
        List<Seat> seatList = new ArrayList<>();
        LocalDate showDateParse = LocalDate.parse(dateconfirm);
        ShowDate showDate = showDateService.findShowDateByDate(showDateParse);
        Schedule schedule = scheduleService.getScheduleById(scheduleId);
//        Member member = memberService.getMemberByID(memberId);
        Movie movie = movieService.getMovieById(movieId);
        Booking booking = new Booking();
        
        booking.setBookingDate(LocalDate.now());
        booking.setSchedule(schedule);
        booking.setStatus(1);  // Assuming 1 is 'confirmed'
        booking.setTotalMoney(totalMoney);
        booking.setShowDate(showDate);
//        booking.setAccount(member.getAccount());
        // Kiểm tra memberId
        Member member = null;
        int newScore = 0;
        int currentScore = 0; // Gán giá trị mặc định là 0
        int useScore = 0;
        int newScoreMember = 0;
        
        if (memberId != null) {
            member = memberService.getMemberByID(memberId);
            booking.setAccount(member != null ? member.getAccount() : null); // Gán account nếu member không null\
            
        } else {
            booking.setAccount(null); // Gán null nếu memberId không có
            booking.setUseScore(0);
        }
        
        double scoreToAdd = (double) totalMoney * 0.1;

        // Nếu memberId không null, lấy điểm của member từ database
        if (memberId != null) {
            currentScore = memberService.getScoreByMember(memberId); // Gọi phương thức lấy điểm của thành viên
        }
        if (isAgree){
            // check currentScore >=  total , if not , just add more score
            if(totalMoney > currentScore){
                currentScore += (int) scoreToAdd;
//                memberService.updateMember(memberId,currentScore);
                member.setScore(currentScore);
                memberService.saveMember(member);
            } else {
                currentScore = currentScore -  totalMoney.intValue();
                useScore = currentScore;
//                memberService.updateMember(memberId,useScore);
                member.setScore(useScore);
                memberService.saveMember(member);
            }
        } else {
            newScore = currentScore + (int)scoreToAdd;
//            memberService.updateMember(memberId,newScore);
            member.setScore(newScore);
            memberService.saveMember(member);
        }
        
        booking.setAddScore(newScore);  // Assuming addScore is calculated somewhere
        booking.setUseScore(member != null ? useScore : null); // Modify based on business logic
        booking.setMovie(movie);
        
        
        // Save the booking
        Booking bookingSaved = bookingService.saveBooking(booking);

        System.out.println(listSeats.size());
        
            for (Long seatID : listSeats) {
                System.out.println(seatID + ": seatId");
                Seat seat = seatService.getSeatById(seatID);
                seatList.add(seat);
                BookingSeat bookingSeat = new BookingSeat();
                bookingSeat.setBooking(bookingSaved);
                bookingSeat.setSeat(seat);
                bookingSeat.setShowDate(showDate);
                bookingSeat.setSchedule(schedule);
                bookingSeat.setMovie(movie);
                bookingSeat.setStatus(1);
                bookingSeatService.save(bookingSeat);
            }

            
        
        System.out.println(movieId + " this is moviedID");
        System.out.println(scheduleId + " this is scheduleId");
        System.out.println(listSeats.size() + "listSeats");
        
        
        model.addAttribute("movieId", movieId);
        model.addAttribute("scheduleId", scheduleId);
        model.addAttribute("booking", bookingSaved);
        model.addAttribute("member", member);
        model.addAttribute("listSeats", seatList);
        model.addAttribute("showDate", showDate);
        model.addAttribute("newScore", scoreToAdd);
        
        return "/TKS-ticketinfomation";
    }
    
    @GetMapping("/getMember")
    @ResponseBody
    public AccountDTO getMember(@RequestParam("memberInput") Long id,
                                @RequestParam(value = "agree", defaultValue = "false") boolean isAgree,  // Đặt giá trị mặc định là false
                                @RequestParam("total") int total,
                                @RequestParam("score") double currentScore,
                                Model model
    ){
         model.addAttribute("agree", isAgree);
         
        return memberService.getMember(id);
    }
    
    
//    public String converToTicketByAdmin(){
//        @RequestParam("agree") boolean isAgree,
//    }
}
