package com.example.backend.user;

import com.example.backend.auth.AuthService;
import com.example.backend.group.GroupService;
import com.example.backend.group.dtos.GroupDetails;
import com.example.backend.group.entities.Group;
import com.example.backend.user.dtos.ChangePasswordReqDto;
import com.example.backend.user.dtos.LoginReqDto;
import com.example.backend.user.dtos.RegUserReq;
import com.example.backend.user.dtos.UpdateUserDetailsReqDto;
import com.example.backend.user.dtos.UserDetails;
import com.example.backend.user.entities.User;

import jakarta.persistence.Tuple;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ModelMapper modelMapper;

    public User createUser(RegUserReq userDto) {
        User user = modelMapper.map(userDto, User.class);
        String hashedPassword = Base64.getEncoder().encodeToString(userDto.getPassword().getBytes());

        user.setPasswordHash(hashedPassword);

        return userRepo.save(user);
    }

    private UserDetails getUserDetails(String username) throws Exception {
        Optional<Tuple> userTupleOptional = userRepo.findByUserName(username);

        if (userTupleOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        Tuple t = userTupleOptional.get();

        return new UserDetails(
                t.get(0, Integer.class),
                t.get(1, String.class),
                t.get(2, String.class),
                t.get(3, String.class));
    }

    public Boolean loginUser(LoginReqDto dto) throws Exception {
        UserDetails userDetails = getUserDetails(dto.getUserName());

        return authService.validateUser(userDetails, dto.getPassword());
    }

    public Boolean changePassword(ChangePasswordReqDto dto) throws Exception {
        UserDetails userDetails = getUserDetails(dto.getUserName());

        boolean isValid = authService.validateUser(userDetails, dto.getOldPassword());

        if (!isValid) {
            return false;
        }

        String newHashedPassword = authService.hashPassword(dto.getNewPassword());
        userDetails.setPasswordHash(newHashedPassword);
        User user = modelMapper.map(userDetails, User.class);
        userRepo.save(user);
        return true;
    }

    public Object getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUser(Integer userId) {
        return userRepo.findById(userId);
    }

    public Optional<UserDetails> getUser(String username) {
        var tOP = userRepo.findByUserName(username);
        if (tOP.isEmpty()) {
            return Optional.empty();
        }
        var t = tOP.get();
        UserDetails dto = new UserDetails();
        dto.setUserId(t.get(0, Integer.class));
        dto.setEmail(t.get(1, String.class));
        dto.setUsername(t.get(2, String.class));

        List<GroupDetails> adminGroups = groupService.getGroupsByAdmin(dto.getUserId());
        List<GroupDetails> groups = groupService.getGroupsByUser(dto.getUserId());

        dto.setAdminGroups(adminGroups);
        dto.setGroups(groups);

        return Optional.of(dto);
    }

    public UserDetails updateUserDetails(UpdateUserDetailsReqDto dto) throws Exception {
        User user = userRepo.findById(dto.userId()).orElseThrow();

        if (dto.email() != null && !dto.email().isEmpty()) {
            user.setEmail(dto.email());
        }

        if (dto.username() != null && !dto.username().isEmpty()) {
            user.setUserName(dto.username());
        }

        User updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, UserDetails.class);
    }
}
