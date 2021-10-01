package net.javaguides.springboot.service.user;

import java.util.Collection;
import java.util.List;
import java.util.Arrays;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	
	/** 
	 * @return List<User>
	 */
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	
	/** 
	 * @param user
	 */
	@Override
	public void saveUser(User user) {
		this.userRepository.save(user);
	}

	
	/** 
	 * @param registrationDto
	 * @return User
	 */
	@Override
	public User save(UserRegistrationDto registrationDto) {

		User user = new User(registrationDto.getEmail().split("@")[0], registrationDto.getFirstName(),
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")), false);
		System.out.println(user);

		return userRepository.save(user);
	}

	
	/** 
	 * @param idal
	 */
	@Override
	public void deleteUserByIdal(int idal) {
		this.userRepository.deleteById(idal);
	}

	
	/** 
	 * @param username
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	
	/** 
	 * @param roles
	 * @return Collection<? extends GrantedAuthority>
	 */
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	
	/** 
	 * @param idal
	 */
	@Override
	public void updateCovidStatus(int idal) {
		User user = userRepository.getOne(idal);
		user.setInfected(!user.getInfected());
		userRepository.save(user);
	}

	
	/** 
	 * @param id
	 * @return User
	 */
	@Override
	public User getUserByIdal(int id) {
		User user = userRepository.getOne(id);
		return user;
	}

	
	/** 
	 * @param username
	 * @return User
	 */
	@Override
	public User getUserByUsername(String username) {
		List<User> users = getAllUsers();
		for (User user : users) {
			System.out.println(users.size());
			System.out.println(user.getUserName());
			if (user.getUserName() != null) {
				if (user.getUserName().equals(username)) {
					return user;
				}
			}
		}
		return null;
	}

}
