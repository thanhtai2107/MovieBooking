package com.example.MovieBooking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ACCOUNT")
public class Account implements UserDetails {
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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }
}
