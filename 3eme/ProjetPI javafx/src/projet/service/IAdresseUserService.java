/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import projet.models.Adresse;

/**
 *
 * @author yassine
 */
public interface IAdresseUserService {
    
    public Adresse getAdresse(); 
    public boolean updateAdresse(Adresse adresse); 
}
