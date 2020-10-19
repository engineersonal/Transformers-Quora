package com.upgrad.quora.service.dao;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * UserDAO with methods
 ****/
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

    //Named queries created according to the functionality as suggested by the name of the respective methods

    //Creates a user and inserts a record in the users table
    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    //Gets a user by email
    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Gets a user based on username
    public UserEntity getUserByUserName(final String username) {
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", username).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Gets a user based on user id
    public UserEntity getUserByUuid(final String uuid){
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Creates auth token and inserts a row in user_auth table
    public UserAuthTokenEntity createAuthToken(final UserAuthTokenEntity userAuthTokenEntity) {
        entityManager.persist(userAuthTokenEntity);
        return userAuthTokenEntity;
    }

    //Updates a user
    public void updateUser(final UserEntity updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }

    //Gets the user auth token
    public UserAuthTokenEntity getUserAuthToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Gets auth token for a particular user id
    public UserAuthTokenEntity getUserAuthTokenByUuid(final String uuid){
        try {
            return entityManager.createNamedQuery("userAuthTokenByUuid", UserAuthTokenEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {

            return null;
        }
    }

    //Deletes a particular user
    public String deleteUser(final UserEntity userEntity) {
        String uuid=userEntity.getUuid();
        entityManager.remove(userEntity);
        return uuid;
    }
}