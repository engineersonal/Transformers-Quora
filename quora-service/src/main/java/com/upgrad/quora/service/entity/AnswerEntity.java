<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
=======
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
<<<<<<< HEAD

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author nmu
 */
@Entity
@Table(name = "answer", schema = "quora")
@NamedQueries(
        {
                @NamedQuery(name = "getAnswerByUuid", query = "select u from AnswerEntity u where u.uuid=:uuid")
        }
)
public class AnswerEntity implements Serializable {

=======
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Entity
@Table(name = "answer")
@NamedQueries({
        @NamedQuery(name = "getAnswerById", query = "select a from AnswerEntity a where a.uuid=:uuid"),
        @NamedQuery(name = "getAllAnswersToQuestion",
                query = "select a from AnswerEntity a where a.uuid = :uuid")
})
public class AnswerEntity {
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
<<<<<<< HEAD
=======
    @NotNull
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    private String uuid;

    @Column(name = "ans")
    @Size(max = 255)
<<<<<<< HEAD
    private String answer;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
=======
    @NotNull
    private String answer;

    @Column(name = "date")
    @NotNull
    private ZonedDateTime date;

//    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "user_id")
//    private UserEntity userEntity;
//
//    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "question_id")
//    private QuestionEntity questionEntity;
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4

    public Integer getId() {
        return id;
    }

<<<<<<< HEAD
    public String getUuid() {
        return uuid;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public UserEntity getUser() {
        return user;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

=======
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setId(Integer id) {
        this.id = id;
    }

<<<<<<< HEAD
=======
    public String getUuid() {
        return uuid;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

<<<<<<< HEAD
=======
    public String getAnswer() {
        return answer;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setAnswer(String answer) {
        this.answer = answer;
    }

<<<<<<< HEAD
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
=======
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

//    public UserEntity getUserEntity() {
//        return userEntity;
//    }
//
//    public void setUserEntity(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }
//
//    public QuestionEntity getQuestionEntity() {
//        return questionEntity;
//    }
//
//    public void setQuestionEntity(QuestionEntity questionEntity) {
//        this.questionEntity = questionEntity;
//    }
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4

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
<<<<<<< HEAD

}
=======
}
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
