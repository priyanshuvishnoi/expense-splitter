package com.example.backend.user;

import com.example.backend.user.dtos.ChangePasswordReqDto;
import com.example.backend.user.dtos.GetUserDetailsReqDto;
import com.example.backend.user.dtos.LoginReqDto;
import com.example.backend.user.dtos.RegUserReq;
import com.example.backend.user.dtos.UpdateUserDetailsReqDto;
import com.example.backend.user.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("details")
    public ResponseEntity<?> getUserDetails(@Valid @RequestBody GetUserDetailsReqDto reqDto) {
        return ResponseEntity.ok(userService.getUser(reqDto.username()));
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegUserReq regUserReqDto) {
        try {
            return ResponseEntity.ok(userService.createUser(regUserReqDto));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReqDto dto) {
        try {
            return ResponseEntity.ok(userService.loginUser(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordReqDto dto) {
        try {
            return ResponseEntity.ok(userService.changePassword(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("updateUserDetails")
    public ResponseEntity<?> updateUserDetails(@Valid @RequestBody UpdateUserDetailsReqDto dto) {
        try {
            return ResponseEntity.ok(userService.updateUserDetails(dto));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
