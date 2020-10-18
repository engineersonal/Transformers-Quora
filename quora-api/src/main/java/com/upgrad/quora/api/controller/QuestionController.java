package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/")
public class QuestionController {


    @Autowired
    private QuestionService questionService;



    @RequestMapping(method = RequestMethod.GET,path = "/question/all",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        List<QuestionEntity> questionEntities = questionService.getAllQuestions(authorization);

        List<QuestionDetailsResponse> questionDetailsResponseList = new LinkedList<>();//list is created to return.


        for(QuestionEntity questionEntity:questionEntities){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(questionEntity.getUuid()).content(questionEntity.getContent());
            questionDetailsResponseList.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.GET,path = "question/all/{userId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestionsByUser(@PathVariable(value = "userId")final String uuid,@RequestHeader(value = "authorization")final String authorization) throws UserNotFoundException, AuthorizationFailedException {

        List<QuestionEntity> questionEntities = questionService.getAllQuestionByUser(uuid, authorization);

        List<QuestionDetailsResponse> questionDetailsResponseList = new LinkedList<>();//list is created to return.


        for(QuestionEntity questionEntity:questionEntities){
            QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse().id(questionEntity.getUuid()).content(questionEntity.getContent());
            questionDetailsResponseList.add(questionDetailsResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList, HttpStatus.OK);

    }


    @RequestMapping(method = RequestMethod.POST, path="/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionRequest.getContent());
        questionEntity.setDate(LocalDateTime.now());
        questionEntity.setUuid(UUID.randomUUID().toString());
        QuestionEntity createdQuestion = questionService.createQuestionForUser(questionEntity, authorization);
        QuestionResponse questionResponse = new QuestionResponse().id(createdQuestion.getUuid()).status("QUESTION CREATED");
        return  new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);

    }


    @RequestMapping(method = RequestMethod.PUT, path = "/question/edit/{question_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(final QuestionEditRequest questionEditRequest, @PathVariable(value = "question_id") final String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(questionEditRequest.getContent());
        questionEntity.setDate(LocalDateTime.now());
        QuestionEntity editedQuestion = questionService.editQuestion(questionId, questionEntity, authorization);
        QuestionEditResponse questionEditResponse = new QuestionEditResponse().id(editedQuestion.getUuid()).status("QUESTION EDITED");
        return new ResponseEntity<QuestionEditResponse>(questionEditResponse, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable(value = "questionId") final String questionId, @RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, InvalidQuestionException {
        QuestionEntity deletedQuestion = questionService.deleteQuestion(questionId, authorization);
        QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse().id(deletedQuestion.getUuid()).status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
    }


}