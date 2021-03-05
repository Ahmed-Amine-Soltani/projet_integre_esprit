/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Evenement;
import com.codename1.capture.Capture;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.io.IOException;
import java.util.Date;


/**
 *
 * @author solta
 */
public class ModifierEvenementForm extends BaseForm {
    
    private static String i;
    
    public ModifierEvenementForm(Resources res, Evenement evenModifier) {
        super(BoxLayout.y());
        
        Toolbar tb = new Toolbar(false);
        setToolbar(tb);
        tb.setTitle("Créer un événement");
        getContentPane().setScrollVisible(true);
        super.addSideMenu(res);
        
        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());
        
     
        /*
        Form previous = Display.getInstance().getCurrent();
        tb.addCommandToRightBar(null, res.getImage("back.png"), e -> previous.showBack());
         */
        TextField nomEvenement = new TextField("");
        nomEvenement.setText(evenModifier.getNom_evenement());
        addStringValue("Titre", nomEvenement);
        nomEvenement.addActionListener((e) -> {
            System.out.println(nomEvenement.getText());
        });
        
        Picker typeEvenement = new Picker();
        typeEvenement.setType(Display.PICKER_TYPE_STRINGS);
        typeEvenement.setSelectedString(evenModifier.getType_evenement());
        typeEvenement.setStrings("Associatif", "Culturel", "Autres");
        addStringValue("Type", typeEvenement);
        
        TextField lieuEvenement = new TextField("");
        lieuEvenement.setText(evenModifier.getLieu_evenement());
        addStringValue("Localisation", lieuEvenement);
        
        TextField capaciteEvenement = new TextField("");
        capaciteEvenement.setText(String.valueOf(evenModifier.getCapacite_evenement()));
        addStringValue("Capacité", capaciteEvenement);
        
        Picker dateDebutEvenement = new Picker();
        dateDebutEvenement.setDate(new Date());
        dateDebutEvenement.setType(Display.PICKER_TYPE_DATE);
        dateDebutEvenement.setDate(evenModifier.getDate_debut_evenement());
        dateDebutEvenement.addActionListener((datecheck) -> {
            Date datedeb = new Date();
            if (dateDebutEvenement.getDate().getTime() * 1000L < datedeb.getTime() * 1000L) {
                Dialog.show("Date invalide", "Veuillez choisir une date valide", "OK", null);
            }
        });
        addStringValue("Date Debut", dateDebutEvenement);
        
        Picker dateFinEvenement = new Picker();
        dateFinEvenement.setDate(new Date());
        dateFinEvenement.setType(Display.PICKER_TYPE_DATE);
        dateFinEvenement.setDate(evenModifier.getDate_fin_evenement());
        dateFinEvenement.addActionListener((datecheck) -> {
            Date datefin = new Date();
            if (dateFinEvenement.getDate().getTime() * 1000L < datefin.getTime() * 1000L) {
                Dialog.show("Date invalide", "Veuillez choisir une date valide", "OK", null);
            }
        });
        addStringValue("Date Fin", dateFinEvenement);
        
        TextArea descriptionEvenement = new TextArea();
        descriptionEvenement.setText(evenModifier.getDescription_evenement());
        addStringValue("Description", descriptionEvenement);
        
        Button afficheEvenementButton = new Button("Affiche");
        addStringValue("", afficheEvenementButton);
        
        ScaleImageLabel imageForm = new ScaleImageLabel();
        TextField path = new TextField("");
        
        Image afficheEvenement = UrlAffiche(evenModifier.getAffiche_evenement());
        imageForm.setIcon(afficheEvenement);
        
        afficheEvenementButton.addActionListener(e -> {
            i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (i != null) {
                Image im;
                try {
                    im = Image.createImage(i).scaledSmallerRatio(1000, 500);
                    imageForm.setIcon(im);
                    refreshTheme();
                    System.out.println("path :" + i);
                    path.setText(i);
                    
                } catch (IOException ex) {
                    System.out.println("Could not load image!");
                }
            } 
        });
        
        add(LayeredLayout.encloseIn(BorderLayout.centerAbsolute(imageForm)));
        Button modifierEvenementBouton = new Button("Modifier");
        addStringValue("", modifierEvenementBouton);
        
        Validator validateur = new Validator();
        validateur.addConstraint(nomEvenement, new LengthConstraint(1)).
                addConstraint(lieuEvenement, new RegexConstraint("^[a-zA-Z0-9 ]+$", "Veuillez choisir une localisation valide")).
                addConstraint(descriptionEvenement, new LengthConstraint(1));
        validateur.addSubmitButtons(modifierEvenementBouton);
        
        modifierEvenementBouton.addActionListener((edit) -> {
            Date dateDebut = dateDebutEvenement.getDate();
            Date dateFin = dateFinEvenement.getDate();
            
            if (path.getText().equals("")) {
                Dialog.show("Echec", "Veuillez uploader une affiche", "Ok", null);
                return;
            }
            Evenement evenement = new Evenement(SessionManager.getId(), evenModifier.getId_Evenement(), nomEvenement.getText(),
                    typeEvenement.getSelectedString(), lieuEvenement.getText(),
                    Integer.parseInt(capaciteEvenement.getText()), descriptionEvenement.getText(), path.getText(), dateDebut, dateFin);
            
            System.out.println(evenement.toString());
            
            Services.EvenementServices.modifierEvenement(evenement);
            Dialog.show("Succès", "Evénement modifié avec succès", "Ok", null);
            new MesEvenementsForm(res).show();
            refreshTheme();
        });
    }
    
    public Image UrlAffiche(String nomAffiche) {
        String url = "http://127.0.0.1:8000/api/Affiche?img=" + nomAffiche;
        EncodedImage placeholder = EncodedImage.createFromImage(Resources.getGlobalResources().getImage("load.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth())), false);
        Image urli = URLImage.createToStorage(placeholder, "Medium_" + url, url, URLImage.RESIZE_SCALE);
        
        return urli;
        
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
