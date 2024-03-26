package com.example.Javada.repository;

import com.example.Javada.entity.role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<role, Long> {
    @Query("SELECT r.idrole FROM role r WHERE r.tenrole = ?1")
    Long getRoleIdByName(String roleName);
}
