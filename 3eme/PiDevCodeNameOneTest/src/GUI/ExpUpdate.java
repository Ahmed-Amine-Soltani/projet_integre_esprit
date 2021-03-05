/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.capture.Capture;
import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.io.MultipartRequest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import com.codename1.ui.util.Resources;
import Entities.Experience;
import Services.ServiceExperience;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author houba
 */
public class ExpUpdate {

    TextModeLayout tl = new TextModeLayout(3, 2);

    Form f = new Form("Ajouter", tl);

    public ExpUpdate(Resources theme, Experience experience) {
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());

        String id = String.valueOf(experience.getId());
        Label idd = new Label(id);
        int iddd = Integer.parseInt(idd.getText());

        TextComponent nom = new TextComponent().label("Nom");
        TextComponent lieu = new TextComponent().label("Lieu");
        TextComponent description = new TextComponent().label("Description").multiline(true);

        nom.text(experience.getNom());
        lieu.text(experience.getLieu());
        description.text(experience.getDesc());

        PickerComponent date = PickerComponent.createDate(new Date()).label("Date");

        Button btn_img = new Button("Image");
        btn_img.addActionListener((i -> {
            MultipartRequest cr = new MultipartRequest();
            String filePath = Capture.capturePhoto();
            String mime = "image/jpeg";
            try {
                cr.addData("file", filePath, mime);
            } catch (IOException ex) {
                System.out.println("Error Image");
            }
            System.out.println(cr);

        }));

        Button add = new Button("Modifier");
        add.addActionListener((e -> {
            ServiceExperience serviceExperience = new ServiceExperience();
            Experience exp = new Experience(iddd, nom.getText(), lieu.getText(),
                    date.toString(), description.getText(), btn_img.getText());
            serviceExperience.updateExperience(exp);
            System.out.println(exp);
            Dialog.setDefaultBlurBackgroundRadius(8);
            Dialog.show("Notification", "Publication Modifiée", "OK", null);
        }));

        add.getUnselectedStyle().setAlignment(Component.CENTER);
        add.getUnselectedStyle().setBorder(
                RoundBorder.create().color(rgb(52, 152, 219)).rectangle(true)
        );

        Validator val = new Validator();
        val.addConstraint(nom, new LengthConstraint(1));
        val.addConstraint(lieu, new LengthConstraint(1));
        val.addConstraint(description, new LengthConstraint(1));

        f.add(tl.createConstraint().horizontalSpan(1), nom);
        f.add(tl.createConstraint().horizontalSpan(1), lieu);
        f.add(tl.createConstraint().horizontalSpan(1), description);

        f.add(add);

    }

    public Form getF() {
        return f;
    }

}
