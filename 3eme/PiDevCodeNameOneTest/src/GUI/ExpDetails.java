/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.Resources;
import Entities.Commentaire;
import Entities.Experience;
import Services.ServiceExperience;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author houba
 */
public class ExpDetails {

    TextModeLayout tl = new TextModeLayout(3, 2);
    Form f = new Form("Deatils", tl);

    ImageViewer iv;

    public ExpDetails(Experience exp, Resources theme) {

        ShareButton sb = new ShareButton();
        sb.setText("Partager");

       Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());

        
        Container commentContainer = new Container(BoxLayout.y());
        Style allStyle = commentContainer.getAllStyles();
        allStyle.setBorder(Border.createLineBorder(1));
        allStyle.setPadding(20, 20, 20, 20);

        Container detailsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        TextComponent comment = new TextComponent().label("Ecrire").multiline(true);

        Label nom = new Label("Nom : " + exp.getNom());
        Label lieu = new Label("Lieu : " + exp.getLieu());

        nom.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));
        lieu.getAllStyles().setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_LARGE));

       EncodedImage encodedImage = EncodedImage
                    .createFromImage(theme.getImage("loading.png"), true);

            String url = "http://localhost/worldfriendship/web/assets/img/experience/uploads/" + exp.getImage_exp();
            
            System.out.println(url);

            Image img = URLImage.createToStorage(encodedImage, url, url, URLImage.RESIZE_SCALE);
            ImageViewer iv = new ImageViewer(img);

            iv.setImage(img.scaled(800, 400));

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(actuelle);
        String dc = date;

        ArrayList<Commentaire> ListReponses = new ArrayList<>();
        ServiceExperience ser = new ServiceExperience();
        ListReponses = ser.getDetailQuestion(exp.getId());
        
        Label comm = new Label("Commentaires :");
      
        commentContainer.add(comm);
        
        for (Commentaire r : ListReponses) {
            Label description_rep = new Label("- " + r.getContenu());
            commentContainer.add(description_rep);
        }

        Button btn_cmnt = new Button("Envoyer");
        btn_cmnt.addActionListener((e) -> {
            Commentaire co = new Commentaire(exp.getId(), comment.getText(), dc);
            ser.ajoutComment(co);
            ExpDetails expDetails = new ExpDetails(exp, theme);
            expDetails.getF().show();
        });

        detailsContainer.add(nom).add(lieu);
        commentContainer.add(comment).add(btn_cmnt);

        f.add(iv).add(detailsContainer).add(commentContainer).add(sb);

        Image screenshot = Image.createImage(f.getWidth(), f.getHeight());
        f.revalidate();
        f.setVisible(true);
        f.paintComponent(screenshot.getGraphics(), true);

        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch (IOException err) {
            Log.e(err);
        }
        sb.setImageToShare(imageFile, "image/png");
    }

    public Form getF() {
        return f;
    }

}
