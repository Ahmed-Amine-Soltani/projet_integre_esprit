/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import Entities.comment;
import Services.ServiceForum;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Moez
 */
public class ajouterCom {

    Form f;
    TextField tdesc;
    Button btnajout;
    Container ctnr;
    ArrayList<comment> ListReponses = new ArrayList<>();

    public ajouterCom(int id_q) {
        ctnr = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //Style S1 = ctnr.getAllStyles();
        //S1.setBorder(Border.createLineBorder(1));

        f = new Form("Ajout de commentaire");

        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage iconBack = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, s, 4);

        Form previous = Display.getInstance().getCurrent();
        f.getToolbar().addCommandToLeftBar(null,iconBack, e -> previous.showBack());
        Container element = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        tdesc = new TextField("", "Contenu");
        btnajout = new Button("commenter");
        element.add(tdesc);
        element.add(btnajout);
        f.add(element);

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(actuelle);
        String dc = date;

        btnajout.addActionListener((e) -> {
            ServiceForum ser = new ServiceForum();

            comment co = new comment(id_q,SessionManager.getId(), tdesc.getText(), dc);

            ser.ajoutComment(co);
            ajouterCom aj = new ajouterCom(id_q);
            aj.getF().show();
          try {  // Construct data
          
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);
            r.setUrl("https://api.txtlocal.com/send/?");
            r.addArgument("apikey", "MfkkwMIH6Qg-9kf49NcnwcAS7P3qCbbjNni79lZXWs");
            r.addArgument("message", "Un Commentaire a ete ajoute");
            r.addArgument("sender", "Mnd");
            r.addArgument("numbers", "21623911535");
            
            NetworkManager.getInstance().addToQueueAndWait(r);
              Map<String, Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
              Map<String, Object> response = (Map<String, Object>) result.get("response");
              System.out.println(response.toString());
        } catch(Exception err) {
              System.out.println(err);
    }});

        ServiceForum ser = new ServiceForum();
        ListReponses = ser.getDetailQuestion(id_q);

        for (comment r : ListReponses) {

            Label description_rep = new Label("Contenu :" + r.getContenu());

            ctnr.add(description_rep);

        }
        f.add(ctnr);

        f.show();
        Validator val = new Validator();
        val.addConstraint(tdesc, new LengthConstraint(2));

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }

    public TextField getTdesc() {
        return tdesc;
    }

    public void setTdesc(TextField tdesc) {
        this.tdesc = tdesc;
    }

}
