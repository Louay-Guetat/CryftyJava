package edu.esprit.cryfty.gui.fxml;

import com.sun.prism.paint.Color;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.cryfty.entity.User;
import edu.esprit.cryfty.entity.chat.GroupeChat;
import edu.esprit.cryfty.gui.Controller;
import edu.esprit.cryfty.service.chat.GroupeChatService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.sun.prism.paint.Color.RED;

public class AffichParticipantsByGroupController implements Initializable {
    @javafx.fxml.FXML
    private Pane PaneP;
    GroupeChatService GrService= new GroupeChatService();
    @javafx.fxml.FXML
    private Button btnUpdate;
    public static GroupeChat UpdateClicked=null;
    public void afficheParticiants(GroupeChat c)
    {

        ArrayList<User> Participants=GrService.getParticipantsByGr(c.getId());
        Label lo=new Label("Nom Group");

        lo.setStyle("-fx-font-weight:bold;-fx-text-fill: purple");
        Label nomGroup =new Label(c.getNom());
        nomGroup.setStyle("-fx-font-weight:bold;-fx-text-fill: white");
        Label o =new Label("Owner :");
        o.setStyle("-fx-font-weight:bold;-fx-text-fill: purple");

        Label p =new Label("Participants :");
        p.setStyle("-fx-font-weight:bold;-fx-text-fill: purple");

        Label OwnerGroup =new Label(c.getOwner().getUsername());
        OwnerGroup.setStyle("-fx-font-weight:bold;-fx-text-fill: white");
        VBox layout = new  VBox(Participants.size());
        layout.getChildren().add(lo);
        layout.getChildren().add(nomGroup);
        layout.getChildren().add(o);
        layout.getChildren().add(OwnerGroup);
        layout.getChildren().add(p);
        for (int i=0;i<Participants.size();i++)
        {
            Label pr = new Label(Participants.get(i).getUsername());
           pr.setStyle("-fx-font-weight:bold;-fx-text-fill: white");
            layout.getChildren().add(pr);
                if(c.getOwner().getId()==4)
                {
                    FontAwesomeIconView deleteIconParticipants = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    HBox hbox=new HBox(deleteIconParticipants);
                    hbox.setStyle("-fx-alignement:right;");
                    deleteIconParticipants.setStyle("-fx-fill:red;");
                    hbox.setMargin(deleteIconParticipants,new Insets(0, 0, 0, 130));
                    layout.getChildren().add(hbox);
                    deleteParticipants(c,Participants.get(i),deleteIconParticipants );
                }
        }
        layout.setPadding(new Insets(5,0,5,30));
        PaneP.getChildren().add(layout);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficheParticiants(Controller.GrClicked);
        rediectToUpdate(Controller.GrClicked);

    }
    public void  deleteParticipants(GroupeChat g,User u,FontAwesomeIconView icon)
    {
        icon.setOnMouseClicked(event->{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you sure to delete "+ u.getUsername() +"from your group "+g.getNom()+"?");
            Optional<ButtonType> action =alert.showAndWait();
            if(action.get()== ButtonType.OK)
            {
                GrService.deleteParticiant(g,u);
                //afficheParticiants(Controller.GrClicked);
            }

        });
    }

    public void rediectToUpdate(GroupeChat c)
    {
        if(c.getOwner().getId()==4){
            btnUpdate.setVisible(true);}
            btnUpdate.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Stage primaryStage = new Stage();
                    Parent root = null;
                    UpdateClicked = c;

                    System.out.println("dkhal" +c);
                    try {
                        root = FXMLLoader.load(getClass().getResource("UpdateGroup.fxml"));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    primaryStage.setScene(new Scene( root));
                    primaryStage.show();
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.hide();
                }
            });

    }

    public Pane getPaneP() {
        return PaneP;
    }

    public void setPaneP(Pane paneP) {
        PaneP = paneP;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(Button btnUpdate) {
        this.btnUpdate = btnUpdate;
    }

    public static GroupeChat getUpdateClicked() {
        return UpdateClicked;
    }

    public static void setUpdateClicked(GroupeChat updateClicked) {
        UpdateClicked = updateClicked;
    }
}
