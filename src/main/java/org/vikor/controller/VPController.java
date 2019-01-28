package org.vikor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.vikor.data.ftabledata.FunctionData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class VPController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Number, Number> LineChart;

    ObservableList<XYChart.Series<Number,Number>> chartData = FXCollections.observableArrayList();
   
    List<Double> chart = FXCollections.observableArrayList();
    
    ObservableList<FunctionData> list = FXCollections.observableArrayList();
    @FXML
    void initialize() {
    	ObservableList<ObservableList<Double>> data_buf = FXCollections.observableArrayList();
    	ObservableList<ObservableList<Double>> data_min = FXCollections.observableArrayList();
    	ObservableList<List<Double>> data = FXCollections.observableArrayList();
    	ObservableList<List<Double>> ser = FXCollections.observableArrayList();
    	for(int j = 0; j < Controller.data1.get(0).size();j++) {
    		List<Double> l = FXCollections.observableArrayList();
    		for(int i =0; i < Controller.data1.size();i++) {
    			l.add(Controller.data1.get(i).get(j));
    		}
    		data.add(l);
    	}
        for(int i = 0; i < data.size(); i++) {
        	ObservableList<Double> f = FXCollections.observableArrayList();
        	for(int j = 0; j < data.get(i).size();j++) {
        		f.add(data.get(i).get(j));
        	}
        	data_buf.add(f.sorted());
        }
        for(int i = 0; i < data_buf.size(); i++) {
        	ObservableList<Double> f = FXCollections.observableArrayList();
        	for(int j = data_buf.get(i).size()-1; j >= 0;j--) {
        		f.add(data_buf.get(i).get(j));
        	}
        	data_min.add(f);
        }
        list = Controller.list;
        
       for(int i = 0; i < data.size();i++) {
    	   ObservableList<Double> c = FXCollections.observableArrayList();
    	   for(int j =0; j < data.get(i).size();j++) {
    		   
    		   if(list.get(i).getMaxmin().equals("MAX")) {
    			   c.add((double)data_buf.get(i).indexOf(data.get(i).get(j)));
    		   }
    		   if(list.get(i).getMaxmin().equals("MIN")) {
    			   c.add((double)data_min.get(i).indexOf(data.get(i).get(j)));
    		   }
    	   }
    	   ser.add(c);
       }
       double c = 0;
       for(int j = 0; j < ser.get(0).size();j++) {
    	   Series<Number, Number> series1= new Series<>();
        for(int i =0; i < ser.size(); i++) {
        		c = i+0.5;
        		double q = ser.get(i).get(j)+0.1;
        		final Data<Number, Number> d3 = new XYChart.Data<>(c,q);
        		d3.setNode(new HoveredThresholdNodea(""+Controller.list.get(i).getFullName(),"Альтернатива:"+ Controller.altname.get(j)));
        		series1.getData().add(d3);
        	}
        series1.setName(Controller.altname.get(j));
        LineChart.getData().add(series1);
        //chartData.add(series1);
		
        } 
        xAxis.setUpperBound(c+0.5);
        xAxis.setLowerBound(0);
        yAxis.setUpperBound(Controller.altname.size());
        yAxis.setLowerBound(0);
        //LineChart.setData(chartData); 
    }
    class HoveredThresholdNodea extends StackPane {

        public HoveredThresholdNodea(String string, Object object) {
            setPrefSize(15, 15);

            final Label label = createDataThresholdLabel(string, object);

            setOnMouseEntered(new EventHandler<Event>() {
                
				@Override
				public void handle(Event event) {
					getChildren().setAll(label);
                    setCursor(Cursor.NONE);
                    toFront();
				}
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                }
            });
        }

        private Label createDataThresholdLabel(String string, Object object) {
            final Label label = new Label(string+"");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

                label.setTextFill(Color.RED);
                label.setStyle("-fx-border-color: RED;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
}
