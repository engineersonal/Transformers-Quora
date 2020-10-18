/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity createQuestionForUser(QuestionEntity questionEntity) {

        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public List<QuestionEntity> getAllQuestions() {

        return entityManager.createNamedQuery("getAllQuestions", QuestionEntity.class).getResultList();
    }

    public QuestionEntity editQuestion(QuestionEntity questionEntity) {

        entityManager.merge(questionEntity);
        return questionEntity;
    }

    public QuestionEntity deleteQuestion(QuestionEntity questionEntity) {

        entityManager.remove(questionEntity);
        return questionEntity;
    }

    public QuestionEntity getQuestionByUuid(String uuid) {

        try {
            return entityManager.createNamedQuery("getQuestByUuid", QuestionEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List <QuestionEntity> getAllQuestionsByUser(final String uuid) {
        try {
            return entityManager.createNamedQuery("allQuestionsByUserId", QuestionEntity.class).setParameter("uuid", uuid).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}