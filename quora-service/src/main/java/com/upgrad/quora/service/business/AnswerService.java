package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity createAnswer(
            AnswerEntity answerEntity, final String accessToken, final String questionId)
            throws AuthorizationFailedException, InvalidQuestionException {

       // UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
//        if (userAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
//        } else if (userAuthEntity.getLogoutAt() != null) {
//            throw new AuthorizationFailedException(
//                    "ATHR-002", "User is signed out.Sign in first to post an answer");
//        }
//        QuestionEntity questionEntity = questionDao.getQuestionById(questionId);
//        if (questionEntity == null) {
//            throw new InvalidQuestionException("QUES-001", "The question entered is invalid");
//        }
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setDate(ZonedDateTime.now());
//        answerEntity.setQuestionEntity(questionEntity);
//        answerEntity.setUserEntity(userAuthEntity.getUserEntity());

        return answerDao.createAnswer(answerEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity editAnswer(
            final String accessToken,final String answerId, final String newAnswer)
            throws AnswerNotFoundException, AuthorizationFailedException {
//        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
//        if (userAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
//        } else if (userAuthEntity.getLogoutAt() != null) {
//            throw new AuthorizationFailedException(
//                    "ATHR-002", "User is signed out.Sign in first to edit an answer");
//        }
        AnswerEntity answerEntity = answerDao.getAnswerById(answerId);
        if (answerEntity == null) {
            throw new AnswerNotFoundException("ANS-001", "Entered answer uuid does not exist");
        }
//        if (!answerEntity.getUserEntity().getUuid().equals(userAuthEntity.getUserEntity().getUuid())) {
//            throw new AuthorizationFailedException(
//                    "ATHR-003", "Only the answer owner can edit the answer");
//        }
        answerEntity.setAnswer(newAnswer);
        answerDao.updateAnswer(answerEntity);
        return answerEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity deleteAnswer(
            final String answerId, final String accessToken)
            throws  AnswerNotFoundException, AuthorizationFailedException {
//        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
//        if (userAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
//        } else if (userAuthEntity.getLogoutAt() != null) {
//            throw new AuthorizationFailedException(
//                    "ATHR-002", "User is signed out.Sign in first to delete an answer");
//        }
        AnswerEntity answerEntity = answerDao.getAnswerById(answerId);
        if (answerEntity == null) {
            throw new AnswerNotFoundException("ANS-001", "Entered answer uuid does not exist");
        }
//        if (userAuthEntity.getUserEntity().getRole().equals("admin")
//                || answerEntity
//                .getUserEntity()
//                .getUuid()
//                .equals(userAuthEntity.getUserEntity().getUuid())) {
//            return answerDao.deleteAnswer(answerId);
//        } else {
//            throw new AuthorizationFailedException(
//                    "ATHR-003", "Only the answer owner or admin can delete the answer");
//        }
        return null;
    }

    public List<AnswerEntity> getAllAnswersToQuestion(
            final String questionId, final String accessToken) throws  InvalidQuestionException,AuthorizationFailedException {
//        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthByToken(accessToken);
//        if (userAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
//        } else if (userAuthEntity.getLogoutAt() != null) {
//            throw new AuthorizationFailedException(
//                    "ATHR-002", "User is signed out.Sign in first to get the answers");
//        }
//        QuestionEntity questionEntity = questionDao.getQuestionById(questionId);
//        if (questionEntity == null) {
//            throw new InvalidQuestionException(
//                    "QUES-001", "The question with entered uuid whose details are to be seen does not exist");
//        }
        return answerDao.getAllAnswersToQuestion(questionId);
    }
}
