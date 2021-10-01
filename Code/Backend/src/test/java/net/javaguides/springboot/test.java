package net.javaguides.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.javaguides.springboot.controller.dto.UserRegistrationDto;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.user.UserService;
import net.javaguides.springboot.service.user.UserServiceImpl;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableJpaRepositories
@ContextConfiguration(classes = { UserRepository.class, UserServiceImpl.class })
public class test {

    @Autowired
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @Test
    public void testear() {
        userService = new UserServiceImpl(userRepository);
        UserRegistrationDto user = new UserRegistrationDto();
        user.setFirstName("Mendipetas");
        userService.save(user);
    }
}
