/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.capture.Capture;
import static com.codename1.charts.util.ColorUtil.rgb;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextComponent;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import com.codename1.ui.util.Resources;
import Entities.Experience;
import Services.ServiceExperience;
import com.codename1.ui.FontImage;
import com.codename1.ui.plaf.UIManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author houba
 */
public class ExpAdd {

    TextModeLayout tl = new TextModeLayout(3, 2);
    Form f = new Form("Ajouter", tl);
    private static final String HTML_API_KEY = "AIzaSyAc2jo7htUNyjwCUqiRLQ_bpSve1l2kbjI";
    
    String photo;
    String path;

    public ExpAdd(Resources theme) {
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());

        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField ac = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                String[] l = searchLocations(text);
                if (l == null || l.length == 0) {
                    return false;
                }

                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }
        };

        ac.setHint("Lieu");
        ac.setMinimumElementsShownInPopup(5);

        TextComponent nom = new TextComponent().label("Nom");
        PickerComponent date = PickerComponent.createDate(new Date()).label("Date");
        TextComponent description = new TextComponent().label("Description").multiline(true);

        Button btn_img = new Button("Image");
        btn_img.addActionListener((i -> {
            String filePath = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            String fileName = extractFileName(filePath) + "jpg";

            photo = fileName;
            path = filePath;
            System.out.println(photo);
            System.out.println(path);

        }));

        btn_img.getUnselectedStyle().setAlignment(Component.CENTER);
        btn_img.getUnselectedStyle().setBorder(
                RoundBorder.create().color(rgb(52, 152, 219)).rectangle(true)
        );

        Button add = new Button("Ajouter");
        add.addActionListener((e -> {
            ServiceExperience serviceExperience = new ServiceExperience();
            Experience exp = new Experience(0, SessionManager.getId(), nom.getText(), ac.getText(), description.getText(), photo);
            serviceExperience.ajoutTask(exp,path);
            Dialog.setDefaultBlurBackgroundRadius(8);
            Dialog.show("Notification", "Publication Ajoutée", "OK", null);            

        }));

        add.getUnselectedStyle().setAlignment(Component.CENTER);
        add.getUnselectedStyle().setBorder(
                RoundBorder.create().color(rgb(52, 152, 219)).rectangle(true)
        );

        //VALIDATOR
        Validator val = new Validator();
        val.addConstraint(nom, new LengthConstraint(2));
        val.addConstraint(ac, new LengthConstraint(2));
        val.addConstraint(date, new LengthConstraint(2));
        val.addConstraint(description, new LengthConstraint(2));
        //END VALIDATOR

        //STYLE
        Style fStyle = f.getAllStyles();
        fStyle.setBgColor(rgb(236, 240, 241));

        Style nomStyle = nom.getAllStyles();
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        nomStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        nomStyle.setBgColor(0xffffff);
        nomStyle.setBgTransparency(255);
        nomStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        nomStyle.setMargin(Component.BOTTOM, 3);

        Style acStyle = ac.getAllStyles();
        acStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        acStyle.setBgColor(0xffffff);
        acStyle.setBgTransparency(255);
        acStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        acStyle.setMargin(Component.BOTTOM, 3);

        Style descriptionStyle = description.getAllStyles();
        descriptionStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        descriptionStyle.setBgColor(0xffffff);
        descriptionStyle.setBgTransparency(255);
        descriptionStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        descriptionStyle.setMargin(Component.BOTTOM, 3);

        Style dateStyle = date.getAllStyles();
        dateStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        dateStyle.setBgColor(0xffffff);
        dateStyle.setBgTransparency(255);
        dateStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
        dateStyle.setMargin(Component.BOTTOM, 3);
        //END STYLE

        f.add(tl.createConstraint().horizontalSpan(2), nom);
        f.add(tl.createConstraint().horizontalSpan(2), ac);
        f.add(tl.createConstraint().horizontalSpan(2), description);
        f.add(tl.createConstraint().horizontalSpan(2), date);
        
        date.setVisible(false);

        f.add(add);
        f.add(btn_img);
    }

    public static String extractFileName(String filePathName) {
        if (filePathName == null) {
            return null;
        }

        int dotPos = filePathName.lastIndexOf('.');
        int slashPos = filePathName.lastIndexOf('\\');
        if (slashPos == -1) {
            slashPos = filePathName.lastIndexOf('/');
        }

        if (dotPos > slashPos) {
            return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0,
                    dotPos);
        }

        return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
    }

    private Map<String, Object> createListEntry(String name) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);

        return entry;

    }

    String[] searchLocations(String text) {
        try {
            if (text.length() > 0) {
                ConnectionRequest r = new ConnectionRequest();

                r.setPost(false);
                r.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json");
                r.addArgument("key", "AIzaSyAc2jo7htUNyjwCUqiRLQ_bpSve1l2kbjI");
                r.addArgument("input", text);

                NetworkManager.getInstance().addToQueueAndWait(r);
                Map<String, Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
                String[] res = Result.fromContent(result).getAsStringArray("//description");

                System.out.println(result);

                return res;

            }
        } catch (IOException | IllegalArgumentException err) {
            Log.e(err);
        }
        return null;
    }

    public Form getF() {
        return f;
    }

}