package com.carrental.car_rental_backend.Service;


import com.carrental.car_rental_backend.dto.UserDTO;
import com.carrental.car_rental_backend.entity.User;
import com.carrental.car_rental_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDTO registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }



        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }


    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return convertToDTO(user);
    }


    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Update user
    public UserDTO updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setPhone(userDetails.getPhone());

        User updated = userRepository.save(user);
        return convertToDTO(updated);
    }


    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().toString()
        );
    }
}