package edu.esprit.cryfty.gui.fxml;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Source;
import com.stripe.model.Token;
import edu.esprit.cryfty.entity.payment.Cart;
import edu.esprit.cryfty.service.payment.CartService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PaiementStripeController implements Initializable {
    @javafx.fxml.FXML
    private TextField cvc;
    @javafx.fxml.FXML
    private Button valider;
    @javafx.fxml.FXML
    private TextField carte;
    @javafx.fxml.FXML
    private Button Annuler;
    @javafx.fxml.FXML
    private DatePicker dateExp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        valider.setOnAction((ActionEvent event) -> {
            try {

                if (controleDeSaisi()) {

                    if (carte.getText().isEmpty()) {
                        carte.setText("");
                    }
                    if (dateExp.getValue()==null) {

                    }

                    if (cvc.getText().isEmpty()) {
                        cvc.setText("");
                    }
                }

                Stripe.apiKey="sk_test_51IyEiPKXO6zoy45XnSBJfUeShcjGESS1F0uIZoZH3XQjKcJrBVsctduUrUqjabgjdHZVALOU1OFe4lefVdlriKJg00dp6rwSy2";
                Customer a = Customer.retrieve("cus_JbSjdC1UgpxQY8");

                Map<String, Object> cardParams = new HashMap<String, Object>();
                cardParams.put("number", Long.parseLong(carte.getText()));
                cardParams.put("exp_year", Integer.parseInt(dateExp.getValue().toString().substring(0,4)));
                cardParams.put("exp_month", Integer.parseInt(dateExp.getValue().toString().substring(5,7)));
                cardParams.put("cvc",  Integer.parseInt(cvc.getText()));

                Map<String, Object> tokenParams = new HashMap<String, Object>();
                tokenParams.put("card", cardParams);

                Token token = Token.create(tokenParams);

                Map<String, Object> source = new HashMap<String, Object>();
                source.put("source", token.getId());

                a.update(source);

                CartService cartService=new CartService();
                float tot = 0;
                for (int i = 0; i < cartService.getPricefromNftCart().size(); i++) {
                    tot = tot + cartService.getPricefromNftCart().get(i).getPrice();
                }

                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount",  (Integer.valueOf((int) tot)));
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", a.getId());

                Charge.create(chargeParams);
                System.out.print("Paiement reussie");

                showAlert(Alert.AlertType.CONFIRMATION, "Données Validés", "Success", "Paiement réussi avec succes!");

                  Cart cartAjout = cartService.getCartfromNft().get(0);
                cartService.supprimerCartfromNftCart(cartAjout.getId());
                PayStripe(event);

            } catch (StripeException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Annuler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
                s.close();
            }

        });
    }
    public void PayStripe(ActionEvent actionEvent) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    private boolean controleDeSaisi() {

        if (carte.getText().isEmpty() || dateExp.getValue()==null
                || cvc.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", carte.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez la reference ! ");
                carte.requestFocus();
                carte.selectEnd();
                return false;
            }
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String f= formatter.format(date);
            System.out.println(dateExp.getValue().getDayOfMonth());
            System.out.println(f.substring(8,10));
            if (dateExp.getValue().getYear()<Integer.parseInt(f.substring(0,4))  || dateExp.getValue().getMonthValue()<Integer.parseInt(f.substring(5,7))) {
                    showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez la date ! ");
                    return false;
            }
            if (!Pattern.matches("[0-9][0-9][0-9][0-9]", cvc.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "le cvc doit contenir 4 chiffres! ");
                cvc.requestFocus();
                cvc.selectEnd();
                return false;
            }
        }
        return true;

    }


    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }
}
