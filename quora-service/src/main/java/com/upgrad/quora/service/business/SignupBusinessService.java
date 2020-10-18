package com.upgrad.quora.service.business;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * SignupBusiness service for user signup
 ****/
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupBusinessService {

    //Respective Data access object and passwordcryptogaphyprovider has been autowired to access the method defined in respective Dao
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;
    
    //Logger logger = LoggerFactory.getLogger(SignupBusinessService.class);


    /**
     * This method checks if the username and email exist in the DB. if the username or email doesn't
     * exist in the DB. Assign encrypted password and salt to the user.
     *
     * @throws SignUpRestrictedException SGR-001 if the username exist in the DB , SGR-002 if the
     *     email exist in the DB.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity,String userName,String email) throws SignUpRestrictedException {
        if (userDao.getUserByUserName(userName) != null) {
            throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken");
        } else if (userDao.getUserByEmail(email) != null) {
            throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
        } else {
            //Generates encrypted password and salt based on the password provided by user during signup
           // logger.info("Inside signupbusiness password:"userEntity.getPassword());

        	String[] encryptedText = passwordCryptographyProvider.encrypt(userEntity.getPassword());
            userEntity.setSalt(encryptedText[0]);
            userEntity.setPassword(encryptedText[1]);
            return userDao.createUser(userEntity);
        }
    }
}
