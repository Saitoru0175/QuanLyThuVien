package com.example.Javada.repository;

import com.example.Javada.entity.user;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<user, Long> {
    @Query("SELECT u FROM user u WHERE u.username = ?1")
    user findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value ="INSERT INTO quyen (user_id,role_id)"+"VALUES(?1,?2)",nativeQuery = true)
    void addRoleToUser(Long userId,Long roleId);
    @Query("SELECT u.id FROM user u WHERE u.username=?1")
    Long getUSerIdByUsername(String username);
    @Query(value="SELECT r.tenrole FROM role r INNER JOIN quyen ur " + "ON r.idrole=ur.role_id WHERE ur.user_id=?1",nativeQuery = true)
    String[] getRolesOfUser(Long userId);
}
