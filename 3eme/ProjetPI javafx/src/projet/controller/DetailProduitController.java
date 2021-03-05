/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import projet.models.Produit;
import projet.service.PanierService;

/**
 * FXML Controller class
 *
 * @author yassine
 */
public class DetailProduitController implements Initializable {

    @FXML
    private TextField quantite;
    @FXML
    private Label errorsQte;
    @FXML
    private Label libelle;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Text description;
    @FXML
    private Label prix;
    @FXML
    private Label created;
    @FXML
    private Rectangle color;
    @FXML
    private ImageView present_img;
    @FXML
    private Label taille;
    @FXML
    private ImageView imgView_1;
    @FXML
    private ImageView imgView_2;
    @FXML
    private ImageView imgView_3;
    @FXML
    private Text description_more;

    private Produit produit;

    private Image image_1, image_2, image_3;
    private boolean visible = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Produit p) throws FileNotFoundException {
        this.produit = p;
        libelle.setText(p.getLibelle());
        description.setText(p.getDescription());
        description_more.setText(p.getDescription());
        prix.setText(String.format("%.2f", p.getPrix()) + " TND");
        created.setText("Disponible le " + String.valueOf(p.getCreated()));
        taille.setText(p.getTaille());
        Color col = Color.web(p.getColor());
        color.setFill(col);
        image_1 = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\" + produit.getFirstimg()));
        present_img.setImage(image_1);
        imgView_1.setImage(image_1);
          System.out.println("testttttttt");
        System.out.println(produit.getSecondimg());
        image_2 = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\" + produit.getSecondimg()));
         
        imgView_2.setImage(image_2);
        image_3 = new Image(new FileInputStream("C:\\wamp64\\www\\worldfriendship\\web\\assets\\img\\product\\" + produit.getThirdimg()));
        imgView_3.setImage(image_3);
    }

    @FXML
    private void firstimg(MouseEvent event) {
        present_img.setImage(image_1);
    }

    @FXML
    private void secondimg(MouseEvent event) {
        present_img.setImage(image_2);
    }

    @FXML
    private void thirdimg(MouseEvent event) {
        present_img.setImage(image_3);
    }

    @FXML
    private void ajouterPanier(MouseEvent event) throws IOException {
        if (Integer.valueOf(quantite.getText()) < 1) {
            errorsQte.setText("min 1");
        } else {

            PanierService ps = new PanierService();
            int qte = Integer.valueOf(quantite.getText());

            if (ps.ajouterLignePanier(produit.getId(), qte)) {

                BorderPane borderpane = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
                Parent sidebar = FXMLLoader.load(getClass().getResource("/projet/presentation/SideBarPanier.fxml"));
                borderpane.setRight(sidebar);
                scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }

        }

    }

    @FXML
    private void QteEvent(KeyEvent event) {

        String ch = event.getCharacter();
        char c = ch.charAt(0);

        if (!Character.isDigit(c)) {
            event.consume();
        } else {
            errorsQte.setText("");
        }
    }

}
