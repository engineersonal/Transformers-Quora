package com.upgrad.quora.api.controller;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * User Controller class for signup,signin and signout API
 ****/
import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignoutResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.SigninBusinessService;
import com.upgrad.quora.service.business.SignoutBusinessService;
import com.upgrad.quora.service.business.SignupBusinessService;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class UserController {
    //Required services are autowired to enable access to methods defined in respective Business services
	//Logger logger = LoggerFactory.getLogger(UserController.class);
	 
	@Autowired
    private SignupBusinessService signupBusinessService;

    @Autowired
    private SigninBusinessService signinBusinessService;

    @Autowired
    private SignoutBusinessService signoutBusinessService;

    /**
     * This method is for user signup. This method receives the object of SignupUserRequest type with
     * its attributes being set.
     *
     * @return SignupUserResponse - UUID of the user created.
     * @throws SignUpRestrictedException - if the username or email already exist in the database.
     */
   @RequestMapping(method = RequestMethod.POST, path = "/user/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest) throws SignUpRestrictedException {
       final UserEntity userEntity = new UserEntity();
        //logger.info("Inside user controller");
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(signupUserRequest.getFirstName());
        userEntity.setLastName(signupUserRequest.getLastName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setContactNumber(signupUserRequest.getContactNumber());
        userEntity.setCountry(signupUserRequest.getCountry());
        userEntity.setDob(signupUserRequest.getDob());
        userEntity.setUserName(signupUserRequest.getUserName());
        userEntity.setAboutMe(signupUserRequest.getAboutMe());
        userEntity.setSalt("1234abc");
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setRole("nonadmin");
        //call signupservice method signup
        final UserEntity createdUserEntity = signupBusinessService.signup(userEntity,signupUserRequest.getUserName(),signupUserRequest.getEmailAddress());
        SignupUserResponse userResponse = new SignupUserResponse()
                .id(createdUserEntity.getUuid())
                .status("USER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupUserResponse>(userResponse, HttpStatus.CREATED);
   }

    /**
     * This method is for a user to singin.
     *
     * @param authorization is basic auth (base 64 encoded). Usage: Basic <Base 64 Encoded
     *     username:password>
     * @return SigninResponse which contains user id and a access-token in the response header.
     * @throws AuthenticationFailedException ATH-001 if username doesn't exist, ATH-002 if password is
     *     wrong.
     */
   @RequestMapping(method = RequestMethod.POST, path = "/user/signin", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
        public ResponseEntity<SigninResponse> login(@RequestHeader("authentication") final String authentication) throws AuthenticationFailedException {
          byte[] decode = Base64.getDecoder().decode(authentication.split("Basic ")[1]);
          String decodedText = new String(decode);
          String[] decodedArray = decodedText.split(":");
          //call signinservice method authenticate
          final UserAuthTokenEntity userAuthToken = signinBusinessService.authenticate(decodedArray[0], decodedArray[1]);
          UserEntity user = userAuthToken.getUser();

          SigninResponse signinResponse = new SigninResponse()
                  .id(user.getUuid())
                  .message("SIGNED IN SUCCESSFULLY");
          HttpHeaders headers = new HttpHeaders();
          headers.add("access-token", userAuthToken.getAccessToken());
          return new ResponseEntity<SigninResponse>(  signinResponse, headers, HttpStatus.OK);
   }

    /**
     * This method is used to signout user.
     *
     * @param accessToken Token used for authenticating the user.
     * @return UUID of the user who is signed out.
     * @throws SignOutRestrictedException if the
     */
   @RequestMapping(method=RequestMethod.POST,path="/user/signout",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
   public ResponseEntity<SignoutResponse> logout(@RequestHeader("authorization") final String accessToken)throws SignOutRestrictedException {
//       String [] bearerToken = accessToken.split("Bearer ");
       //call signoutservice method verifyAuthToken
//       final UserAuthTokenEntity userAuthTokenEntity=signoutBusinessService.verifyAuthToken(bearerToken[1]);
       final UserAuthTokenEntity userAuthTokenEntity=signoutBusinessService.verifyAuthToken(accessToken);
       SignoutResponse signoutResponse=new SignoutResponse()
               .id(userAuthTokenEntity.getUuid())
               .message("SIGNED OUT SUCCESSFULLY");
       return new ResponseEntity<SignoutResponse>(signoutResponse,HttpStatus.OK);
   }
}


