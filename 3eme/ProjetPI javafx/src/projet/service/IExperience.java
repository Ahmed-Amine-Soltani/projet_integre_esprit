/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.List;
import projet.models.Experience;

/**
 *
 * @author houba
 */
public interface IExperience {

    public List<Experience> selectAll();

    public void insert(Experience e);

    public void supprimer(int x);
}
