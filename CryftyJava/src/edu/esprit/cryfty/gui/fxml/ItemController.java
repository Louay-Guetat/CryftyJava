package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Nft;
import edu.esprit.cryfty.service.user.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static edu.esprit.cryfty.gui.Controller.nft;
import static edu.esprit.cryfty.gui.fxml.ExploreController.nft1;


public class ItemController implements Initializable{
    @FXML
    private Pane pnItem;
    @FXML
    private ImageView imNft;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Label lblOwner;
    @FXML
    private Label lblCategory;
    @FXML
    private Label lblSubCategory;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblCurrency;
    @FXML
    private Label lblLikes;
    public static Nft thisNft = nft;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javafx.scene.shape.Rectangle clip = new Rectangle(
                imNft.getFitWidth(), imNft.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imNft.setClip(clip);

        if (nft1 != null) {
            lblTitle.setText(nft1.getTitle());
            lblCategory.setText(nft1.getCategory().getName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' \n  HH:mm");
            lblCreationDate.setText(nft1.getCreationDate().format(formatter));
            lblLikes.setText(nft1.getLikes() + "");
            lblOwner.setText(nft1.getOwner().getUsername());
            lblPrice.setText(nft1.getPrice() + "");
            lblSubCategory.setText(nft1.getSubCategory().getName());
            lblCurrency.setText(nft1.getCurrency().getCoinCode());
            try {
                File watermarkImageFile  = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\" + nft1.getImage());
                File sourceImage = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\LogoNoText.png");
                Image image;
                if(nft1.getOwner().getId() != Session.getInstance().getCurrentUser().getId()){
                    File destinationImage = waterMark(sourceImage,watermarkImageFile);
                    image = new Image(new FileInputStream(destinationImage));
                    BoxBlur blur = new BoxBlur();
                    blur.setHeight(2);
                    blur.setWidth(2);
                    blur.setIterations(1);
                    imNft.setEffect(blur);
                }
                else{
                    image = new Image(new FileInputStream(watermarkImageFile));
                }

                imNft.setImage(image);

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            lblTitle.setText(nft.getTitle());
            lblCategory.setText(nft.getCategory().getName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' \n  HH:mm");
            lblCreationDate.setText(nft.getCreationDate().format(formatter));
            lblLikes.setText(nft.getLikes() + "");
            lblOwner.setText(nft.getOwner().getUsername());
            lblPrice.setText(nft.getPrice() + "");
            lblSubCategory.setText(nft.getSubCategory().getName());
            lblCurrency.setText(nft.getCurrency().getCoinCode());
            try {
                File watermarkImageFile  = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Nfts\\" + nft.getImage());
                File sourceImage = new File("C:\\Users\\LOUAY\\Desktop\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\LogoNoText.png");
                Image image;
                if(nft.getOwner().getId() != Session.getInstance().getCurrentUser().getId()){
                    File destinationImage = waterMark(sourceImage,watermarkImageFile);
                    image = new Image(new FileInputStream(destinationImage));
                    BoxBlur blur = new BoxBlur();
                    blur.setHeight(2);
                    blur.setWidth(2);
                    blur.setIterations(1);
                    imNft.setEffect(blur);
                }
                else{
                    image = new Image(new FileInputStream(watermarkImageFile));
                }

                imNft.setImage(image);

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File waterMark(File watermarkImageFile, File sourceImageFile) throws IOException {
        File destination = File.createTempFile("hhhh",".png");
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);

            // calculates the coordinate where the image is painted
            int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight()) / 2;

            // paints the image watermark
            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);

            ImageIO.write(sourceImage, "png", destination);
            g2d.dispose();

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return destination;
    }

}
