package edu.esprit.cryfty.gui.fxml.node;

import edu.esprit.cryfty.entity.Wallet;
import edu.esprit.cryfty.service.NodeService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class StatsController {



    @FXML
    private NumberAxis numberAxis;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private BarChart<String,Number> barChart;
    private Stage dialogStage;
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        NodeService nodeService = new NodeService();
        Map<String, Integer> listMap = nodeService.getStats();
        for (Map.Entry<String, Integer> entry : listMap.entrySet()) {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName(entry.getKey());
            series.getData().add(new XYChart.Data<>("Total",entry.getValue()));
            System.out.println("valu = "+entry.getValue()+"ky = "+entry.getKey());
            barChart.getData().add(series);
        }

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
