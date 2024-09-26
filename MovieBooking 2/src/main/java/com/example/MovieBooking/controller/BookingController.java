package com.example.MovieBooking.controller;


import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Bank;
import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.dto.BookingDTO;
import com.example.MovieBooking.service.impl.BankServiceImpl;
import com.example.MovieBooking.service.impl.BookingServiceImpl;

import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.example.MovieBooking.service.impl.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.entity.Schedule;
import com.example.MovieBooking.repository.MovieRepository;
import com.example.MovieBooking.repository.ScheduleRepository;
import com.example.MovieBooking.service.IMovieService;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;


@Controller
public class BookingController {
    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    BookingServiceImpl bookingService;

    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @Autowired
    private BankServiceImpl bankServiceImpl;

    private final int pageSize = 2;
    private final float percentagePoints = 0.1F;
    private static final String CARD_NUMBER_REGEX = "^[0-9]{16,19}$";

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

    @GetMapping("/admin/booking-list")
    public String getBookingListPageByAdmin(Model model,
                                           @RequestParam(value="search") Optional<String> searchOptial
            ,@RequestParam(value="page") Optional<String> pageOptional) {

        int page = 1;

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        Page<Booking> bookingPage = null;
        String search = "";

        if (pageOptional.isPresent() == false || searchOptial.isPresent() == false) {
//
            Pageable pageable = PageRequest.of(page - 1 , pageSize);
            bookingPage = bookingService.getBookings(pageable);
            List<Booking> bookings = bookingPage.getContent();
            bookingDTOList =  bookingService.convertToBookingDTOList(bookings);
            if (searchOptial.isPresent()) {
                search = searchOptial.get();
            }
        } else {
            try {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } catch (Exception e) {
                // page = 1
                // TODO: handle exception
            }

            if (searchOptial.get().equals("")) {

                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage = bookingService.getBookings(pageable);
            } else {
                search = searchOptial.get();
                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage =  bookingService.getBookingsByConditionWithAdmin(pageable, searchOptial.get());
            }

            List<Booking> bookings = bookingPage.getContent();
            bookingDTOList =  bookingService.convertToBookingDTOList(bookings);



        }
        model.addAttribute("bookingList", bookingDTOList);
        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        return "bookinglistadmin";
    }


    @GetMapping("/member/booking-list")
    public String getBookingListPageByMember(Model model,
                                     @RequestParam(value="search") Optional<String> searchOptial
            ,@RequestParam(value="page") Optional<String> pageOptional,
                                     HttpServletRequest request,
                                             @AuthenticationPrincipal Account account) {

        int page = 1;
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        Page<Booking> bookingPage = null;
        String search = "";
        if (pageOptional.isPresent() == false || searchOptial.isPresent() == false) {
            Pageable pageable = PageRequest.of(page - 1 , pageSize);
            bookingPage = bookingService.getBookingByUserName(pageable, account.getUsername());
            List<Booking> bookings = bookingPage.getContent();
            bookingDTOList =  bookingService.convertToBookingDTOList(bookings);
            if (searchOptial.isPresent()) {
                search = searchOptial.get();
            }
        } else {
            try {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } catch (Exception e) {
                // Neu tham so page la string thi mac dinh page = 1
                // page = 1
                // TODO: handle exception
            }

            if (searchOptial.get().equals("")) {

                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage = bookingService.getBookingByUserName(pageable, account.getUsername());
            } else {
                search = searchOptial.get();
                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage =  bookingService.getBookingsByConditionWithUser(pageable, account.getUsername(), searchOptial.get());
            }

            List<Booking> bookings = bookingPage.getContent();
            bookingDTOList =  bookingService.convertToBookingDTOList(bookings);



        }
        model.addAttribute("bookingList", bookingDTOList);
        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        return "bookinglistmember";
    }

    @GetMapping("/member/confirm-booking/{id}")
    public String getConfirmBookingPageByMember(Model model, @PathVariable long id ,@AuthenticationPrincipal Account account){
        Optional<Booking> bookingOptional = bookingService.getBookingById(id);

    if (!bookingOptional.isPresent()) {

        return "redirect:/member/booking-list";
    } else {
        Booking booking = bookingOptional.get();
        if (booking.getAccount().getAccountId() == account.getAccountId()){
            Member member = account.getMember();
            Integer score = member.getScore();
            BookingDTO bookingDTO = bookingService.convertToBookingDTO(booking);

            List<Bank> bankList = bankServiceImpl.getAllBanks();
            model.addAttribute("banks", bankList);
            model.addAttribute("booking", bookingDTO);
            model.addAttribute("score", score);
            return "confirmbookingmember";
        }else{
            return "redirect:/member/booking-list";
            }
        }
    }

