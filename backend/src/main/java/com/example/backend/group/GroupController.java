package com.example.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.group.dtos.AddUserToGroupReq;
import com.example.backend.group.dtos.CreateGroupReqDto;
import com.example.backend.group.dtos.GroupDetails;
import com.example.backend.utils.ApiResponse;
import com.example.backend.utils.ApiResponseEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("create")
    public ApiResponseEntity<GroupDetails> createGroup(@Valid @RequestBody CreateGroupReqDto reqDto) {
        return ApiResponseEntity.Ok(groupService.createGroup(reqDto));
    }

    @GetMapping("groupDetails/{groupId}")
    public ApiResponseEntity<GroupDetails> getGroupDetails(@PathVariable Integer groupId) {
        return ApiResponseEntity.Ok(groupService.getGroupDetails(groupId));
    }

    @GetMapping("byAdmin/{userId}")
    public ResponseEntity<?> getGroupsForAdmin(@PathVariable Integer userId) {
        return ResponseEntity.ok(groupService.getGroupsByAdmin(userId));
    }

    @PostMapping("addUserToGroup")
    public ResponseEntity<String> addUserToGroup(@RequestBody AddUserToGroupReq reqDto) {
        groupService.addUserToGroup(reqDto.groupId(), reqDto.userId());
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("removeUserFromGroup")
    public ResponseEntity<String> removeUserFromGroup(@RequestBody AddUserToGroupReq reqDto) {
        groupService.removeUserFromGroup(reqDto.groupId(), reqDto.userId());
        return ResponseEntity.ok("Ok");
    }
}
