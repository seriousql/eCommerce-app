package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void testCreateUser(){
        when(encoder.encode("testPassword")).thenReturn("hashedPassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0,u.getId());
        assertEquals("test",u.getUsername());
        assertEquals("hashedPassword",u.getPassword());
    }

    @Test
    public void testBadPassword() {
        when(encoder.encode("testPassword")).thenReturn("hashedPassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("Passwordtest");

        ResponseEntity<User> response = userController.createUser(r);
        User u = response.getBody();
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testFindByUserName(){
        when(encoder.encode("testPassword")).thenReturn("hashedPassword");
        Cart cart = new Cart();
        User user = new User();
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setId(0);
        user.setCart(cart);

        when(userRepository.findByUsername("test")).thenReturn(user);
        ResponseEntity<User> res = userController.findByUserName("test");
        assertEquals(200, res.getStatusCodeValue());
        assertEquals("test",res.getBody().getUsername());
    }

    @Test
    public void testFindByUserId(){
        when(encoder.encode("testPassword")).thenReturn("hashedPassword");
        Cart cart = new Cart();
        User user = new User();
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setId(0);
        user.setCart(cart);

        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
        ResponseEntity<User> res = userController.findById(0L);
        assertEquals(200, res.getStatusCodeValue());
        assertEquals("test",res.getBody().getUsername());
    }

}
