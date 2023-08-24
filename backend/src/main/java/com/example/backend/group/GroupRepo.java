package com.example.backend.group;

import com.example.backend.group.dtos.GroupDetails;
import com.example.backend.group.entities.Group;

import jakarta.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GroupRepo extends CrudRepository<Group, Integer> {
    @Query(value = """
            SELECT group_id, group_name
            FROM groups
            WHERE group_id = ?
             """, nativeQuery = true)
    Tuple getGroupDetails(Integer groupId);

    @Query(value = """
            SELECT  group_id, group_name
            FROM groups
            WHERE admin_id = ?
            """, nativeQuery = true)
    List<Tuple> getGroupsByAdmin(Integer userId);

    @Query(value = """
            SELECT group_id, group_name
            FROM groups
            WHERE group_id IN (
                SELECT group_id
                FROM user_group_membership
                WHERE user_id = ?
            )
            """, nativeQuery = true)
    List<Tuple> getGroupsByUser(Integer userId);
}
