package com.harshit.journalApp.service;

import com.harshit.journalApp.entity.User;
import com.harshit.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;




    public Boolean saveNewUser(User user){
        try{ user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        return true;
        }
        catch (Exception e){
            log.info("error occured for {}",user.getUserName(), e);
            log.warn("error occured for {}",user.getUserName(), e);
            log.error("error occured for {}",user.getUserName(), e);
            log.debug("error occured for {}",user.getUserName(), e);

            return false;

        }


    }

    public void saveAdmin(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);

    }

    public List<User> getAll(){
    return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String UserName){
        return userRepository.findByUserName(UserName);
    }


}
