package com.example.MovieBooking.controller;

import com.example.MovieBooking.entity.Type;
import com.example.MovieBooking.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private ITypeService typeService;

    @GetMapping("/list")
    public String getAllTypes(Model model) {
        model.addAttribute("types", typeService.getAllTypes());
        return "Type/TypeList";
    }

    @GetMapping("/details/{id}")
    public String getTypeById(@PathVariable Long id, Model model) {
        Type type = typeService.getTypeById(id);
        if (type != null) {
            model.addAttribute("type", type);
            return "Type/TypeDetails";
        } else {
            return "redirect:/types/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("type", new Type());
        return "Type/CreateType";
    }

    @PostMapping("/create")
    public String createType(@ModelAttribute Type type) {
        typeService.saveType(type);
        return "redirect:/types/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Type type = typeService.getTypeById(id);
        if (type != null) {
            model.addAttribute("type", type);
            return "Type/EditType";
        } else {
            return "redirect:/types/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateType(@PathVariable Long id, @ModelAttribute Type type) {
        type.setTypeId(id);
        typeService.saveType(type);
        return "redirect:/types/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return "redirect:/types/list";
    }
}