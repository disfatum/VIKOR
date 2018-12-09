package org.vikor.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import org.vikor.calculation.VikorCalculation;
import org.vikor.controller.Controller;
import org.vikor.view.settings.SettingsView;
import org.vikor.data.VikorTableData.VikorTableData;


public class CalculateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BarChart<String, Number> BarChartVikor;

    @FXML
    private TableView<VikorTableData> VikorTable;

    @FXML
    private Label CondidtionOneLabel;

    @FXML
    private Label CondidtionTwoLabel;

    ObservableList<Double> S = FXCollections.observableArrayList();
    ObservableList<Double> R = FXCollections.observableArrayList(); 
    ObservableList<Double> Q = FXCollections.observableArrayList();
    ObservableList<String> alt = FXCollections.observableArrayList();
    ObservableList<VikorTableData> Data = FXCollections.observableArrayList();
    
    public void AddData() throws Exception{
    	try {
    	int c = alt.size();
    		for(int i = 0; i < c; i++) {
    			VikorTableData t = new VikorTableData(alt.get(i),S.get(i),R.get(i),Q.get(i));
    			Data.add(t);
    		}
    	}catch( Exception e ){
    		Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(SettingsView.getPrimaryStage());
	        alert.setTitle("Ошибка");
	        alert.setHeaderText("Неправильное заполнение");
	        alert.setContentText("Введите число");
	        alert.showAndWait();
        }
    }
    @FXML
    void initialize() {
    	VikorCalculation t = Controller.t;
    	alt = Controller.altname;
    	Q = t.Q;
    	S = t.S;
    	R = t.R;
    	try {
			AddData();
		} catch (Exception e) {
		}
    	
    	TableColumn<VikorTableData,String> alte = new TableColumn<VikorTableData,String>("Название Альтернативы");
    	alte.setCellValueFactory(new PropertyValueFactory<VikorTableData, String>("AltName"));
    	TableColumn<VikorTableData,Double> s = new TableColumn<VikorTableData,Double>("S");
    	s.setCellValueFactory(new PropertyValueFactory<VikorTableData, Double>("S"));
    	TableColumn<VikorTableData,Double> r = new TableColumn<VikorTableData,Double>("R");
    	r.setCellValueFactory(new PropertyValueFactory<VikorTableData, Double>("R"));
    	TableColumn<VikorTableData,Double> q = new TableColumn<VikorTableData,Double>("Q");
    	q.setCellValueFactory(new PropertyValueFactory<VikorTableData, Double>("Q"));
    	
    	VikorTable.getColumns().add(alte);
    	VikorTable.getColumns().add(s);
    	VikorTable.getColumns().add(r);
    	VikorTable.getColumns().add(q);
    	VikorTable.setItems(Data);
    	
    	 CategoryAxis xAxis = new CategoryAxis();
         xAxis.setLabel("Название альтернативы");
   
         NumberAxis yAxis = new NumberAxis();
         yAxis.setLabel("значение коэф. Q");
         
    	int c = alt.size();
    	for(int i = 0; i < c; i++) {
    		XYChart.Series<String, Number> alt1 = new XYChart.Series<String, Number>();
    		alt1.getData().add(new XYChart.Data<String, Number>("", Q.get(i)+0.01));
    		alt1.setName(alt.get(i));
    		BarChartVikor.getData().add(alt1);
    	}
    	 
    	
    }
}
