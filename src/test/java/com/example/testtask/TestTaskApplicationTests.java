package com.example.testtask;

import com.example.testtask.exception.InvalidUserException;
import com.example.testtask.model.User;
import com.example.testtask.model.dto.UserDto;
import com.example.testtask.repository.UserDAO;
import com.example.testtask.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:/application.properties")
class TestTaskApplicationTests {

	private UserService userService;
	@Mock
	private UserDAO userDAO;
	@Value("${min.age}")
	private int minAge;

	MockMvc mockMvc;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		userService = new UserService(userDAO, minAge);
	}

	@Test
	void testCreateUserValidAge() {
		UserDto userDto = createUserDto(LocalDate.of(2000, 1, 1));
		User createdUser = userService.create(userDto);
		User userFromDto = createUser(createdUser.getId(), userDto);
		assertEquals(createdUser, userFromDto);
	}

	@Test
	void testCreateUserInvalidAge() {
		UserDto userDto = createUserDto(LocalDate.of(2022, 10, 2));

		assertThrows(InvalidUserException.class, () -> userService.create(userDto));
	}

	@Test
	@Transactional

	void testUpdateUser() {
		UserDto userDto = createUserDto(LocalDate.of(2000, 1, 1));
		User user = createUser(1, userDto);

		when(userDAO.findById(1)).thenReturn(Optional.of(user));

		UserDto newUserDto = UserDto.builder()
				.email("newtest@example.com")
				.lastName("Max")
				.firstName("Mak")
				.birthday(LocalDate.of(2002, 10, 14))
				.phoneNumber("123123 Main St")
				.address("123123123")
				.build();


		User updatedUser = userService.update(user.getId(), newUserDto);

		assertEquals(newUserDto.getFirstName(), updatedUser.getFirstName());
		assertEquals(newUserDto.getLastName(), updatedUser.getLastName());
		assertEquals(newUserDto.getEmail(), updatedUser.getEmail());
		assertEquals(newUserDto.getBirthday(), updatedUser.getBirthday());
		assertEquals(newUserDto.getPhoneNumber(), updatedUser.getPhoneNumber());
		assertEquals(newUserDto.getAddress(), updatedUser.getAddress());
	}

	@Test
	void testUpdateUserNotFound() {
		Integer userId = 1;
		UserDto userDto = createUserDto(LocalDate.of(1990, 1, 1)); // Age is valid

		when(userDAO.findById(userId)).thenReturn(Optional.empty());

		assertThrows(InvalidUserException.class, () -> userService.update(userId, userDto));
	}

	@Test
	void testDeleteUser() {
		UserDto userDto = createUserDto(LocalDate.of(2000, 1, 1));

		userService.delete(1);

		verify(userDAO, times(1)).deleteById(1);
	}

	@Test
	void testGetUsersByBirthday() {
		LocalDate startDate = LocalDate.of(1990, 1, 1);
		LocalDate endDate = LocalDate.of(2000, 12, 31);
		List<User> usersInRange = createUsersInRange(startDate, endDate);

		when(userDAO.findByBirthdayBetween(startDate, endDate)).thenReturn(usersInRange);

		List<User> result = userService.getUsersByBirthday(startDate, endDate);

		assertEquals(usersInRange, result);

		verify(userDAO, times(1)).findByBirthdayBetween(startDate, endDate);

		verifyNoMoreInteractions(userDAO);
	}

	private List<User> createUsersInRange(LocalDate startDate, LocalDate endDate) {
		List<User> users = new ArrayList<>();
		LocalDate currentDate = startDate;
		while (!currentDate.isAfter(endDate)) {
			User user = new User();
			user.setBirthday(currentDate);
			users.add(user);
			currentDate = currentDate.plusDays(1);
		}
		return users;
	}

	private UserDto createUserDto(LocalDate birthday) {
		return UserDto.builder()
				.email("test@example.com")
				.lastName("Doe")
				.firstName("John")
				.birthday(birthday)
				.address("123 Main St")
				.phoneNumber("1234567890")
				.build();
	}

	private User createUser(Integer id,UserDto userDto) {
		return User.builder()
				.id(id)
				.email(userDto.getEmail())
				.lastName(userDto.getLastName())
				.firstName(userDto.getFirstName())
				.birthday(userDto.getBirthday())
				.phoneNumber(userDto.getPhoneNumber())
				.address(userDto.getAddress())
				.build();
	}
}
