package com.example.api.service;

import com.example.shared.database.entity.User;
import com.example.shared.database.repository.UserRepository;
import com.example.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
    }
}
