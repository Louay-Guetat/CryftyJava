package edu.esprit.cryfty.gui.fxml;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.cryfty.entity.payment.Transaction;
import edu.esprit.cryfty.service.payment.TransactionService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.NORMAL;


public class AffichetransactionController implements Initializable {
    @javafx.fxml.FXML
        private TableColumn<Transaction, String> AmountId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> dateTransactionId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> RefId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> wAddressId;
    @javafx.fxml.FXML
    private TableView<Transaction> TableTransaction;
    @javafx.fxml.FXML
    private TextField searchId;
    @javafx.fxml.FXML
    private TableColumn<Transaction,String> pdfBtn;
    private String QRCode_PATH="C:\\Users\\Siam Info\\Desktop\\JavaFinal\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\QR";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getTransactionClient();
        search();
    }

    public void getTransactionClient()
    {
        TransactionService transactionService=new TransactionService();
        //System.out.println(transactionService.getTransactionsByClient(1));
        AmountId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                return new SimpleStringProperty(param.getValue().getCartId().getTotal().toString());
            }
        });

        dateTransactionId.setCellValueFactory(new PropertyValueFactory<>("datetransaction"));
        RefId.setCellValueFactory(new PropertyValueFactory<>("id"));
        wAddressId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transaction,String>, ObservableValue<String>>(){

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Transaction, String> param) {
                return new SimpleStringProperty(param.getValue().getWallets().getWalletAddress());
            }
        });

        Callback<TableColumn<Transaction,String>, TableCell<Transaction,String>> cellFoctory = (TableColumn<Transaction,String> param) -> {

            // make cell containing buttons
            final TableCell<Transaction,String> cell = new TableCell<Transaction,String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView PDFIcon = new FontAwesomeIconView(FontAwesomeIcon.DOWNLOAD);


                        PDFIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:20px;"
                                        + "-fx-fill:#0080FF;"
                        );

                        PDFIcon.setOnMouseClicked((MouseEvent event) -> {
                            Transaction transaction= TableTransaction.getSelectionModel().getSelectedItem();
                            if(pdfGen(transaction.getId(),transaction.getWallets().getWalletAddress(),transaction.getCartId().getTotal(),transaction.getDatetransaction(),transaction.getCartId().getClientId().getFirstName(),transaction.getCartId().getClientId().getLastName(),transaction.getCartId().getClientId().getAddress(),transaction.getCartId().getClientId().getPhoneNumber()))
                            {
                                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Success");
                                alert.setContentText("PDF downloaded successfully,Go to Downloads and check your invoice");
                                alert.showAndWait();
                            }
                            else
                            {
                                Alert alert2=new Alert(Alert.AlertType.ERROR);
                                alert2.setTitle("Error");
                                alert2.setContentText("Error where dowloading pdf ");
                                alert2.showAndWait();
                            }
                            getTransactionClient();
                            System.out.println("ok download");
                        });
                        HBox managebtn = new HBox( PDFIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(PDFIcon, new Insets(2, 2, 0, 3));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        pdfBtn.setCellFactory(cellFoctory);
        System.out.println(transactionService.getTransactionsByClient(1));
        TableTransaction.setItems(transactionService.getTransactionsByClient(1));
    }

    public void search()
    {

        TransactionService transactionService=new TransactionService();
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Transaction> filteredData = new FilteredList<>(transactionService.getTransactionsByClient(1), b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transaction -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();


                if (String.valueOf(transaction.getCartId().getTotal()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(TableTransaction.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        TableTransaction.setItems(sortedData);
    }
    public boolean pdfGen (int ref,String walletadress,Double total,Date date,String nom,String prenom,String address,int numTel){
        boolean test=false;
        try {
            Document document = new Document();
            OutputStream file = new FileOutputStream(new File("C:\\Users\\Siam Info\\Desktop\\JavaFinal\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\Downloads\\Test.pdf"));
            PdfWriter writer=PdfWriter.getInstance(document, file);
            /*Document document2 = new Document();
            OutputStream file2 = new FileOutputStream(new File("C:\\Users\\Siam Info\\Desktop\\JavaFinal\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\Downloads\\qr.pdf"));
            PdfWriter writer2=PdfWriter.getInstance(document2, file2);*/

            writer.setPageEvent( new PdfPageEventHelper() {
                                     public void onEndPage(PdfWriter writer, Document document) {
                                         int pageNumber = writer.getPageNumber();
                                         String text = "Page " + pageNumber;
                                         Rectangle page = document.getPageSize();
                                         PdfPTable structure = new PdfPTable(1);
                                         structure.addCell(new Paragraph(text));
                                         structure.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
                                         structure.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
                                     }
                                 });
                Image front = Image.getInstance("C:\\Users\\Siam Info\\Desktop\\JavaFinal\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\logo.png");
            document.open();
            front.scaleAbsolute(100,100);
            document.add(front);

            Font f=new Font(Font.FontFamily.UNDEFINED, 18, BOLD,BaseColor.BLACK);
            Phrase title=new Phrase("                                    FACTURE n° #00"+String.valueOf(ref)+"00 \n \n \n",f);
            document.add(title);

            Font fnomcl=new Font(Font.FontFamily.UNDEFINED, 11, BOLD,BaseColor.BLACK);

            document.add(new Phrase("Nom du client : ",fnomcl));
            document.add(new Phrase(nom+" "+prenom+"\n"));

            document.add(new Phrase("Adresse  : ",fnomcl));
            document.add(new Phrase(address+"\n"));

            document.add(new Phrase("Numero de téléphone : ",fnomcl));
            document.add(new Phrase(numTel+"\n \n \n"));


            PdfPTable tableC=new PdfPTable(4);
            PdfPCell table_cell2;
            table_cell2=new PdfPCell(new Phrase("Réference"));
            table_cell2.setBackgroundColor(new BaseColor(102,178,255));
            tableC.addCell(table_cell2);
            table_cell2=new PdfPCell(new Phrase("Wallet adresse utilisée"));
            table_cell2.setBackgroundColor(new BaseColor(102,178,255));
            tableC.addCell(table_cell2);
            table_cell2=new PdfPCell(new Phrase("Montant"));
            table_cell2.setBackgroundColor(new BaseColor(102,178,255));
            tableC.addCell(table_cell2);
            table_cell2=new PdfPCell(new Phrase("Date de transaction"));
            table_cell2.setBackgroundColor(new BaseColor(102,178,255));
            tableC.addCell(table_cell2);



            PdfPTable table=new PdfPTable(4);
            PdfPCell table_cell;
            table_cell=new PdfPCell(new Phrase(String.valueOf(ref)));
            table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase(walletadress));
            table.addCell(table_cell);
            table_cell=new PdfPCell(new Phrase(String.valueOf(total)));
            table.addCell(table_cell);

            SimpleDateFormat formatter2= new SimpleDateFormat("dd-MM-yyyy");
            String formats2= formatter2.format(date);
            table_cell=new PdfPCell(new Phrase(formats2));
            table.addCell(table_cell);
            document.add(tableC);
            document.add(table);

            Font ftc=new Font(Font.FontFamily.TIMES_ROMAN, 10, BOLD,BaseColor.RED);
            Phrase titleCondition=new Phrase("\n \n \n \n \n Conditions Générales : \n ",ftc);
            document.add(titleCondition);
            Font fc=new Font(Font.FontFamily.TIMES_ROMAN, 9, NORMAL,BaseColor.BLACK);
            Phrase Conditions=new Phrase( "\n" + "Conditions de paiement : paiement à réception de facture, 15 jours.\n" + "\n" + "Aucun escompte accordé pour paiement anticipé.\n" + "\n" + "Paiement par crypto \n \n",fc);
            document.add(Conditions);


            //document2.open();
            BarcodeQRCode my_code = new BarcodeQRCode("Réference :"+ref+"\n"+"Nom client:"+nom+" "+prenom+"\nMontant :"+total+"\nAdresse wallet :"+walletadress+"\nDate de Transaction :"+formats2, 150, 150, null);
            Image qr_image = my_code.getImage();
            qr_image.setAbsolutePosition(220,100);
            document.add(qr_image);
            /*Image qr_image2 = my_code.getImage();
            qr_image2.scaleAbsolute(500,500);
            document2.add(qr_image2);
            document2.close();*/


            //writer2.addDirectImageSimple(qr_image);





            Font fdate=new Font(Font.FontFamily.UNDEFINED, 11, BOLD,BaseColor.BLACK);
            SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
            java.sql.Date dates = new java.sql.Date(System.currentTimeMillis());
            String formats= formatter.format(dates);
            document.add(new Phrase("\n \n \n \n \n \n \n \n \n \n \n \n                                                                                                                                 Délivrée le : ",fdate));
            document.add(new Phrase(formats));
            document.getPageNumber();
            document.close();
            file.close();
            test=true;
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    return test;
    }

    // Function to create the QR code
    public  String createQR(int ref,String nom,String prenom,String walletAddress)
            throws Exception
    {

        String qrcode=QRCode_PATH+"QRCODE.png";
        QRCodeWriter writer=new QRCodeWriter();

        ByteMatrix matrix =  writer.encode("Réference :"+ref+"\n"+"Nom client:"+nom+" "+prenom+"\nAdresse wallet :"+walletAddress,BarcodeFormat.QR_CODE,350,350);



        BitMatrix output = new BitMatrix(matrix.get(matrix.width(), matrix.height()));
        for (int i = 0; i < matrix.width(); i++) {
            for (int j = 0; j < matrix.height(); j++) {
                // Zero is white in the bytematrix
                if (matrix.get(i, j) == 1) {
                    output.set(i, j);
                }
            }
        }

        Path path= FileSystems.getDefault().getPath(qrcode);
        MatrixToImageWriter.writeToPath(output, "PNG", path);
        return "QR CODE IS GENERATED SUCCESSFULLY";
    }

   }
