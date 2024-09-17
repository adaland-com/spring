package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.auth.Role;
import com.adaland.springsecurity.model.auth.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {
        String email = "to.ada.boczar+testing@gmail.com";
        String name = "ada";
        String username = "adaboczar";
        String password = "password";
        Role role = Role.CUSTOMER;

        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(password)
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getName()).isEqualTo(name);
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getRole()).isEqualTo(role);
    }

    @Test
    public void givenUserObject_whenFindAll_thenReturnUserList() {
        String email = "to.ada.boczar+testing@gmail.com";
        String name = "ada";
        String username = "adaboczar";
        String password = "password";
        Role role = Role.CUSTOMER;

        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(password)
                .role(role)
                .build();

        String email2 = "to.ada.boczar+testing2@gmail.com";
        String name2 = "ada";
        String username2 = "adaboczar2";
        String password2 = "password";
        Role role2 = Role.CUSTOMER;

        User user2 = User.builder()
                .email(email2)
                .name(name2)
                .username(username2)
                .password(password2)
                .role(role2)
                .build();

        User savedUser1 = userRepository.save(user);
        User savedUser2 = userRepository.save(user2);


        List<User> userList = userRepository.findAll();

        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList.containsAll(List.of(savedUser1, savedUser2)));
    }

    @Test
    public void givenUserObject_whenFindById_thenReturnSavedUser() {
        String email = "to.ada.boczar+testing@gmail.com";
        String name = "ada";
        String username = "adaboczar";
        String password = "password";
        Role role = Role.CUSTOMER;

        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(password)
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(email);
        assertThat(foundUser.getName()).isEqualTo(name);
        assertThat(foundUser.getUsername()).isEqualTo(username);
        assertThat(foundUser.getRole()).isEqualTo(role);
    }


    @Test
    public void givenUserObject_whenUpdate_thenReturnSavedUser() {
        String email = "to.ada.boczar+testing@gmail.com";
        String name = "ada";
        String username = "adaboczar";
        String password = "password";
        Role role = Role.CUSTOMER;

        User user = User.builder()
                .email(email)
                .name(name)
                .username(username)
                .password(password)
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertThat(foundUser).isNotNull();

        String updatedUsername = "boczarada";
        foundUser.setUsername(updatedUsername);
        User updatedUser = userRepository.save(foundUser);

        assertThat(updatedUser.getEmail()).isEqualTo(email);
        assertThat(updatedUser.getName()).isEqualTo(name);
        assertThat(updatedUser.getUsername()).isEqualTo(updatedUsername);
        assertThat(updatedUser.getRole()).isEqualTo(role);
    }

    @Test
    public void whenFindByNonExistingUser_thenThrowsException() {
        long rentId = 1L;

        Optional<User> byId = userRepository.findById(rentId);

        assertTrue(byId.isEmpty());
    }
}