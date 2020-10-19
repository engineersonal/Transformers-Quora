package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.*;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//RestController annotation specifies that this class represents a REST API(equivalent of @Controller + @ResponseBody)
@RestController
@RequestMapping("/")
public class QuestionController {
    //Required services are autowired to enable access to methods defined in respective Business services
    @Autowired
    private CreateQuestionBusinessService createQuestionBusinessService;

    @Autowired
    private GetAllQuestionsBusinessService getAllQuestionsBusinessService;

    @Autowired
    private GetAllQuestionsByUserBusinessService getAllQuestionsByUserBusinessService;

    @Autowired
    EditQuestionContentBusinessService editQuestionContentBusinessService;

    @Autowired
    DeleteQuestionBusinessService deleteQuestionBusinessService;

    /**
     * This API creates a question in the database.
     *
     * @param accessToken: To authenticate the user who is trying to create a question
     * @param questionRequest: Contains the question content
     * @return QuestionResponse entity
     * @throws AuthorizationFailedException ATHR-001 If the user has not signed in and ATHR-002 If the
     *     user is already signed out
     */
    //createQuestion method takes accessToken for doing Bearer Authorization and creates the question by the signed in user
    @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(@RequestHeader("authorization") final String accessToken,
                                                           final QuestionRequest questionRequest) throws AuthorizationFailedException {

        final UserAuthTokenEntity userAuthTokenEntity = createQuestionBusinessService.verifyAuthToken(accessToken);
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setUser(userAuthTokenEntity.getUser());
        questionEntity.setContent(questionRequest.getContent());
        final ZonedDateTime now = ZonedDateTime.now();
        questionEntity.setDate(now);
        final QuestionEntity createdQuestionEntity = createQuestionBusinessService.createQuestion(questionEntity);

        QuestionResponse questionResponse = new QuestionResponse()
                .id(createdQuestionEntity.getUuid())
                .status("QUESTION CREATED");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }

    /**
     * This API gets all the questions
     *
     * @param accessToken: To authenticate the user
     * @return List of QuestionDetailsResponse entity
     * @throws AuthorizationFailedException ATHR-001 If the user has not signed in and ATHR-002 If the
     *     user is already signed out
     */
    //getAllQuestions method takes accessToken for doing Bearer Authorization and returns all the questions created
    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException {
        getAllQuestionsBusinessService.verifyAuthToken(accessToken);
        List<QuestionEntity> allQuestions = new ArrayList<QuestionEntity>();
        allQuestions.addAll(getAllQuestionsBusinessService.getAllQuestions());
        List<QuestionDetailsResponse> questionDetailsResponses = new ArrayList<QuestionDetailsResponse>();

        for (QuestionEntity question : allQuestions) {
            QuestionDetailsResponse questionDetailsResponse=new QuestionDetailsResponse();
            questionDetailsResponses.add(questionDetailsResponse
                    .id(question.getUuid())
                    .content(question.getContent()));
        }
        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponses,HttpStatus.OK);
    }

    /**
     * This API gets all the questions for a specific user
     *
     * @param accessToken: To authenticate the user who is trying to create a question
     * @param userId: User Id
     * @return List of QuestionDetailsResponse entity
     * @throws AuthorizationFailedException ATHR-001: if User has not signed in
     *                                      ATHR-002: if the User is signed out
     * @throws UserNotFoundException USR-001: User with entered does not exist
     */
    //getAllQuestionsByUser method takes userId to retrieve user and accessToken for doing Bearer Authorization and returns all the questions created by a specific user
    @RequestMapping(method = RequestMethod.GET, path = "question/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestionsByUser(@PathVariable("userId")final String userId,
                                                                               @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, UserNotFoundException {
        getAllQuestionsByUserBusinessService.verifyAuthTokenAndUuid(userId,accessToken);
        final UserAuthTokenEntity userAuthTokenEntity= getAllQuestionsByUserBusinessService.getUserAuthTokenByUuid(userId);
        List<QuestionEntity> allQuestionsByUser = new ArrayList<QuestionEntity>();
        allQuestionsByUser.addAll(getAllQuestionsByUserBusinessService.getAllQuestionsByUserId((userAuthTokenEntity.getUser())));
        List<QuestionDetailsResponse> questionDetailsResponses = new ArrayList<QuestionDetailsResponse>();

        for (QuestionEntity question : allQuestionsByUser) {
            QuestionDetailsResponse questionDetailsResponse=new QuestionDetailsResponse();
            questionDetailsResponses.add(questionDetailsResponse
                    .id(question.getUuid())
                    .content(question.getContent()));
        }
        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponses,HttpStatus.OK);
    }

    /**
     * This API edits the question which already exist in the database.
     *
     * @param accessToken: To authenticate the user who is trying to edit the question
     * @param questionId: Id of the question which is to be edited
     * @param questionEditRequest Contains the new content of the question
     * @return QuestionEditResponse entity
     * @throws AuthorizationFailedException ATHR-001 If the user has not signed in and ATHR-002 If the
     *     user is already signed out and ATHR-003 if the user is not the owner of the question.
     * @throws InvalidQuestionException QUES-001 if the question is not found in the database.
     */
    //editQuestionContent method takes questionId to retrieve question and accessToken for doing Bearer Authorization and returns the edit question response
    @RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestionContent(final QuestionEditRequest questionEditRequest ,
                                                                    @PathVariable("questionId") final String questionId,
                                                                    @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, InvalidQuestionException {

        final QuestionEntity questionEntity = editQuestionContentBusinessService.verifyUserStatus(questionId,accessToken);
        questionEntity.setContent(questionEditRequest.getContent());
        final QuestionEntity updatedQuestionEntity = editQuestionContentBusinessService.updateQuestion(questionEntity);

        QuestionEditResponse questionEditResponse = new QuestionEditResponse()
                .id(updatedQuestionEntity.getUuid())
                .status("QUESTION EDITED");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK);
    }

    /**
     * delete a question using questionId
     *
     * @param questionId id of the question to be deleted
     * @param accessToken token to authenticate user
     * @return QuestionDeleteResponse entity with Id and status of the question deleted
     * @throws AuthorizationFailedException In case the access token is invalid.
     * @throws InvalidQuestionException if question with questionId doesn't exist.
     */
    //deleteQuestion method takes questionId to retrieve question and accessToken for doing Bearer Authorization and allows only the question owner or admin to delete the question
    @RequestMapping(method= RequestMethod.DELETE,path="/question/delete/{questionId}",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable("questionId") final String questionUuid,
                                                                 @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, InvalidQuestionException {

        final QuestionEntity questionEntityToDelete=deleteQuestionBusinessService.verifyQUuid(questionUuid);
        final UserEntity signedinUserEntity = deleteQuestionBusinessService.verifyAuthToken(accessToken);
        final String Uuid = deleteQuestionBusinessService.deleteQuestion( questionEntityToDelete,signedinUserEntity);

        QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse()
                .id(Uuid)
                .status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
    }
}

