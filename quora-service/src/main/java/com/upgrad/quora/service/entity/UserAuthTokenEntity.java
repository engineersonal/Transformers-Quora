/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author nmu
 */
@Entity
@Table(name = "user_auth", schema = "quora")
@NamedQueries(
        {
                @NamedQuery(name = "userAuthByToken", query = "select u from UserAuthTokenEntity u where u.accessToken =:token")
                ,
                @NamedQuery(name = "userAuthByUserId", query = "select u from UserAuthTokenEntity u where u.user.uuid =:user_id")
        }
)

public class UserAuthTokenEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "access_token")
    @Size(max = 500)
    private String accessToken;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "login_at")
    private LocalDateTime loginAt;

    @Column(name = "logout_at")
    private LocalDateTime logoutAt;

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getLoginAt() {
        return loginAt;
    }

    public LocalDateTime getLogoutAt() {
        return logoutAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public void setLogoutAt(LocalDateTime logoutAt) {
        this.logoutAt = logoutAt;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}