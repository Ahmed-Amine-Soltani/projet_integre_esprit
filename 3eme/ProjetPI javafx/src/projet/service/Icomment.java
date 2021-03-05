/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.service;

import java.util.List;
import projet.models.comment;

/**
 *
 * @author Moez
 */
public interface Icomment {
    
    public List<comment> ListCommentaire();
    public int ajouterCommentaireForum(comment cf);
    public List<String> afficherCommentaire(int IdForum);
    public void supprimer(int x);
    public List<comment> rechercheCommentaires(String str);
    
}
