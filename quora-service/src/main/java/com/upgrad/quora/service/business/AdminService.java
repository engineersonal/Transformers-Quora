/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    UserDao userDao;

    /**
     * Deletes a user in the database
     *
     * @param targetUuid: Contains the user id
     * @param token: To authenticate the user
     * @return UserEntity: Returns the deleted User Entity
     * @throws AuthorizationFailedException ATHR-001: if User has not signed in
     *                                      ATHR-002: if the User is signed out
     *                                      ATHR-003: Unauthorized Access, Entered user is not an admin
     * @throws UserNotFoundException USR-001: User with entered uuid to be deleted does not exist
     */
    //deleteUser method considers user id and access token as input parameters for deleting a user.
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(String targetUuid, String token) throws AuthorizationFailedException, UserNotFoundException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out");
        }

        if (!userAuth.getUser().getRole().equalsIgnoreCase("admin")) {
            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");
        }

        UserEntity userEntity = userDao.getUserByUuid(targetUuid);
        if (userEntity == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
        }

        userDao.deleteUser(userEntity);
        return userEntity;

    }

}