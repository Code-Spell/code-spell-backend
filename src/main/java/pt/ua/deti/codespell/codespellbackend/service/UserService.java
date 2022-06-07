package pt.ua.deti.codespell.codespellbackend.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ExistentUserException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.InvalidEmailException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.InvalidPasswordException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.UserNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.User;
import pt.ua.deti.codespell.codespellbackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @NonNull
    public User findByUsername(String username) {

        if (!userRepository.existsByUsername(username))
            throw new UserNotFoundException(String.format("The user %s could not be found.", username));
        return userRepository.findByUsername(username);

    }

    @NonNull
    public User findByEmail(String email) {

        if (!userRepository.existsByEmail(email))
            throw new UserNotFoundException(String.format("The user %s could not be found.", email));
        return userRepository.findByEmail(email);

    }

    public void registerUser(User user) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        boolean emailChecker = Pattern.matches(emailRegex, user.getEmail());
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        boolean passwordChecker = Pattern.matches(passwordRegex, user.getPassword());

        if (userRepository.existsByUsername(user.getUsername()))
            throw new ExistentUserException("The provided username is already taken.");
        else if (!emailChecker)
            throw new InvalidEmailException("The provided email is invalid.");
        else if (!passwordChecker)
            throw new InvalidPasswordException("The provided password is invalid.");
        userRepository.save(user);

    }

    public void updateUser(User user) {
        
        if (!userRepository.existsByUsername(user.getUsername()))
            throw new UserNotFoundException(String.format("The user %s could not be found.", user.getUsername()));
        userRepository.save(user);

    }
}
