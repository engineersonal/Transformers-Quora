package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private UserService userService;

    /** Gets the user profile details
     * @param userId: to delete a specific user
     * @param authorization: access token to authenticate user
     * @return UserDetailsResponse entity
     * @throws AuthorizationFailedException ATHR-001: if User has not signed in
     *                                      ATHR-002: if the User is signed out
     * @throws UserNotFoundException USR-001: User with entered user id does not exist
     */
    //getUserProfileById method considers user id and authorization as inputs and gets the profile details of a user
    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUserProfileById(@PathVariable("userId") final String userUuid, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, UserNotFoundException {
        UserEntity userEntity = userService.getUser(userUuid, authorization);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).userName(userEntity.getUserName())
                .emailAddress(userEntity.getEmail()).contactNumber(userEntity.getContactNumber())
                .dob(userEntity.getDob()).country(userEntity.getCountry()).aboutMe(userEntity.getAboutMe());
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);

    }
}