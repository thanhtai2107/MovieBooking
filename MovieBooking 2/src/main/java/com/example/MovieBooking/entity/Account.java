package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(columnDefinition = "varchar(255)")
    private String username;

    @Column(columnDefinition = "varchar(255)")
    private String password;

    @Column(columnDefinition = "varchar(255)")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "varchar(255)")
    private String email;

    @Column(columnDefinition = "nvarchar(255)")
    private String fullname;

    @Column(columnDefinition = "varchar(255)")
    private String gender;

    @Column(columnDefinition = "varchar(255)")
    private String identityCard;

    @Column(columnDefinition = "varchar(255)")
    private String image;

    @Column(columnDefinition = "varchar(255)")
    private String phoneNumber;

    @Column(name = "register_date")
    private LocalDate registerDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Integer status;


    @OneToOne(mappedBy = "account")
    private Employee employee;

    @OneToOne(mappedBy = "account")
    private Member member;

    @OneToMany(mappedBy = "account")
    private List<Booking> bookingList;
}
