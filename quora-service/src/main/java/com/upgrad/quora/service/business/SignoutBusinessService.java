package com.upgrad.quora.service.business;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * SignoutBusiness service for verifyAuthToken
 ****/
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SignoutBusinessService {

        //Respective Data access object has been autowired to access the method defined in respective Dao
        @Autowired
        private UserDao userDao;

        /**
         * This method is used by user to signout.
         *
         * @param accessToken Access token of the user.
         * @return UserEntity details of the signed out user.
         * @throws SignOutRestrictedException SGR-001 if the access-token is not present in the DB.
         */
        @Transactional(propagation = Propagation.REQUIRED)
        public UserAuthTokenEntity verifyAuthToken(final String accessToken) throws SignOutRestrictedException {
            UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(accessToken);
            if (userAuthTokenEntity == null) {
                throw new SignOutRestrictedException("SGR-001", "User is not Signed in");
            } else {
                final ZonedDateTime now = ZonedDateTime.now();
                userAuthTokenEntity.setLogoutAt(now);
                return userAuthTokenEntity;
            }
        }
}
