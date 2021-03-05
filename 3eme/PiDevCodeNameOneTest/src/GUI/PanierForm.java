/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Lignedepanier;
import Entities.Panier;
import Entities.Produit;
import Services.ServiceProduit;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yassine
 */
public class PanierForm {
    
    private Form form;
    private EncodedImage encodedImage;
    private ImageViewer imageViewer,imageViewer2;
    private Image img;
    private Panier panier = null;
    private ArrayList<Lignedepanier> lignes;
    
    public PanierForm( ) {
      
        
        initPanier();
        
    
    }

    public void initPanier(){
          
        
        panier = ServiceProduit.getPanier();
        
        Toolbar.setGlobalToolbar(true);

        form  = new Form("Panier", new BoxLayout(BoxLayout.Y_AXIS));
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        form.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        Font fontMedium = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);    

        Font fontLarge = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);    

        Label nbr_article = new Label(panier.getNbr_produit()+" articles");
        nbr_article.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        nbr_article.getAllStyles().setFont(fontMedium);
       
        Label total_nbr = new Label((panier.getTotal())+" TND");
        total_nbr.setAlignment(Component.RIGHT);
        total_nbr.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        total_nbr.getAllStyles().setFont(fontMedium);
        
        Label livraison = new Label("Livraison");
        livraison.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        livraison.getAllStyles().setFont(fontMedium);
        
        Label prix_liv = new Label("7,00 TND");
        prix_liv.setAlignment(Component.RIGHT);
        prix_liv.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        prix_liv.getAllStyles().setFont(fontMedium);
        
        Label total = new Label("Total");
        total.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        total.getAllStyles().setFont(fontLarge);
        
        Label prix_total = new Label(panier.getTotal()+" TND");
        prix_total.setAlignment(Component.RIGHT);
        prix_total.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        prix_total.getAllStyles().setFont(fontLarge);
      
        
        
     


       // Border border = Border.createCompoundBorder(null, Border.createLineBorder(1, 0x858585), null, null);
        TableLayout fullLayout = new TableLayout(1,2);
        
        Container container_total = new Container(fullLayout);
    
        container_total.add(fullLayout.createConstraint().widthPercentage(50), nbr_article).
            add(fullLayout.createConstraint().widthPercentage(50), total_nbr).add(fullLayout.createConstraint().widthPercentage(50), livraison).
            add(fullLayout.createConstraint().widthPercentage(50), prix_liv).add(fullLayout.createConstraint().widthPercentage(50), total).
            add(fullLayout.createConstraint().widthPercentage(50), prix_total);

        //container_total.getAllStyles().setBorder(border);
                
        
        
            
        lignes = ServiceProduit.getLignePanier(panier.getId());
        
        for (Lignedepanier ligne : lignes) {
            
            Produit produit = ServiceProduit.getProduit(ligne.getProduit_id());
            
            Container item = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Container info = new Container(new BoxLayout(BoxLayout.X_AXIS));
            Container details = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            
            TableLayout topLayout = new TableLayout(1,2);
            Container containerTop = new Container(topLayout);


            TableLayout totalLayout = new TableLayout(1,2);
            Container total_qte = new Container(totalLayout);
            
            
            
            try {
              encodedImage = (EncodedImage) EncodedImage.create("/load.png").fill(form.getWidth()/3, form.getWidth()/3);
            } catch (IOException ex) { }

            String url="http://localhost/worldfriendship/web/assets/img/product/"+produit.getFirstimg();

            
            Image img = URLImage.createToStorage(encodedImage,url, url, URLImage.RESIZE_SCALE);
            ImageViewer imageViewer = new ImageViewer(img);
            
            info.add(imageViewer);
            
            Font font = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);    

            FontImage iconDelete = FontImage.createMaterial(FontImage.MATERIAL_CLOSE, s, 4);

            
            Button btn_delete = new Button(iconDelete);
            btn_delete.setAlignment(Component.RIGHT);
            
            btn_delete.addActionListener(new ActionListener() {            
                @Override
                public void actionPerformed(ActionEvent evt) {
                 if( ServiceProduit.deleteLigne(ligne.getId(),produit.getId(),panier.getId())){
                     new PanierForm().getForm().show();
                 }
                 
                }
            });
                 
            Label libelle = new Label(produit.getLibelle());
            libelle.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
            libelle.getAllStyles().setFont(font);
            
            containerTop.add(topLayout.createConstraint().widthPercentage(80), libelle).
            add(topLayout.createConstraint().widthPercentage(20),btn_delete);
            
            
            SpanLabel desc = new SpanLabel(produit.getDescription().substring(0,70)+"...");
            desc.getTextAllStyles().setFont(font);
            
            details.add(containerTop);
            details.add(desc);
            
            info.add(details);
                   
            item.add(info);
            
            
            TextField qte = new TextField(String.valueOf(ligne.getQuantite()));
            
            qte.addDataChangedListener((i, ii) -> {
                if(isValidInput(qte.getText())) {
                   qte.putClientProperty("LastValid", qte.getText());
                   if(qte.getText().length()>0){
                       
                       if(ServiceProduit.modifierLigne(ligne.getId(), produit.getId(), Integer.valueOf(qte.getText()))){
                          new PanierForm().getForm().show();
                       }else {
                           
                           Dialog dialog = new Dialog("stock insuffisant");
                           Button ok = new Button(new Command("OK"));
                           dialog.add(ok);
                           dialog.show();
                       }
                   }
                   
                   
                } else {
                   qte.stopEditing();
                   qte.setText((String)qte.getClientProperty("LastValid"));
                   qte.startEditingAsync();
                }
            });

          
            
            Label prix_tot = new Label(produit.getPrix()*ligne.getQuantite()+" TND");
            prix_tot.setAlignment(Component.RIGHT);
            prix_tot.getAllStyles().setFgColor(ColorUtil.BLUE);

            
            total_qte.add(totalLayout.createConstraint().widthPercentage(40), qte).
            add(totalLayout.createConstraint().widthPercentage(60),prix_tot);

            details.add(total_qte);
            form.add(item);

            
        }

        Button home = new Button("Continuer mes achats");
        home.setIcon(iconBack);
        
        home.addActionListener(new ActionListener() {            
              @Override
              public void actionPerformed(ActionEvent evt) {
                 new ProduitForm().getForm().show();
              }
          });
        form.add(container_total);
        form.add(home);
        
    }
    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    
    public boolean isValidInput(String input){

        if(input.contains("a") || input.contains("b") || input.contains("c") 
        || input.contains("d") || input.contains("e") || input.contains("f")
        || input.contains("g") || input.contains("h") || input.contains("i")
        || input.contains("j") || input.contains("k") || input.contains("l")
        || input.contains("m") || input.contains("n") || input.contains("o")
        || input.contains("p") || input.contains("q") || input.contains("r")
        || input.contains("s") || input.contains("t") || input.contains("u")
        || input.contains("v") || input.contains("w") || input.contains("x")
        || input.contains("y") || input.contains("z") || input.contains("&")
        || input.contains("é") || input.contains("'") || input.contains("(")
        || input.contains("-") || input.contains("_")) {
            return false;
        }
        else {
            return true;
        }
    }
    
}
