/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.domain.service;

import java.util.List;
import org.easycompta.domain.User;

/**
 *
 * @author Yannick
 */
public interface UserManager {
    public void validateUser();    
    public List<User> getUsers();
    public User getUserById(int id);
}
