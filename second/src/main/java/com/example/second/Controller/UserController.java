package com.example.second.Controller;

import com.example.second.Entity.UserEntity;
import com.example.second.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserEntity> getAll(){
        log.info("Get all users with tasks");
        return userRepository.findAllByOrderByIdAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable Long id){
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
