package com.harshit.journalApp.service;

import com.harshit.journalApp.entity.User;
import com.harshit.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Disabled
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
 @ArgumentsSource(UserArgumentsProvider.class)
    @Disabled
    public void testFindByUserName(User user){
        assertTrue(userService.saveNewUser(user));
    }


    @Disabled
    @ParameterizedTest
@CsvSource({
        "1,1,2",
        "2,10,12",
        "3,3,6"
})
    public void test(int a , int b, int expected){
        assertEquals( expected ,a+b);
    }
}
