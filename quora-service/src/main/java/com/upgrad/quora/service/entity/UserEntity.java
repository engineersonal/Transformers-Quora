/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.upgrad.quora.service.entity;

=======
package com.upgrad.quora.service.entity;
/***Author : Anitha Rajamuthu 
 * Date: 17-Oct-2020
 * User Entity against users table
 ****/
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
<<<<<<< HEAD
=======
import javax.validation.constraints.NotNull;
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
<<<<<<< HEAD
@Table(name = "users", schema = "quora")
@NamedQueries(
        {
                @NamedQuery(name = "userByUsername", query = "select u from UserEntity u where u.username=:username")
                ,
                @NamedQuery(name = "userByEmailId", query = "select u from UserEntity u where u.email=:email")
                ,
                @NamedQuery(name = "userByUuid", query = "select u from UserEntity u where u.uuid=:userUuid")
        }
)
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "firstname")
    @Size(max = 30)
    private String firstname;

    @Column(name = "lastname")
    @Size(max = 30)
    private String lastname;

    @Column(name = "username")
    @Size(max = 30)
    private String username;

    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "password")
    @Size(max = 255)
    private String password;

    @Column(name = "salt")
    @Size(max = 200)
    private String salt;

    @Column(name = "country")
    @Size(max = 30)
    private String country;

    @Column(name = "aboutme")
    @Size(max = 50)
    private String aboutMe;

    @Column(name = "dob")
    @Size(max = 30)
    private String dob;

    @Column(name = "role")
    @Size(max = 30)
    private String role;

    @Column(name = "contactnumber")
    @Size(max = 30)
    private String contactNumber;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private ArrayList<QuestionEntity> questionList = new ArrayList();

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getCountry() {
        return country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getDob() {
        return dob;
    }

    public String getRole() {
        return role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
=======
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email = :email"),
        @NamedQuery(name = "userByUserName", query = "select u from UserEntity u where u.userName = :userName"),
        @NamedQuery(name ="userByUuid",query="select u from UserEntity u where u.uuid =:uuid")
})
public class UserEntity implements Serializable {
    //primary key
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID")
    @Size(max = 200)
    private String uuid;

    @Column(name = "FIRSTNAME")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @Column(name = "LASTNAME")
    @NotNull
    @Size(max = 30)
    private String lastName;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 30)
    private String userName;

    @Column(name = "EMAIL")
    @NotNull
    @Size(max = 50)
    private String email;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "SALT")
    @NotNull
    @Size(max = 200)
    //@ToStringExclude
    private String salt;

    @Column(name = "COUNTRY")
    @Size(max = 30)
    private String country;

    @Column(name = "ABOUTME")
    @Size(max = 50)
    private String aboutMe;

    @Column(name = "DOB")
    @Size(max = 30)
    private String dob;

    @Column(name = "ROLE")
    @Size(max = 30)
    private String role;

    @Column(name = "CONTACTNUMBER")
    @Size(max = 30)
    private String contactNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    }

    public void setEmail(String email) {
        this.email = email;
    }

<<<<<<< HEAD
=======
    public String getPassword() {
        return password;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setPassword(String password) {
        this.password = password;
    }

<<<<<<< HEAD
=======
    public String getSalt() {
        return salt;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setSalt(String salt) {
        this.salt = salt;
    }

<<<<<<< HEAD
=======
    public String getCountry() {
        return country;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setCountry(String country) {
        this.country = country;
    }

<<<<<<< HEAD
=======
    public String getAboutMe() {
        return aboutMe;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

<<<<<<< HEAD
=======
    public String getDob() {
        return dob;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setDob(String dob) {
        this.dob = dob;
    }

<<<<<<< HEAD
=======
    public String getRole() {
        return role;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setRole(String role) {
        this.role = role;
    }

<<<<<<< HEAD
=======
    public String getContactNumber() {
        return contactNumber;
    }

>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
<<<<<<< HEAD
}
=======

}










>>>>>>> 097773e9249a9bc92de89fbeb54ff213e71a0cc4
