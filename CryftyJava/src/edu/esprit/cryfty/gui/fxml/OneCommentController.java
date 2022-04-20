//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.NftComment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class OneCommentController implements Initializable {
    @FXML
    private Label lblComment;
    @FXML
    private Label lblOwner;
    @FXML
    private Label lblReactions;

    public OneCommentController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        NftComment thisComment = OneItemController.comment;
        this.lblComment.setText(thisComment.getContent());
        this.lblOwner.setText(OneItemController.comment.getUser().getUsername() + " commented on " + OneItemController.comment.getPostDate());
    }
}
