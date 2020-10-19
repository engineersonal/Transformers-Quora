package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminService;
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
public class AdminController {

    @Autowired
    private AdminService adminService;

    /** Admin deletes a user
     * @param userId: to delete a specific user
     * @param authorization: access token to authenticate user
     * @return UserDeleteResponse entity
     * @throws AuthorizationFailedException ATHR-001: if User has not signed in
     *                                      ATHR-002: if the User is signed out
     *                                      ATHR-003: Unauthorized Access, Entered user is not an admin
     * @throws UserNotFoundException USR-001: User with entered uuid to be deleted does not exist
     */
    //deleteUser method considers userid and authorization as input parameters for deleting a user. Only Admin can delete a user
    @RequestMapping(method = RequestMethod.DELETE , path = "/admin/user/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable("userId") final String userid, @RequestHeader("authorization")
    final String authorization) throws AuthorizationFailedException, UserNotFoundException {

        UserEntity userEntity = adminService.deleteUser(userid ,authorization);
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(userEntity.getUuid()).status("USER SUCCESSFULLY DELETED");
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }
}