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
import com.codename1.components.SpanButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
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
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.DefaultLookAndFeel;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 *
 * @author yassine
 */
public class DetailProduitForm extends BaseForm{
    
    private Form form;
    private EncodedImage encodedImage;
    private ImageViewer imageViewer,imageViewer2;
    private Image img;
    private Produit produit;

    public DetailProduitForm(Produit produit) {
        
        this.produit = produit;
        
          
        Toolbar.setGlobalToolbar(true);
        
        form = new Form(produit.getLibelle(), new BoxLayout(BoxLayout.Y_AXIS));
        
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        form.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        
        
        String url="http://localhost/worldfriendship/web/assets/img/product/"+produit.getFirstimg();


        Font font = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);    

        
        try {
            encodedImage = (EncodedImage) EncodedImage.create("/load.png").fill(form.getWidth(), form.getWidth());
        } catch (IOException ex) { }
            
        Image img = URLImage.createToStorage(encodedImage,url, url, URLImage.RESIZE_SCALE);
        ImageViewer imageViewer = new ImageViewer(img);
        
        Label libelle = new Label(produit.getLibelle());
        libelle.getAllStyles().setFgColor(ColorUtil.rgb(0,0,0));
        libelle.getAllStyles().setFont(font);
        
        Label prix = new Label(produit.getPrix()+ "TND");
        prix.getAllStyles().setFgColor(ColorUtil.BLUE);
        prix.getAllStyles().setFont(font);
        
        SpanLabel description = new SpanLabel(produit.getDescription());
        
        Button btn = new Button("Ajouter au panier");
        btn.getUnselectedStyle().setBackgroundType(Style.BACKGROUND_GRADIENT_RADIAL);
        btn.getUnselectedStyle().setBackgroundGradientEndColor(0x3399ff);
        btn.getUnselectedStyle().setBackgroundGradientStartColor(0x3399ff);
        btn.getAllStyles().setFgColor(ColorUtil.rgb(255,255,255));
        
        btn.addActionListener(new ActionListener() {            
                @Override
                public void actionPerformed(ActionEvent evt) {
                    
                    System.out.print("ddd");
                  
                    ServiceProduit ps = new ServiceProduit();
                    if(ps.ajouterLigne(produit.getId())){
                        PanierForm panierForm = new PanierForm();
                        panierForm.getForm().show();
                    }else {
                        Dialog dialog = new Dialog("stock insuffisant");
                        Button ok = new Button(new Command("OK"));
                        dialog.add(ok);
                        dialog.show();
                    }
                    
                    
                }
            });


        form.add(imageViewer);
        form.add(libelle);
        form.add(prix);
        form.add(description);
        form.add(btn);



    } 

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
    
    
}
