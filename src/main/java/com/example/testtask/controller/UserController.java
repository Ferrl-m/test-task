package com.example.testtask.controller;

import com.example.testtask.model.User;
import com.example.testtask.model.dto.UserDto;
import com.example.testtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/update/{id}")
    public User update(@RequestBody UserDto userDto, @PathVariable Integer id) {
        return userService.update(id, userDto);
    }

    @DeleteMapping("/delete/{id}")
    public String update(@PathVariable Integer id) {
        userService.delete(id);

        return "User was successfully deleted!";
    }

    @GetMapping("/by-age")
    public List<User> getUserBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<User> users = userService.getUsersByBirthday(startDate, endDate);
        return users;
    }

}
