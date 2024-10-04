package com.example.MovieBooking.controller;


import com.example.MovieBooking.entity.*;
import com.example.MovieBooking.entity.dto.BookingDTO;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.impl.BankServiceImpl;
import com.example.MovieBooking.service.impl.BookingServiceImpl;
import com.example.MovieBooking.service.impl.MovieServiceImpl;
import com.example.MovieBooking.service.impl.ScheduleServiceImpl;
import com.example.MovieBooking.service.IShowDateService;
import com.example.MovieBooking.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.MovieSchedule;
import com.example.MovieBooking.entity.Schedule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {
    @Autowired
    private MovieServiceImpl movieService;
    
    @Autowired
    private IShowDateService showDateService;
    
    @Autowired
    private ScheduleServiceImpl scheduleService;


    @Autowired
    private BookingServiceImpl bookingService;


    @Autowired
    private MemberServiceImpl memberServiceImpl;

    @Autowired
    private BankServiceImpl bankServiceImpl;

    @Autowired
    private IAccountService accountService;
    private final int pageSize = 3;
    private final float percentagePoints = 0.1F;
    private static final String CARD_NUMBER_REGEX = "^[0-9]{16,19}$";

    /**
     * booked ticket and search with userId - pagination
     * @author Le Thanh Tri
     * @param page
     * @param size
     * @param searchInput
     * @param account
     * @param model
     * @return "BookedTicketManagement"
     */
    @GetMapping("/booked-ticket")
    public String bookedTicket(@RequestParam(value = "page", defaultValue = "0")int page
            ,@RequestParam(value = "size",defaultValue = "10")int size
            ,@RequestParam(value = "searchInput", defaultValue = "", required = false)String searchInput
            ,@AuthenticationPrincipal Account account
            ,Model model){

        String search = "";
        if(searchInput != null){
            search = searchInput;
        }

        Account account1 = accountService.findUserByUsername(account.getUsername());

        Page<Booking> listBooking = bookingService.getBookingsPagination(account1.getAccountId(),search, page, size);
        System.out.println(listBooking.toList().size());
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("totalPages", listBooking.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        return "BookedTicketManagement";
    }

    /**
     * NGUYENVANSU
     * @param model
     * @param searchOptional
     * @param pageOptional
     * @return
     */
    @GetMapping("/admin/booking-list")
    public String getBookingListPageByAdmin(Model model,
                                           @RequestParam(value="search") Optional<String> searchOptional
            ,@RequestParam(value="page") Optional<String> pageOptional) {

        int page = 1;

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        Page<Booking> bookingPage = null;
        String search = "";

        if (pageOptional.isPresent() == false || searchOptional.isPresent() == false) {
//
            Pageable pageable = PageRequest.of(page - 1 , pageSize);
            bookingPage = bookingService.getBookings(pageable);
            List<Booking> bookings = bookingPage.getContent();
            bookingDTOList =  bookingService.convertToBookingDTOList(bookings);
            if (searchOptional.isPresent()) {
                search = searchOptional.get();
            }
        } else {
            try {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } catch (Exception e) {
                // page = 1
                // TODO: handle exception
            }

            if (searchOptional.get().equals("")) {

                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage = bookingService.getBookings(pageable);
            } else {
                search = searchOptional.get();
                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage =  bookingService.getBookingsByConditionWithAdmin(pageable, searchOptional.get());
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


    /**
     * NGUYENVANSU
     * @param model
     * @param searchOptial
     * @param pageOptional
     * @param request
     * @param account
     * @return
     */
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

            if (searchOptial.get().isEmpty()) {
                Pageable pageable = PageRequest.of(page -1 , pageSize);
                bookingPage = bookingService.getBookingByUserName(pageable, account.getUsername());
            } else {
                search = (String) searchOptial.get();
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

    /**
     * NGUYENVANSU
     * @param model
     * @param id
     * @param account
     * @return
     */
    @GetMapping("/member/confirm-booking/{id}")
    public String getConfirmBookingPageByMember(Model model, @PathVariable String id , @AuthenticationPrincipal Account account){
        try {
            Long.parseLong(id);
        }catch (Exception e) {
            return "redirect:/member/booking-list";
        }

        Optional<Booking> bookingOptional = bookingService.getBookingById(Long.parseLong(id));

    if (!bookingOptional.isPresent()) {

        return "redirect:/member/booking-list";
    } else {
        Booking booking = bookingOptional.get();
        if (booking.getAccount().getUsername().equals(account.getUsername())){
            Member member = booking.getAccount().getMember();
            Long score = member.getScore();
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

    /**
     * NGUYENVANSU
     * @param model
     * @param request
     * @param redirectAttributes
     * @param bookingIdOptional
     * @param convertTicketOptional
     * @param bankOptional
     * @param cardNumberOptional
     * @param account
     * @return
     */
    @PostMapping("/member/update-ticket")
    public String convertToTicketByMember(Model model, HttpServletRequest request,
                                          RedirectAttributes redirectAttributes,
                                          @RequestParam(value = "bookingId") Optional<String> bookingIdOptional,
                                          @RequestParam(value="convertTicket") Optional<String> convertTicketOptional,
                                          @RequestParam(value="bank") Optional<String> bankOptional,
                                          @RequestParam(value="cardNumber") Optional<String> cardNumberOptional,
                                          @AuthenticationPrincipal Account account){

        if (!bookingIdOptional.isPresent()){
            return "redirect:/member/booking-list";
        }

        try {
            Long.parseLong(bookingIdOptional.get());
        } catch (Exception e) {
            return "redirect:/member/booking-list";
        }

        long bookingId = Long.parseLong(bookingIdOptional.get());

        Optional<Booking> bookingOptional = bookingService.getBookingById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();

            if (booking.getAccount().getUsername().equals(account.getUsername())){
                Member member = booking.getAccount().getMember();
                Long score = member.getScore();


                if(booking.getStatus() == 1) { //Kiem tra da dat chua
                    return "redirect:/member/confirm-booking/" +bookingId;
                } else {
                    if (!convertTicketOptional.isPresent()) {
                        return "redirect:/member/confirm-booking/" +bookingId;
                    }

                    String convertTicket = convertTicketOptional.get();
                    if(convertTicket.equals("agree")) {
                        Long money = booking.getTotalMoney();
                        if (score < money) {
                            redirectAttributes.addFlashAttribute("error", "Not enough score to convert into ticket.");
                        } else {
                            System.out.println(money);
                            member.setScore(member.getScore() - money);
                            booking.setAddScore(0L);
                            booking.setUseScore(money);
                            //thanh toan = diem
                            //member.score - score
                            //set booking.addScore = 0
                            booking.setStatus(1);
                            memberServiceImpl.updateMember(member);
                            bookingService.updateBooking(booking);
                            return "redirect:/member/convert-to-ticket/" +bookingId;
                        }
                    } else {
                        if(bankOptional.isPresent() == false || cardNumberOptional.isPresent() == false) {
                            // Kiem tra khong truyen tham
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
                            }

                            if (!Pattern.matches(CARD_NUMBER_REGEX, cardNumber)){
                                isValid = false;
                            }

                            if (!isValid) {
                                redirectAttributes.addFlashAttribute("errorBank", "Card number is not valid");
                            } else {
                                booking.setUseScore(0L);
                                booking.setAddScore((long) (booking.getTotalMoney()*0.1));
                                member.setScore(member.getScore() +  (long) (booking.getTotalMoney()*0.1));
                                System.out.println("score :" +score );
                                booking.setStatus(1);
                                memberServiceImpl.updateMember(member);
                                bookingService.updateBooking(booking);
                                return "redirect:/member/convert-to-ticket/" +bookingId;
                            }

                        }
                    }
                }
            }


        }
        return "redirect:/member/confirm-booking/" +bookingId;
    }

    /**
     * NGUYENVANSU
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/admin/confirm-booking/{id}")
    public String getConfirmBookingPageByAdmin(Model model, @PathVariable Optional<String> id){
        if (!id.isPresent()) {
            return "redirect:/member/booking-list";
        }
        try {
            Long.parseLong(id.get());
        }catch(Exception e) {
            return "redirect:/member/booking-list";
        }

        Optional<Booking> bookingOptional = bookingService.getBookingById(Long.parseLong(id.get()));
        if (!bookingOptional.isPresent()) {
            return "redirect:/member/booking-list";
        } else {
            Booking booking = bookingOptional.get();
            Member member = booking.getAccount().getMember();
            Long scoreOfMember = member.getScore();
            BookingDTO bookingDTO = bookingService.convertToBookingDTO(booking);

            model.addAttribute("booking", bookingDTO);
            model.addAttribute("score", scoreOfMember);
            return "confirmbookingadmin";
        }
    }

    @GetMapping("/member/convert-to-ticket/{id}")
    public String getConvertToTicketPageByMember(Model model, @PathVariable Optional<String> id, @AuthenticationPrincipal Account account) {
        if(!id.isPresent()) {
            return "redirect:/member/booking-list";
        }

        try {
            Long.parseLong(id.get());
        } catch (Exception e) {
            return "redirect:/member/booking-list";
        }

        Optional<Booking> bookingOptional = bookingService.getBookingById(Long.parseLong(id.get()));
        if (!bookingOptional.isPresent()) {
            return "redirect:/member/booking-list";
        } else {
            Booking booking = bookingOptional.get();
            if (!booking.getAccount().getUsername().equals(account.getUsername()))  {
                return "redirect:/member/booking-list";
            } else {

                Member member = booking.getAccount().getMember();
                Long score = member.getScore();
                BookingDTO bookingDTO = bookingService.convertToBookingDTO(booking);


                model.addAttribute("booking", bookingDTO);
                model.addAttribute("score", score);

                return "converttoticketmember";
            }
        }
    }

    /**
     * Get movie and render schedule time with movieID
     * @author Le Thanh Tri
     * @param date
     * @param id // movieId
     * @param model
     * @return "ShowTimeWithId"
     */
    @GetMapping("/movie-show-time")
    public String scheduleOfMovie(@RequestParam(value = "date", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date,
                                  @RequestParam(value = "movieId", required = false)Long id,
                                  Model model){
        List<Movie> movieList = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,dateFormatter);
        List<Schedule> scheduleList = scheduleService.getAllSchedulesByDateAndMovieIdCustom(localDate,id);
        Movie movie = movieService.getMovieById(id);

        List<MovieSchedule> movieSchedulesOfMovie = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            MovieSchedule movieSchedule = new MovieSchedule();
            movieSchedule.setSchedule(schedule);
            movieSchedulesOfMovie.add(movieSchedule);
        }
        movie.setMovieScheduleList(movieSchedulesOfMovie);
        movieList.add(movie);
        //Current Hour for checking to disable schedule time in front end
        model.addAttribute("currentHour", LocalTime.now().toString());
        model.addAttribute("movieList", movieList);
        //Keep the id for keep searching for the next day
        model.addAttribute("movieId",id);
        return "ShowTimeWithId";
    }

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
            List<Schedule> movieScheduleList = scheduleService.getAllSchedulesByMovieId(date, movie.getMovieId());
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

    /**
     * Get History Added Score / Used Score with Pagination
     * @author Le Thanh Tri
     * @param fromDate
     * @param toDate
     * @param actionScore //action search
     * @param page
     * @param size
     * @param account
     * @param model
     * @return "HistoryScore"
     */
    @GetMapping("/view-history-score")
    public String historyScore(@RequestParam(value = "fromDate", required = false, defaultValue = "")String fromDate
            ,@RequestParam(value = "toDate", required = false, defaultValue = "")String toDate
            ,@RequestParam(value = "actionScore", required = false)String actionScore
            ,@RequestParam(value = "page", defaultValue = "0")int page
            ,@RequestParam(value = "size",defaultValue = "10")int size
            ,@AuthenticationPrincipal Account account
            ,Model model){
        LocalDate validFrom = null;
        LocalDate validTo = null;
        Page<Booking> pageBookingList;
        List<Booking> bookingList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!fromDate.isEmpty() && !toDate.isEmpty()){
            validFrom = LocalDate.parse(fromDate,formatter);
            validTo = LocalDate.parse(toDate,formatter);
        }

        Account account1 = accountService.findUserByUsername(account.getUsername());

        //action search ( addedScore / usedScore )
        if("addedScore".equals(actionScore)){
            pageBookingList = bookingService.getBookingsAddedScoreByDate(account1.getAccountId(),validFrom,validTo,page,size);
        } else {
            pageBookingList = bookingService.getBookingsUsedScoreByDate(account1.getAccountId(),validFrom,validTo,page,size);
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