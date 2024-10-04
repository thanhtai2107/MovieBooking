package com.example.MovieBooking.repository;

import com.example.MovieBooking.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b " +
            "ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[show_date] AS c " +
            "ON c.show_date_id = a.show_date_id " +
            "WHERE b.username = ?1 " +
            "AND CURRENT_TIMESTAMP < c.show_date",
            nativeQuery = true)
    Page<Booking> findBookingByUserName(String userName, Pageable page);



    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
            "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
            "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
            "WHERE b.username = ?1 " +
            "AND CURRENT_TIMESTAMP < f.show_date " +
            "AND (a.booking_id = ?2  OR b.identity_card = ?3 OR b.phone_number = ?4)",
            countQuery = "SELECT COUNT(*) FROM [dbo].[booking] AS a " +
                    "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
                    "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
                    "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
                    "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
                    "WHERE b.username = ?1 " +
                    "AND CURRENT_TIMESTAMP < f.show_date " +
                    "AND (a.booking_id = ?2  OR b.identity_card = ?3 OR b.phone_number = ?4)",
            nativeQuery = true)
    Page<Booking> findBookingByConditionsWithUser(String username, long bookingId, String identityCard, String phoneNumber, Pageable page);


    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
            "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
            "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
            "WHERE b.username = ?1 " +
            "AND CURRENT_TIMESTAMP < f.show_date " +
            "AND (b.identity_card = ?2 OR b.phone_number = ?3)",
            nativeQuery = true)
    Page<Booking> findBookingByIdCAndPhoneNumWithUser( String username, String identityCard, String phoneNumber, Pageable page);

    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b " +
            "ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[show_date] AS c " +
            "ON c.show_date_id = a.show_date_id " +
            "WHERE CURRENT_TIMESTAMP < c.show_date",
            nativeQuery = true)
    Page<Booking> findAllBookings(Pageable page);


    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
            "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
            "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
            "WHERE CURRENT_TIMESTAMP < f.show_date " +
            "AND (a.booking_id = ?1 OR b.account_id = ?2  OR b.identity_card = ?3 OR b.phone_number = ?4)",
            countQuery = "SELECT count(*) FROM [dbo].[booking] AS a " +
                    "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
                    "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
                    "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
                    "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
                    "WHERE CURRENT_TIMESTAMP < f.show_date " +
                    "AND (a.booking_id = ?1 OR b.account_id = ?2  OR b.identity_card = ?3 OR b.phone_number = ?4)",
            nativeQuery = true)
    Page<Booking> findBookingByConditionsWithAdmin(long bookingId, long accountId, String identityCard, String phoneNumber, Pageable page);

    @Query(value = "SELECT a.* FROM [dbo].[booking] AS a " +
            "INNER JOIN [dbo].[account] AS b ON a.account_id = b.account_id " +
            "INNER JOIN [dbo].[movie] AS d ON d.movie_id = a.movie_id " +
            "INNER JOIN [dbo].[schedule] AS e ON e.schedule_id = a.schedule_id " +
            "INNER JOIN [dbo].[show_date] AS f ON f.show_date_id = a.show_date_id " +
            "WHERE CURRENT_TIMESTAMP < f.show_date " +
            "AND (b.identity_card = ?1 OR b.phone_number = ?2)",
            nativeQuery = true)
    Page<Booking> findBookingByIdCAndPhoneNumWithAdmin(String identityCard,  String phoneNumber, Pageable page);

    @Query("from Booking b  where b.account.accountId = :id and (b.movie.nameVN like %:searchInput% or b.movie.nameEnglish like %:searchInput%)")
    Page<Booking> findWithString(@Param("id") Long id, @Param("searchInput") String searchInput, Pageable pageable);
    @Query("from Booking b  where b.account.accountId = :id and (b.totalMoney = :searchInput)")
    Page<Booking> findWithNumeric(@Param("id") Long id, @Param("searchInput") int searchInput, Pageable pageable);

    @Query("from Booking b where b.useScore = 0 and b.account.accountId = ?1 and b.bookingDate between ?2 and ?3 ")
    Page<Booking> findAddedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    @Query("from Booking b where b.useScore != 0 and b.account.accountId = ?1 and b.bookingDate between ?2 and ?3 ")
    Page<Booking> findUsedScoreByDate(Long id, LocalDate fromDate, LocalDate toDate, Pageable pageable);
}
