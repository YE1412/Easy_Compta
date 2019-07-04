/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.domain;

import java.io.Serializable;

import lombok.ToString;

/**
 *
 * @author Yannick
 */
@ToString(of = {"login", "lastname", "firstname", "mail"})
public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String login;
    private String lastname;
    private String firstname;
    private String password;
    private String password_confirm;
    private String mail;
    private String mail_confirm;

    public String getPassword_confirm() {
        return password_confirm;
    }

    public String getMail_confirm() {
        return mail_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    public void setMail_confirm(String mail_confirm) {
        this.mail_confirm = mail_confirm;
    }

    
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }
    
//    @Override
//    public String toString() {
//
//        StringBuilder buffer = new StringBuilder();
//        buffer.append("Login: ");
//        buffer.append(login);
//        buffer.append(";FirstName: ");
//        buffer.append(firstname);
//        buffer.append(";LastName: ");
//        buffer.append(lastname);
//        buffer.append(";Mail: ");
//        buffer.append(mail);
//        buffer.append(";Password: ");
//        buffer.append(password);
//        buffer.append(";Id: ");
//        buffer.append(getId());
//        return buffer.toString();
//
//    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
