package com.example.testtask.service;

import com.example.testtask.exception.InvalidUserException;
import com.example.testtask.repository.UserDAO;
import com.example.testtask.model.User;
import com.example.testtask.model.dto.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final int minAge;

    public UserService(UserDAO userDAO, @Value("${min.age}") int minAge) {
        this.userDAO = userDAO;
        this.minAge = minAge;
    }

    public User create(UserDto userDto) {
        if (!isAgeValid(userDto.getBirthday())) {
            throw new InvalidUserException("You must be 18 or older to register.");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .birthday(userDto.getBirthday())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .build();

        userDAO.save(user);

        return user;
    }

    public User update(Integer id, UserDto userDto) {
        User user = userDAO.findById(id)
                .orElseThrow(() -> new InvalidUserException("User with this ID was not found"));

        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getBirthday() != null && isAgeValid(userDto.getBirthday())) {
            user.setBirthday(userDto.getBirthday());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }

        userDAO.save(user);

        return user;
    }

    public void delete(Integer id) {
        userDAO.deleteById(id);
    }

    public List<User> getUsersByBirthday(LocalDate startDate, LocalDate endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new InvalidUserException("Start date must be before End date!");
        }

        return userDAO.findByBirthdayBetween(startDate, endDate);
    }

    private boolean isAgeValid (LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears() > minAge;
    }
}
