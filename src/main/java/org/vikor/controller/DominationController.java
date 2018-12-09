package org.vikor.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.vikor.data.ftabledata.FunctionData;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class DominationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<List<String>> TableView;

    @FXML
    private TableColumn<List<String>, String> AltNames;

    @FXML
    private TableColumn<List<String>, String> Domination;

    ObservableList<FunctionData> list = FXCollections.observableArrayList();
    ObservableList<List<String>> TableData = FXCollections.observableArrayList();
   
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
      System.out.println(ser.toString()+" ser");
      
      
      List<Double> dom1 = FXCollections.observableArrayList();
      List<String> domst = FXCollections.observableArrayList();
      for(int i = 0; i < ser.get(0).size();i++) {
    	  double buf = 0;
    	  
    	  for(int j = 0; j < ser.size(); j++) {
    		  buf = buf + ser.get(j).get(i);
    	  }
    	  dom1.add(buf);
      }
      System.out.println(dom1.toString()+" dom1");
      for(int i = 0; i < dom1.size();i++) {
    	  String buf = "";
    	 for(int j = 0; j < dom1.size(); j++) {
    		 if(dom1.get(i) < dom1.get(j)) {
    			 buf = buf+Controller.altname.get(j)+" ; ";
    		 }
    	 }
    	 if(!buf.equals("")) {
    		 String b = "Доминируется: "+buf;
    		 domst.add(b);
    	 }else {
    		 String s = "Не доминируется";
    		 domst.add(s);
    	 }
      }System.out.println(domst+"domst");
      
      for(int i = 0; i < domst.size();i++) {
    	   List<String> row =  FXCollections.observableArrayList();
    	  row.add(Controller.altname.get(i));
    	  row.add(domst.get(i));
    	  TableData.add(row);
      }
      
      System.out.println(TableData.toString()+ " row");
      
      for(int i = 0; i < Controller.altname.size();i++) {
    	  
      AltNames.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
    	     public ObservableValue<String> call(CellDataFeatures< List<String>, String> p) {
    	         return new ReadOnlyObjectWrapper<String>(p.getValue().get(0));
    	     }
    	  });
      Domination.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
 	     public ObservableValue<String> call(CellDataFeatures< List<String>, String> p) {
 	         return new ReadOnlyObjectWrapper<String>(p.getValue().get(1));
 	     }
 	  });
   
      }
      
      TableView.setItems(TableData);
    }
    
}
