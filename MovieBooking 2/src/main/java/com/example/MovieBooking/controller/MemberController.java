package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Member;
import com.example.MovieBooking.entity.dto.MemberDTO;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("member-management")
public class MemberController {
    @Autowired
    IMemberService memberService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/members")
    public String memberManagement(@RequestParam(value = "searchInput", required = false, defaultValue = "") String searchInput
            , @RequestParam(required = false, defaultValue = "0") int page
            , @RequestParam(required = false, defaultValue = "10") int size, Model model) {
        String search = "";
        if(searchInput != null && !searchInput.isEmpty()) {
            search = searchInput;
        }
        System.out.println("page"+page + "size"+size);
        List<MemberDTO> memberDTOList = memberService.getAllMembers(search, page, size);
        int totalPages = memberService.getTotalPage(search,page,size);
        model.addAttribute("members", memberDTOList);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("search", search);

        return "member-list";
    }

    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable("id") Long id, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Member member = memberService.findMemberById(id);
        Account account1 = member.getAccount();
        AccountReq accountEdit = AccountReq.builder()
                .id(account1.getAccountId())
                .username(account1.getUsername())
                .password(account1.getPassword())
                .address(account1.getAddress())
                .dateOfBirth(account1.getDateOfBirth().format(formatter))
                .email(account1.getEmail())
                .fullname(account1.getFullname())
                .gender(account1.getGender())
                .identityCard(account1.getIdentityCard())
                .phoneNumber(account1.getPhoneNumber())
                .build();
        System.out.println(accountEdit.toString());
        model.addAttribute("account", accountEdit);
        model.addAttribute("image", account1.getImage());
        return "edit-account";
    }
}
