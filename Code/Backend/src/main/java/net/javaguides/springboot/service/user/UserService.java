package net.javaguides.springboot.service.user;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.User;
@Service
public interface UserService extends UserDetailsService {
	List<User> getAllUsers();

	void saveUser(User user);

	void deleteUserByIdal(int idal);

	User getUserByUsername(String username);

	User getUserByIdal(int id);

	User save(UserRegistrationDto registrationDto);

	public void updateCovidStatus(int idal);
}
