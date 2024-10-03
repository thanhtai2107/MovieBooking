package com.example.MovieBooking.controller;

import org.springframework.ui.Model;
import com.example.MovieBooking.entity.Schedule;
import com.example.MovieBooking.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @GetMapping("/list")
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/details/{id}")
    public String getScheduleById(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule != null) {
            model.addAttribute("schedule", schedule);
            return "Schedule/ScheduleDetails";
        } else {
            return "redirect:/schedules/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "Schedule/CreateSchedule";
    }

    @PostMapping("/create")
    public String createSchedule(@ModelAttribute Schedule schedule) {
        scheduleService.saveSchedule(schedule);
        return "redirect:/schedules/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.getScheduleById(id);
        if (schedule != null) {
            model.addAttribute("schedule", schedule);
            return "Schedule/EditSchedule";
        } else {
            return "redirect:/schedules/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateSchedule(@PathVariable Long id, @ModelAttribute Schedule schedule) {
        scheduleService.saveSchedule(schedule);
        return "redirect:/schedules/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules/list";
    }
}
