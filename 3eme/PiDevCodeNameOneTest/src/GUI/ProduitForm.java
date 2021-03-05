/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Produit;
import Services.ServiceProduit;
import com.codename1.charts.util.ColorUtil;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
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
public class ProduitForm {
    
    private EncodedImage encodedImage;
    private ArrayList<Produit> produits;
    private Form form;

    public ProduitForm( ){
        
        
        ServiceProduit.getPanier();
        
        produits = ServiceProduit.getProduits();
        form = new Form("Produit", new TableLayout(2, 2));

 Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        form.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        
        
        for (Produit produit : produits) {
          
            Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));

            try {
              encodedImage = (EncodedImage) EncodedImage.create("/load.png").fill(form.getWidth()/2, form.getWidth()/2);
            } catch (IOException ex) { }

            String url="http://localhost/worldfriendship/web/assets/img/product/"+produit.getFirstimg();

            
            Image img = URLImage.createToStorage(encodedImage,url, url, URLImage.RESIZE_SCALE);
            ImageViewer imageViewer = new ImageViewer(img);
            
            Font font = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);    

            Label libelle = new Label(produit.getLibelle());
            libelle.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
            libelle.getAllStyles().setFont(font);
            
            Label prix = new Label(produit.getPrix()+" TND");
            prix.getAllStyles().setFont(font);
            
            container.add(imageViewer);
            container.add(libelle);
            container.add(prix);
            
            Button b = new Button("button");
            
            b.addActionListener(new ActionListener() {            
                @Override
                public void actionPerformed(ActionEvent evt) {
                  DetailProduitForm detailsForm = new DetailProduitForm(produit);
                  detailsForm.getForm().show();
                }
            });
                    

            container.setLeadComponent(b);
            form.add(container); 
            
        }
       
        
         /*Container list = new InfiniteContainer() {
           @Override
           public Component[] fetchComponents(int index, int amount) {

               
                if (index == 0) {
                    produits = ServiceProduit.getProduits();
                    amount = produits.size();

                }
                if (index + amount > produits.size()) {
                    
                    amount = produits.size() - index;
                }
                if (amount <= 0) {
                    return null;
                }
                Component[] elements = new Component[amount];

                int i = 0;

               for (com.Esprit.Entite.Produit p : produits) {

                   //Creating custom container
                  Container element = new Container(BoxLayout.y());
                   Container line1 = new Container(BoxLayout.x());
                   Label nameLabel = new Label(p.getLibelle());
                   Label ageLabel = new Label(p.getFirstimg());
                   line1.add(nameLabel);
                   line1.add(ageLabel);
                   element.add(line1);

                   Label ps = new Label("We never show ID");
                   ps.getAllStyles().set3DText(true, true);
                   ps.getAllStyles().setFgColor(ColorUtil.rgb(255, 0, 0));

                   element.add(ps);

                   Button b = new Button("button");
                   b.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent evt) {
                           Dialog.show("Info", p.getLibelle()+ " has " + p.getFirstimg()+ " years", "ok", "");
                       }
                   });
                   element.setLeadComponent(b);
                   elements[i] = element;*/

                   //Using MultiButton
                   /* MultiButton mb = new MultiButton(p.getLibelle());
                   
                   String url="http://localhost/worldfriendship/web/assets/img/product/"+p.getFirstimg();

                   mb.setIcon(URLImage.createToStorage(encodedImage,url, url, URLImage.RESIZE_SCALE));
                   
                   mb.setTextLine2(p.getFirstimg());
                   mb.setTextLine3("Never show Id");
                   mb.setUIIDLine3("Active");
                   mb.setTextLine4("");
                   
                   mb.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent evt) {
                           Dialog.show("Info", p.getLibelle()+ " has " + p.getFirstimg()+ " years", "ok", "");
                       }
                   });
                   
                   elements[i]=mb;
                   i++;

               }
               return elements;
           }
       };
        list.setScrollableY(false);
*/
    }
    
    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
    
    
}
