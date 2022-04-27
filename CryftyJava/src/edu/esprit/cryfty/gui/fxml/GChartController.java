package edu.esprit.cryfty.gui.fxml;

import edu.esprit.cryfty.entity.Nft.Category;
import edu.esprit.cryfty.entity.Nft.SubCategory;
import edu.esprit.cryfty.service.Nft.CategoryService;
import edu.esprit.cryfty.service.Nft.SubCategoryService;
import edu.esprit.cryfty.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.PopupWindow;

import javax.tools.Tool;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GChartController implements Initializable {
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    ObservableList<PieChart.Data> dataCategory = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> dataSubCategory = FXCollections.observableArrayList();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox hBox;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
        cnx= DataSource.getInstance().getCnx();
        statCategories();
        statSubCategories();
        // TODO
    }


    public void statCategories()
    {
        PieChart pieChart = new PieChart();
        try {

            String query = "SELECT name, nbr_nft FROM category order by nbr_nft desc";

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();

            while (rs.next()){
                int numberOf = rs.getInt("nbr_nft");
                if(numberOf !=0){
                    dataCategory.add(new PieChart.Data(rs.getString("name")+" ("+numberOf+")",numberOf));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        pieChart.setTitle("**Number of NFTs by Category**");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setData(dataCategory);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(20);

        pieChart.getData().forEach(data -> {
            double total = 0;
            for (PieChart.Data d : pieChart.getData()) {
                total += d.getPieValue();
            }
            String pourcentage = String.format("%.1f%%", 100*data.getPieValue()/total) ;
            Tooltip tooltip = new Tooltip(pourcentage);
            Tooltip.install(data.getNode(),tooltip);
        });

        hBox.getChildren().add(pieChart);
    }

    public void statSubCategories()
    {
        PieChart pieChart = new PieChart();
        try {

            String query = "SELECT name, nbr_nft FROM sub_category order by nbr_nft desc";
            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();

            while (rs.next()){
                int numberOf = rs.getInt("nbr_nft");
                if(numberOf !=0){
                    dataSubCategory.add(new PieChart.Data(rs.getString("name")+" ("+numberOf+")",numberOf));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        pieChart.setTitle("**Number of NFTs by SubCategory**");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setData(dataSubCategory);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(20);

        pieChart.getData().forEach(data -> {
            double total = 0;
            for (PieChart.Data d : pieChart.getData()) {
                total += d.getPieValue();
            }
            String pourcentage = String.format("%.1f%%", 100*data.getPieValue()/total) ;
            Tooltip tooltip = new Tooltip(pourcentage);
            Tooltip.install(data.getNode(),tooltip);
        });

        hBox.getChildren().add(pieChart);
    }

    public void statSubCategoriesByCategories()
    {
        PieChart pieChart = new PieChart();
        try {
            String query = "SELECT name, nbr_nft FROM sub_category order by nbr_nft desc";
            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();

            while (rs.next()){
                int numberOf = rs.getInt("nbr_nft");
                if(numberOf !=0){
                    dataSubCategory.add(new PieChart.Data(rs.getString("name")+" ("+numberOf+")",numberOf));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        pieChart.setTitle("**Number of NFTs by SubCategory**");
        pieChart.setLegendSide(Side.BOTTOM);
        pieChart.setData(dataSubCategory);
        hBox.getChildren().add(pieChart);
    }
}
