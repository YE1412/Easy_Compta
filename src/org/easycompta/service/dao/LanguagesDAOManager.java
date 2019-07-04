/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.Langue;

/**
 *
 * @author Yannick
 */
public interface LanguagesDAOManager {
     public List<Langue> getAllLanguages();
    public int insertLanguage(Langue fac);
    public int updateLanguage(Langue lan);
    public Langue getLanguageById(int id);
    public int deleteLanguage(int id);
}
