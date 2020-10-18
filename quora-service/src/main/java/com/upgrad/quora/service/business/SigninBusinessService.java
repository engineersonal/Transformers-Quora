package com.upgrad.quora.service.business;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * SigninBusinessService for user authenticaiton
 ****/
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class SigninBusinessService {

    //Respective Data access object has been autowired to access the method defined in respective Dao
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    /**
     * This method is used for the user to signin.
     *
     * @param username Username of the user who is singing in.
     * @param password Credentials of the user who is singing in
     * @return UserAuthEntity which contains the access-token and other details.
     * @throws AuthenticationFailedException ATH-001 if the username doesn't exist in DB or ATH-002 if
     *     the password is wrong.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAuthTokenEntity authenticate(final String username, final String password) throws AuthenticationFailedException {
        UserEntity userEntity = userDao.getUserByUserName(username);
        if (userEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This username does not exist");
        }
        final String encryptedPassword = cryptographyProvider.encrypt(password, userEntity.getSalt());
        if (encryptedPassword.equals(userEntity.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            UserAuthTokenEntity userAuthToken = new UserAuthTokenEntity();
            userAuthToken.setUser(userEntity);
            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            userAuthToken.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(), now, expiresAt));
            userAuthToken.setLoginAt(now);
            userAuthToken.setExpiresAt(expiresAt);
            userAuthToken.setUuid(userEntity.getUuid());
            userDao.createAuthToken(userAuthToken);
            userDao.updateUser(userEntity);
            return userAuthToken;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Password Failed");
        }
    }
}
