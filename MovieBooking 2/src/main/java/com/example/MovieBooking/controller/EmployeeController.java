package com.example.MovieBooking.controller;

import com.example.MovieBooking.dto.req.AccountReq;
import com.example.MovieBooking.entity.Account;
import com.example.MovieBooking.entity.Employee;
import com.example.MovieBooking.repository.AccountRepository;
import com.example.MovieBooking.service.impl.AccountServiceImpl;
import com.example.MovieBooking.service.impl.EmployeeServiceImpl;
import com.example.MovieBooking.util.AccountRegisterValidate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @author DuongVanBao
 * Controller class for managing employee-related operations.
 */
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

    /**
     * Displays a paginated list of employees based on the search criteria.
     *
     * @param model   the UI model for passing attributes to the view
     * @param pageNo  the current page number, defaults to 1 if not specified
     * @param username the username search criteria, defaults to an empty string if not specified
     * @return the view for listing employees
     */
    @GetMapping("/employees")
    public String showListEmployee(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "search", defaultValue = "") String username) {

        Page<Employee> list = employeeService.getAll(username, pageNo);
        System.out.println(list.toList().size());
        model.addAttribute("search", username);
        model.addAttribute("list", list.toList());
        model.addAttribute("totalPages", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "employee/list";
    }

    /**
     * Displays the form to add a new employee.
     *
     * @param model the UI model for passing attributes to the view
     * @return the view for adding a new employee
     */
    @GetMapping("/add")
    public String showFormAdd(Model model) {
        AccountReq account = new AccountReq();
        account.setGender("Nam");
        model.addAttribute("account", account);

        return "employee/add";
    }

    /**
     * Processes the form submission for adding a new employee.
     *
     * @param account       the account request object containing employee details
     * @param bindingResult the binding result for validation errors
     * @param model         the UI model for passing attributes to the view
     * @return a redirect to the employee list view if successful, otherwise returns to the add form
     */
    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("account") AccountReq account, BindingResult bindingResult, Model model) {
        Account account1 = accountService.findUserByUsername(account.getUsername());
        if (account1 != null){
            bindingResult.rejectValue("username", "Account already exists");
        }
        accountRegisterValidate.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            return "employee/add";
        }
        employeeService.add(account);
        return "redirect:/employee-management/employees";
    }

    /**
     * Displays the form for editing an existing employee.
     *
     * @param model the UI model for passing attributes to the view
     * @param id    the ID of the employee to be edited
     * @return the view for editing an employee
     */
    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") Long id) {
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
        System.out.println(account.getImage());
        return "employee/edit";
    }

    /**
     * Processes the form submission for editing an employee.
     *
     * @param account            the account request object containing the updated employee details
     * @param image              the image file uploaded for the employee, if any
     * @param model              the UI model for passing attributes to the view
     * @param redirectAttributes the redirect attributes for passing messages
     * @return a redirect to the employee list view if successful
     * @throws IOException if there is an error during image processing
     */
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("account") AccountReq account, @RequestParam(value = "image", required = false) MultipartFile image, Model model, RedirectAttributes redirectAttributes) throws IOException {
        redirectAttributes.addAttribute("msg", "Update Successful");
        accountService.updateAccount(account, image);
        return "redirect:/employee/list";
    }

    /**
     * Deletes an employee by setting their status to inactive.
     *
     * @param id the ID of the employee to be deleted
     * @return a redirect to the employee list view
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Account account = accountService.findUserById(id);
        account.setStatus(0);
        accountRepository.save(account);
        return "redirect:/employee-management/employees";
    }
}
