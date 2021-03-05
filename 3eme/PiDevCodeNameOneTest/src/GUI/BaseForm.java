package GUI;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Image selection = res.getImage("selection-in-sidemenu.png");
        Image statsImage = null;
        if (isCurrentStats()) {
            statsImage = selection;
        }

        getToolbar().addCommandToSideMenu("Accueil Evenement", statsImage, e -> new AccueilEvenementForm(res).show());
        getToolbar().addCommandToSideMenu("Forum ", statsImage, e -> new HomeForum().getF().show());
        getToolbar().addCommandToSideMenu("Promotion ", statsImage, e -> new promotion(res).getF().show());
        getToolbar().addCommandToSideMenu("Produit ", statsImage, e -> new ProduitForm().getForm().show());
        getToolbar().addCommandToSideMenu("Experience ", statsImage, e -> new Affichage(res).getF().show());

    }

    protected boolean isCurrentStats() {
        return false;
    }

    protected boolean isCurrentInbox() {
        return false;
    }

    protected Label createForFont(Font fnt, String s) {
        Label l = new Label(s);
        l.getUnselectedStyle().setFont(fnt);
        return l;
    }

}
