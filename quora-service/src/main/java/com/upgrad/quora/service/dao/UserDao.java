/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity getUserByUsername(String username) {

        try {
            return entityManager.createNamedQuery("userByUsername", UserEntity.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserEntity getUserByUuid(String uuid) {

        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("userUuid", uuid).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserEntity getUserByEmail(String email) {

        try {
            return entityManager.createNamedQuery("userByEmailId", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserAuthTokenEntity getUserAuthByToken(String token) {

        try {
            return entityManager.createNamedQuery("userAuthByToken", UserAuthTokenEntity.class).setParameter("token", token).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserAuthTokenEntity getUserAuthByUserId(Integer userId) {

        try {
            return entityManager.createNamedQuery("userAuthByUserId", UserAuthTokenEntity.class).setParameter("user_id", userId).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public UserEntity deleteUser(UserEntity userEntity) {

        UserAuthTokenEntity userAuthTokenEntity = getUserAuthByUserId(userEntity.getId());
        entityManager.remove(userEntity);
        entityManager.remove(userAuthTokenEntity);
        return userEntity;
    }

}