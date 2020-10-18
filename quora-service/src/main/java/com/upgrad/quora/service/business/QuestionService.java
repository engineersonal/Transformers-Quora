/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity createQuestionForUser(QuestionEntity questionEntity, String token) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthByToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to post a question");
        }

        questionEntity.setUser(userAuth.getUser());

        questionDao.createQuestionForUser(questionEntity);
        return questionEntity;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<QuestionEntity> getAllQuestions(String token) throws AuthorizationFailedException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthByToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get all questions");
        }

        List<QuestionEntity> questionEntityList = questionDao.getAllQuestions();
        return questionEntityList;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity editQuestion(String questUuid, QuestionEntity questionEntity, String token) throws AuthorizationFailedException, InvalidQuestionException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthByToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to edit the question");
        }

        QuestionEntity currentquestionEntity = questionDao.getQuestionByUuid(questUuid);

        if (currentquestionEntity == null) {
            throw new InvalidQuestionException("QUES-001", "Entered question uuid does not exist");
        }

        if (!currentquestionEntity.getUser().getUuid().equals(userAuth.getUser().getUuid())) {
            throw new AuthorizationFailedException("ATHR-003", "Only the question owner can edit the question");
        }
        currentquestionEntity.setContent(questionEntity.getContent());
        return questionDao.editQuestion(currentquestionEntity);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity deleteQuestion(String questUuid, String token) throws AuthorizationFailedException, InvalidQuestionException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthByToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to delete the question");
        }

        QuestionEntity questionEntity = questionDao.getQuestionByUuid(questUuid);

        if (questionEntity == null) {
            throw new InvalidQuestionException("QUES-001", "Entered question uuid does not exist");
        }

        if (!questionEntity.getUser().getUuid().equals(userAuth.getUser().getUuid())) {
            throw new AuthorizationFailedException("ATHR-003", "Only the question owner or admin can delete the question");
        }
        return questionDao.deleteQuestion(questionEntity);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<QuestionEntity> getAllQuestionByUser(String uuid, String token) throws AuthorizationFailedException, UserNotFoundException {

        UserAuthTokenEntity userAuth = userDao.getUserAuthByToken(token);
        if (userAuth == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }

        if (userAuth.getLogoutAt() != null && userAuth.getLogoutAt().isAfter(userAuth.getLoginAt())) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get all questions");
        }

        UserEntity userEntity = userDao.getUserByUuid(uuid);
        if (userEntity == null) {
            throw new UserNotFoundException("USR-001", "User with entered uuid whose question details are to be seen does not exist");
        }

        return questionDao.getAllQuestionsByUser(uuid);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity getQuestionByUuid(String uuid) {
        return questionDao.getQuestionByUuid(uuid);
    }

}