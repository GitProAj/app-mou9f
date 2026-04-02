package com.mou9f.repository;

import com.mou9f.entity.Role;
import com.mou9f.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRole(RoleName roleName);
}
