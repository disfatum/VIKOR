package org.vikor.controller;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import org.vikor.calculation.VikorCalculation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class QvController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea TextArea;

    @FXML
    private LineChart<Number, Number> Chart;

    @FXML
    private Button ReButton;
    
    @FXML
    private Slider Slider;
    
    @FXML
    private NumberAxis xAxis;
    
    ObservableList<XYChart.Series<Number,Number>> chartData = FXCollections.observableArrayList();
    ObservableList<Double> Qv = FXCollections.observableArrayList();
    ObservableList<Double> s = FXCollections.observableArrayList();
    ObservableList<Double> r = FXCollections.observableArrayList();
    ObservableList<Double> Q = FXCollections.observableArrayList();
    ObservableList<String> alt = FXCollections.observableArrayList();
    
    @FXML
    void initialize() {
    	
    	ReButton.setDisable(true);
        TextArea.setEditable(false);
        VikorCalculation t = Controller.t;
 
        alt = Controller.altname;
        s = t.S;
        r = t.R;
        
        Double Rminus = Collections.max(r);
		Double Sminus = Collections.max(s);
		Double Rstar = Collections.min(r);
		Double Sstar = Collections.min(s);
        Qv = t.Qv;
        Q = t.Q;
        
        Slider.setMin(Collections.min(Qv));
        Slider.setMax(Collections.max(Qv));
        Slider.setShowTickLabels(true);
        Slider.setShowTickMarks(true);

        for(int i  = 0; i < s.size(); i++) {
        		
        	Series<Number, Number> series1 = new Series<>();
        	for(int j = 0; j < Qv.size();j++) {
        		Double q = (Qv.get(j) * (s.get(i) - Sstar) / (Sminus - Sstar))+((1 - Qv.get(j)) * (r.get(i) - Rstar)/(Rminus - Rstar));
        		series1.getData().add(new Data<Number, Number>(Qv.get(j),q));
        		
        	}
        	series1.setName(alt.get(i));
        	//Chart.getData().add(series1);
        	chartData.add(series1);
        }
        
        Series<Number, Number> series2 = new Series<>();
        series2.getData().add(new Data<Number,Number>(Controller.f.getV(),0));
        series2.getData().add(new Data<Number,Number>(Controller.f.getV(),1));
        series2.setName("V");
        chartData.add(series2);
        
        xAxis.setLowerBound(Qv.get(0));
        xAxis.setUpperBound(Qv.get(Qv.size()-1));
        //xAxis.setTickLabelsVisible(true);
        Chart.setData(chartData);
        
        Slider.setValue(Controller.f.getV());
        Slider.setShowTickLabels(true);
        Slider.setShowTickMarks(true);
        int c = 0;
		TextArea.appendText("при V = "+Controller.f.getV()+"\n");
		do {
			TextArea.appendText(String.valueOf(alt.get(c))+" = "+String.valueOf(Q.get(c))+"\n");
			
			c++;
		}while(c != Q.size());
		//xAxis.setTickLabelsVisible(true);
        Slider.valueProperty().addListener(new ChangeListener<Number>() {
        	
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                  Number oldValue, Number newValue) {
            	ReButton.setDisable(false);
            		Chart.getData().clear();
            		TextArea.clear();
            		chartData.clear();
            		ObservableList<Double> qq1 = FXCollections.observableArrayList();
            		ObservableList<String> a = FXCollections.observableArrayList();
            		a = t.altname();
            		qq1 = t.VikorV((double)newValue);
            		Series<Number, Number> series2 = new Series<>();
					series2.getData().add(0, new Data<Number,Number>(newValue,0));
					series2.getData().add(1, new Data<Number,Number>(newValue,1));
					series2.setName("V");
					for(int i  = 0; i < s.size(); i++) {
		        		
			        	Series<Number, Number> series1 = new Series<>();
			        	for(int j = 0; j < Qv.size();j++) {
			        		Double q = (Qv.get(j) * (s.get(i) - Sstar) / (Sminus - Sstar))+((1 - Qv.get(j)) * (r.get(i) - Rstar)/(Rminus - Rstar));
			        		series1.getData().add(new Data<Number, Number>(Qv.get(j),q));
			        		
			        	}
			        	series1.setName(alt.get(i));
			        	chartData.add(series1);
			        }
					xAxis.setLowerBound(Qv.get(0));
			        chartData.add(series2);
			        xAxis.setUpperBound(Qv.get(Qv.size()-1));
			        //xAxis.setTickLabelsVisible(true);
			        Chart.setData(chartData);
            		int c = 0;
            		TextArea.appendText("при V = "+newValue+"\n");
            		do {
            			TextArea.appendText(String.valueOf(a.get(c))+" = "+String.valueOf(qq1.get(c))+"\n");
            			
            			c++;
            		}while(c != qq1.size());
            }
         });
    ReButton.setOnAction(e->{
    	
    	chartData.clear();	
    	Chart.getData().clear();
    	TextArea.setText("");
    	Slider.setValue(Controller.f.getV());
    	ReButton.setDisable(true);
    	
    });
  }
}
    