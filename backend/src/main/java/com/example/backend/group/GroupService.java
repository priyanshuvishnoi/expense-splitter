package com.example.backend.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.backend.group.dtos.CreateGroupReqDto;
import com.example.backend.group.dtos.GroupDetails;
import com.example.backend.group.entities.Group;
import com.example.backend.user.UserService;
import com.example.backend.user.entities.User;
import com.example.backend.utils.ApiException;

import jakarta.persistence.Tuple;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    // @Transactional
    public GroupDetails createGroup(CreateGroupReqDto dto) throws ApiException {
        Optional<User> userOptional = userService.getUser(dto.getUserId());

        if (userOptional.isEmpty()) {
            throw new ApiException("Invalid userId", HttpStatus.BAD_REQUEST);
        }

        User user = userOptional.get();
        Group group = modelMapper.map(dto, Group.class);

        group.setAdmin(user);
        group.getMembers().add(user);
        Group savedGroup = groupRepo.save(group);
        GroupDetails savedGroupDetails = new GroupDetails();
        savedGroupDetails.setGroupId(savedGroup.getGroupId());
        savedGroupDetails.setGroupName(dto.getGroupName());

        return savedGroupDetails;
    }

    public GroupDetails getGroupDetails(Integer groupId) {
        Tuple t = groupRepo.getGroupDetails(groupId);
        GroupDetails groupDetails = new GroupDetails();
        groupDetails.setGroupId(t.get(0, Integer.class));
        groupDetails.setGroupName(t.get(1, String.class));

        return groupDetails;
    }

    public List<GroupDetails> getGroupsByAdmin(Integer userId) {
        List<Tuple> tList = groupRepo.getGroupsByAdmin(userId);

        List<GroupDetails> groupDetailsDtos = new ArrayList<>();
        for (Tuple t : tList) {
            GroupDetails groupDetails = new GroupDetails();
            groupDetails.setGroupId(t.get(0, Integer.class));
            groupDetails.setGroupName(t.get(1, String.class));

            groupDetailsDtos.add(groupDetails);
        }

        return groupDetailsDtos;
    }

    public List<GroupDetails> getGroupsByUser(Integer userId) {
        List<Tuple> tuples = groupRepo.getGroupsByUser(userId);

        List<GroupDetails> groupDetailsDtos = new ArrayList<>();
        for (Tuple t : tuples) {
            GroupDetails dto = new GroupDetails();
            dto.setGroupId(t.get(0, Integer.class));
            dto.setGroupName(t.get(1, String.class));
            groupDetailsDtos.add(dto);
        }

        return groupDetailsDtos;
    }

    public void addUserToGroup(Integer groupId, Integer userId) throws ApiException {
        Optional<Group> groupOptional = groupRepo.findById(groupId);

        if (groupOptional.isEmpty()) {
            throw new ApiException("Group not found!", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = userService.getUser(userId);

        if (userOptional.isEmpty()) {
            throw new ApiException("User not found!",  HttpStatus.BAD_REQUEST);
        }

        Group group = groupOptional.get();
        User user = userOptional.get();

        group.getMembers().add(user);
        groupRepo.save(group);
    }

    public void removeUserFromGroup(Integer groupId, Integer userId) throws ApiException {
        Optional<Group> groupOptional = groupRepo.findById(groupId);

        if (groupOptional.isEmpty()) {
            throw new ApiException("Group not found!", HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = userService.getUser(userId);

        if (userOptional.isEmpty()) {
            throw new ApiException("User not found!", HttpStatus.BAD_REQUEST);
        }

        Group group = groupOptional.get();
        User user = userOptional.get();

        if (user.getUserId().equals(group.getAdmin().getUserId())) {
            throw new ApiException("Cannot remove admin!!", HttpStatus.NOT_ACCEPTABLE);
        }

        group.getMembers().removeIf((User member) -> member.getUserId().equals(user.getUserId()));
        groupRepo.save(group);
    }
}