    @PostMapping("/member/update-ticket")
    public String convertToTicketByMember(Model model, HttpServletRequest request,
                                          @RequestParam(value = "bookingId") long bookingId,
                                          RedirectAttributes redirectAttributes,
                                          @RequestParam(value="convertTicket") String convertTicket,
                                          @RequestParam(value="bank") Optional<String> bankOptional,
                                          @RequestParam(value="cardNumber") Optional<String> cardNumberOptional,
                                          @AuthenticationPrincipal Account account){

        System.out.println(bookingId + "-----------------------------------------------------------------");
        Optional<Booking> bookingOptional = bookingService.getBookingById(bookingId);
        if (bookingOptional.isPresent()) {
            Member member = account.getMember();
            int score = member.getScore();
            Booking booking = bookingOptional.get();
            int useScore = booking.getUseScore();

            if(booking.getStatus() == 1) { //Kiem tra da dat chua
                return "redirect:/member/confirm-booking/" +bookingId;
            } else {
                if(convertTicket.equals("agree")) {
                    if (score < useScore) {
                        redirectAttributes.addFlashAttribute("error", "Not enough score to convert into ticket.");
                    } else {
                        member.setScore((int) ((score - useScore) + useScore*percentagePoints));
                        System.out.println("score :" +score );
                        System.out.println("useScore :" +useScore );
                        booking.setStatus(1);
                        memberServiceImpl.updateMember(member);
                        bookingService.updateBooking(booking);
                        return "redirect:/member/convert-to-ticket/" +bookingId;
                    }
                } else {
                    if(bankOptional.isPresent() == false || cardNumberOptional.isPresent() == false) {
                        // Kiem tra khong truyen tham
                        System.out.println(00000000000000000000000);
                    } else {
                        long bankId = 0;
                        String cardNumber = cardNumberOptional.get();
                        boolean isValid = true;
                        try {
                            bankId = Long.parseLong(bankOptional.get());
                        } catch (Exception e) {
                            isValid = false;
                        }

                        Optional<Bank> bankOptional1 = bankServiceImpl.getBankById(bankId);
                        if (!bankOptional1.isPresent()) {
                            isValid = false;
                            System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        }

                        if (!Pattern.matches(CARD_NUMBER_REGEX, cardNumber)){
                            isValid = false;
                        }

                        if (!isValid) {
                            redirectAttributes.addFlashAttribute("errorBank", "Card number is not valid");
                        } else {
                                member.setScore((int)(score + useScore*percentagePoints));
                                System.out.println("score :" +score );
                                System.out.println("useScore :" +useScore );
                                booking.setStatus(1);
                                memberServiceImpl.updateMember(member);
                                bookingService.updateBooking(booking);
                            return "redirect:/member/convert-to-ticket/" +bookingId;
                        }

                    }
                }
            }
        }
        return "redirect:/member/confirm-booking/" +bookingId;
    }


    @GetMapping("/admin/confirm-booking/{id}")
    public String getConfirmBookingPageByAdmin(Model model, @PathVariable long id){
        Optional<Booking> bookingOptional = bookingService.getBookingById(id);
        if (!bookingOptional.isPresent()) {
            return "redirect:/member/booking-list";
        } else {
            Booking booking = bookingOptional.get();
            Member member = booking.getAccount().getMember();
            Integer scoreOfMember = member.getScore();
            BookingDTO bookingDTO = bookingService.convertToBookingDTO(booking);

            model.addAttribute("booking", bookingDTO);
            model.addAttribute("score", scoreOfMember);
            return "confirmbookingadmin";
        }
    }

    @GetMapping("/member/convert-to-ticket/{id}")
    public String getConvertToTicketPageByMember(Model model, @PathVariable long id, @AuthenticationPrincipal Account account) {

        Optional<Booking> bookingOptional = bookingService.getBookingById(id);
        if (!bookingOptional.isPresent()) {
            return "redirect:/member/booking-list";
        } else {
            if (!(bookingOptional.get().getAccount().getAccountId() == account.getAccountId())) {
                return "redirect:/member/booking-list";
            } else {
                Booking booking = bookingOptional.get();
                Member member = booking.getAccount().getMember();
                Integer score = member.getScore();
                BookingDTO bookingDTO = bookingService.convertToBookingDTO(booking);


                model.addAttribute("booking", bookingDTO);
                model.addAttribute("score", score);

                return "converttoticketmember";
            }


        }
    }

//    @GetMapping("/booking-selling")
//    public String bookingSelling(){
//        return "TKS-showtimes";
//    }

