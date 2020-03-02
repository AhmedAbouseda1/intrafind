package com.intrafind.user.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrafind.user.IntraFindApplicationTests;
import com.intrafind.user.model.dao.User;
import com.intrafind.user.model.entity.UserEntity;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends IntraFindApplicationTests {

    private final static String FIND_ALL_URL = "/v1/user/findAllUsers";

    private final static String GET_ALL_URL = "/v1/user/getUser/{id}";

    private final static String ADD_ALL_URL = "/v1/user/addUser";

    private final static String DELETE_ALL_URL = "/v1/user/deleteUser/{id}";

    private final static String UPDATE_ALL_URL = "/v1/user/updateUser/{id}";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testFindAllUsersEmptyDB() throws Exception {
        this.mvc.perform(get(FIND_ALL_URL))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testFindAllUsers() throws Exception {
        User user = generateUser("first1", "email", "last1");
        UserEntity userEntity = userRepository.save(modelMapper.map(user, UserEntity.class));
        this.mvc.perform(get(FIND_ALL_URL))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":" + userEntity.getId() +
                        ",\"firstName\":first1,\"lastName\":last1,\"emailAddress\":\"email\"}]"));
    }

    @Test
    public void testDeleteUsers() throws Exception {
        User user = generateUser("first1", "email", "last1");
        UserEntity userEntity = userRepository.save(modelMapper.map(user, UserEntity.class));
        this.mvc.perform(get(FIND_ALL_URL))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":" + userEntity.getId() +
                        ",\"firstName\":first1,\"lastName\":last1,\"emailAddress\":\"email\"}]"));
        this.mvc.perform(delete(DELETE_ALL_URL, userEntity.getId()))
                .andExpect(status().isOk());
        this.mvc.perform(get(FIND_ALL_URL))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddUsers() throws Exception {
        User user = generateUser("firstAdded", "emailAdded", "lastAdded");
        this.mvc.perform(post(ADD_ALL_URL).contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isOk());

        UserEntity userEntity = userRepository.findAll().get(0);
        assertUser(user, userEntity);
    }

    @Test
    public void testUpdateUsers() throws Exception {

        User user = generateUser("first1", "email", "last1");

        UserEntity userEntity = userRepository.save(modelMapper.map(user, UserEntity.class));
        User updateUser = generateUser("firstUpdated", "emailUpdated", "lastUpdated");
        this.mvc.perform(put(UPDATE_ALL_URL, userEntity.getId()).contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(updateUser)))
                .andExpect(status().isOk());

        UserEntity userEntityAfterUpdate = userRepository.findById(userEntity.getId()).orElse(new UserEntity());
        assertUser(updateUser, userEntityAfterUpdate);
    }

    private User generateUser(String firstName, String email, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setEmailAddress(email);
        user.setLastName(lastName);
        return user;
    }

    private void assertUser(User user, UserEntity userEntityAfterUpdate) {
        assertEquals(user.getEmailAddress(), userEntityAfterUpdate.getEmailAddress());
        assertEquals(user.getFirstName(), userEntityAfterUpdate.getFirstName());
        assertEquals(user.getLastName(), userEntityAfterUpdate.getLastName());
    }
}