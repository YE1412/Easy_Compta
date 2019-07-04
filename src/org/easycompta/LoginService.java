/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta;

/**
 *
 * @author Yannick
 */
public class LoginService {
    public static String setMessage(String name){
        return "Utilisateur "+ name +" innexistant ou mot de passe erroné !";
    }
}