    @GetMapping("/movies")
    public String getMoviesByDay(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "movieId", required = false)Long id,
            Model model) {
        System.out.println(date);
        System.out.println(id);
        // Nếu không có ngày được chọn, sử dụng ngày hiện tại
        if (date == null) {
            date = LocalDate.now();
        }
        List<Movie> movieList = new ArrayList<>();
        if(id!= null){
           List<Schedule> scheduleList = scheduleService.getAllSchedulesByDateAndMovieIdCustom(date,id);
            System.out.println("shcedule" + scheduleList.size());
           Movie movie = movieService.getMovieById(id);
//           List<Movie> movieList = new ArrayList<>();
//           movieList.add(movie);
           List<MovieSchedule> movieSchedulesOfMovie = new ArrayList<>();
           for (Schedule schedule : scheduleList) {
                MovieSchedule movieSchedule = new MovieSchedule();
                movieSchedule.setSchedule(schedule);
                movieSchedulesOfMovie.add(movieSchedule);

           }
           movie.setMovieScheduleList(movieSchedulesOfMovie);
           movieList.add(movie);

            System.out.println("schedule movie service" + movieSchedulesOfMovie.size());
           model.addAttribute("movieList", movieList);
//           model.addAttribute("scheduleList", scheduleList);
           model.addAttribute("movieId",id);
        } else {
            // Lấy danh sách phim theo ngày
            movieList = movieService.getMoviesByDate(date);
            for (Movie movie : movieList) {
                String schedules = "schedules" + movie.getMovieId();
                // Lay schedule theo phim theo ngay
                //TriLT updated
                List<Schedule> movieScheduleList = scheduleService.getAllSchedulesByDateAndId(date, movie.getMovieId());
                List<MovieSchedule> movieScheduleList1 = new ArrayList<>();
                for (Schedule schedule : movieScheduleList) {
                    MovieSchedule movieSchedule = new MovieSchedule();
                    movieSchedule.setSchedule(schedule);
                    movieScheduleList1.add(movieSchedule);
                }
                movie.setMovieScheduleList(movieScheduleList1);
            }
        }
        // lay danh sach lich chieu phim
//        List<Schedule> scheduleList = scheduleService.getAllSchedulesByMovieID()

        // Truyền dữ liệu vào model để gửi tới view
        model.addAttribute("selectedDate", date);     // Ngày đã chọn
        model.addAttribute("movieList", movieList);   // Danh sách phim theo ngày
//        model.addAttribute("movieScheduleList", movieScheduleList); // Danh sach lich chieu theo phim theo ngay

        // Debug thông tin ngày và danh sách phim (nếu cần)
//        System.out.println("Selected date: " + date);
//        System.out.println("Movie list: " + movieList);

        // Trả về view để hiển thị
        return "TKS-showtimes";  // Hiển thị trang TKS-showtimes
    }
//    @GetMapping("/history-score")
//    public String historyScorePage(){
//        return "HistoryScore";
//    }

//    @GetMapping("/view-history-score")
//    public String renderHistoryScore(@RequestParam(value = "page", defaultValue = "0")int page
//            ,@RequestParam(value = "size",defaultValue = "10")int size,
//            Model model){
//        List<Booking> bookingList = new ArrayList<>();
//        model.addAttribute("bookingList", bookingList);
//        model.addAttribute("totalPages", bookingList.size());
//        model.addAttribute("currentPage", page);
//        return "HistoryScore";
//    }

    @GetMapping("/view-history-score")
    public String historyScore(@RequestParam(value = "fromDate", required = false, defaultValue = "")String fromDate
                                ,@RequestParam(value = "toDate", required = false, defaultValue = "")String toDate
                                ,@RequestParam(value = "actionScore", required = false)String actionScore
                                ,@RequestParam(value = "page", defaultValue = "0")int page
                                ,@RequestParam(value = "size",defaultValue = "1")int size
                                ,Model model){
        LocalDate validFrom = null;
        LocalDate validTo = null;
        Page<Booking> pageBookingList;
        List<Booking> bookingList = new ArrayList<>();
        if(!fromDate.isEmpty() && !toDate.isEmpty()){
            validFrom = LocalDate.parse(fromDate);
            validTo = LocalDate.parse(toDate);
        }

        if("addedScore".equals(actionScore)){
            pageBookingList = bookingService.getBookingsAddedScoreByDate(1l,validFrom,validTo,page,size);
        } else {
            pageBookingList = bookingService.getBookingsUsedScoreByDate(1l,validFrom,validTo,page,size);
        }
        bookingList = pageBookingList.toList();
        model.addAttribute("bookingList", bookingList);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        model.addAttribute("actionScore", actionScore);
        model.addAttribute("totalPages", pageBookingList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "HistoryScore";
    }


}