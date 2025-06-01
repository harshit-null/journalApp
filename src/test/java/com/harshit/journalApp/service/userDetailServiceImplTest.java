package com.harshit.journalApp.service;

import com.harshit.journalApp.entity.User;
import com.harshit.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
public class userDetailServiceImplTest {

   @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
         MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUserName(){
            when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("ram").roles(new ArrayList<>()).build());
            UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
