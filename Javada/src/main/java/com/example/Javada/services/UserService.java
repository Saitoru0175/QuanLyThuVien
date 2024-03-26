package com.example.Javada.services;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Category;
import com.example.Javada.entity.role;
import com.example.Javada.entity.user;
import com.example.Javada.repository.IRoleRepository;
import com.example.Javada.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public void save(user user){
        userRepository.save(user);
        Long userId= userRepository.getUSerIdByUsername(user.getUsername());
        Long roleId= roleRepository.getRoleIdByName("member");
        if(roleId!=0 && userId!=0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }

    public List<user> getAllUsers(){
        return userRepository.findAll();
    }

    public user getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public void addBook(user user){
        userRepository.save(user);
    }

    public user saveUser(user user){return  userRepository.save(user);}

    public List<String> getRoleNames() {
        List<role> roles = roleRepository.findAll();
        List<String> roleNames = roles.stream()
                .map(role::getTenrole)
                .collect(Collectors.toList());
        return roleNames;
    }
}
