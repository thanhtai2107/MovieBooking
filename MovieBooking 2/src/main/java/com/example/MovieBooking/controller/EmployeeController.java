package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Employee;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.service.IAccountService;
import com.example.MovieBooking.service.impl.AccountServiceImpl;
import com.example.MovieBooking.service.impl.EmployeeServiceImpl;
import com.example.MovieBooking.util.AccountRegisterValidate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/employee-management")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private AccountRegisterValidate accountRegisterValidate;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/employees")
    public String showListEmployee(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "search", defaultValue = "") String username) {

        Page<Employee> list = employeeService.getAll(username, pageNo);
//        List<Employee> employeeList = employeeService.getALl();
//        System.out.println(employeeList);
        model.addAttribute("search", username);
        model.addAttribute("list", list.toList());
        System.out.println(list.toList().size());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        System.out.println(list.getTotalPages());
        return "employee/list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        AccountReq account = new AccountReq();
        account.setGender("Nam");
        model.addAttribute("account", account);

        return "employee/add";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@Valid @ModelAttribute("account") AccountReq account, BindingResult bindingResult, Model model) {
        try {
            employeeService.add(account);
            return ResponseEntity.ok().body("Employee added successfully"); // Trả về phản hồi JSON
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding employee");
        }
    }
//    public String addEmployee(@Valid @ModelAttribute("account") AccountReq account, BindingResult bindingResult, Model model) {
//        accountRegisterValidate.validate(account, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "employee/add";
//        }
//        employeeService.add(account);
//        return "redirect:/employee/list";
//    }

    @GetMapping("/edit")
    public String edit(Model model,@RequestParam("id")Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Account account = accountService.findUserById(id);
        AccountReq accountEdit = AccountReq.builder()
                .id(account.getAccountId())
                .username(account.getUsername())
                .password(account.getPassword())
                .address(account.getAddress())
                .dateOfBirth(account.getDateOfBirth().format(formatter))
                .email(account.getEmail())
                .fullname(account.getFullname())
                .gender(account.getGender())
                .identityCard(account.getIdentityCard())
                .phoneNumber(account.getPhoneNumber())
                .build();
        model.addAttribute("account", accountEdit);
        model.addAttribute("image", account.getImage());
        return "edit-account";
    }


    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("account") AccountReq account, @RequestParam(value = "image", required = false) MultipartFile image, Model model, RedirectAttributes redirectAttributes) throws IOException {
//        Account account1 = accountService.findUserById(account.getId());
//        account1.setPassword(account.getPassword());
//        account1.setFullname(account.getFullname());
//        account1.setDateOfBirth(account.getDateOfBirth());
//        account1.setGender(account.getGender());
//        account1.setIdentityCard(account.getIdentityCard());
//        account1.setPhoneNumber(account.getPhoneNumber());
//        account1.setAddress(account.getAddress());
//        account1.setEmail(account.getEmail());
//        if (!image.isEmpty()) {
//            account1.setImage(uploadImage.uploadImage(image));
//        }
        redirectAttributes.addAttribute("msg", "Update Successful");
        accountService.updateAccount(account, image);
        return "redirect:/employee/list";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        Account account = accountService.findUserById(id);
        account.setStatus(0);
        accountRepository.save(account);

        return "redirect:/employee/list";
    }
}



