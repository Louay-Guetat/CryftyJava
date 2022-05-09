package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.blogs.BlogArticles;
import edu.esprit.cryfty.service.blogs.BlogsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddArticleController implements Initializable {
    static Map<String, String[]> words = new HashMap<>();
    static int largestWordLength = 0;
  @FXML
  private TextField title;
    @FXML
    private TextArea contents;
    @FXML
    private DatePicker date;
    @FXML
    private TextField author;
    @FXML
    private Button btnadd;

    private String path;
    File selectedFile;
    @FXML
    public ComboBox<String> category;
    ObservableList<String> cat = FXCollections.observableArrayList("Memes","Art","Photography","Music","Crypto");
    @FXML
    private ImageView imArt;
    @FXML
    private Button artii;

    public void initialize(URL arg0, ResourceBundle arg1) {
        category.setItems(cat);
        imArt.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        imArt.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath(🙁"C:\Users\X\Desktop\ScreenShot.6.png"
                        imArt.setImage(new Image("file:" + file.getAbsolutePath()));
//                        screenshotView.setFitHeight(150);
//                        screenshotView.setFitWidth(250);
                        //   image.setText(path);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv ").openConnection().getInputStream()));
            String line = "";
            int counter = 0;
            while((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    if(content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] ignore_in_combination_with_words = new String[]{};
                    if(content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    if(word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
   /* @Deprecated
    public void addImage(ActionEvent actionEvent) {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a photo");
        fileChooser.setInitialDirectory(new File("C:\\Users\\med amine ben lazrak\\Desktop\\testimg"));
        fileChooser.getExtensionFilters().add(imageFilter);
        Image image = null;
        try{
            image = new Image(fileChooser.showOpenDialog(null).toURI().toString());
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        if(image != null){
            imArt.setImage(image);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No photo Selected");
            alert.setHeaderText("Please select a photo");
            alert.showAndWait();
        }
    }*/


    @FXML
    public void savearticle(ActionEvent actionEvent) {
        try {
            /// SAUVEGARDE DANS LA BASE


            String btitle = title.getText();
            String bcontents = contents.getText();
            DatePicker bdate = date;

            String bcategory = (String) category.getValue();
            String bauthor = author.getText();
            if (selectedFile == null) {
               // System.out.print("nsit tswera heyyy ");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("nist etswira rak ");
                alert.show();
            }

            if (selectedFile != null) {
                try {
                    System.out.println(selectedFile.toString());
                    File source = new File(selectedFile.toString());
                    File dest = new File("C:\\Users\\med amine ben lazrak\\Desktop\\javaaaa\\CryftyJava\\CryftyJava\\src\\edu\\esprit\\cryfty\\images\\Blogs");
                    FileUtils.copyFileToDirectory(source, dest);
                } catch (IOException ex) {
                    Logger.getLogger(AddArticleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }



            if ((btitle==null)||(bcontents==null)||(bcategory==null)||(bauthor==null)||(bdate==null)) {


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Some fields are empty ");
                alert.show();


        }
            if (selectedFile == null) {
                // System.out.print("nsit tswera heyyy ");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("nist etswira rak ");
                alert.show();
            }
            if(filterText(bcontents)==true){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("bad words ");
                alert.show();

            }
            else {


        BlogArticles r = new BlogArticles(btitle, bcontents, bcategory, bauthor, bdate,path);

          BlogsService sr = new BlogsService();
          sr.ajouterArticle(r);

          //// REDIRECTION

          FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeArticles.fxml"));
          Parent root2 = loader.load();


          btnadd.getScene().setRoot(root2);
      }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @FXML
    public void comboChange(ActionEvent actionEvent) {
    }
    public String getUniqid(String extension){
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+extension;
    }

    @FXML
    public void blist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeArticles.fxml"));
        Parent root2 = loader.load();


        artii.getScene().setRoot(root2);
    }



    public static ArrayList<String> badWordsFound(String input) {
        if(input == null) {
            return new ArrayList<>();
        }

        // remove leetspeak
        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");

        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // iterate over each letter in the word
        for(int start = 0; start < input.length(); start++) {
            // from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
                if(words.containsKey(wordToCheck)) {
                    // for example, if you want to say the word bass, that should be possible.
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for(int s = 0; s < ignoreCheck.length; s++ ) {
                        if(input.contains(ignoreCheck[s])) {
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }




        return badWords;

    }


    public static boolean filterText(String input) {
        ArrayList<String> badWords = badWordsFound(input);
        if(badWords.size() > 0) {
            return true;
        }
        return false;
    }


}
