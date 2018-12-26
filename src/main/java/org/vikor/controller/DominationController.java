package org.vikor.controller;

import java.net.URL;
import java.util.Collections;
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
    	ObservableList<List<Double>> data_buf = FXCollections.observableArrayList();
    	ObservableList<List<Double>> data = FXCollections.observableArrayList();
    	ObservableList<List<Double>> datas = FXCollections.observableArrayList();
    	for(int i = 0; i < Controller.data1.size();i++) {
    		List<Double> l = FXCollections.observableArrayList();
    		for(int j =0;j < Controller.data1.get(0).size();j++) {
    			l.add(Controller.data1.get(i).get(j));
    		}
    		data.add(l);
    	}
    	list = Controller.list;
    	double counterrank = 0;
    	for(int i = 0; i < data.size();i++) {
    		List<Double> l = FXCollections.observableArrayList();
    		for(int j = 0; j < data.get(i).size();j++) {
    			counterrank =0;
    			for(int i1 = 0; i1 < data.size();i1++) {
    	    		for(int j1 = 0; j1 < data.get(i1).size();j1++) {
    	    			if(list.get(i).getMaxmin().equals("MAX")) {
	    	    			if(data.get(i).get(j) > data.get(i1).get(j1) | 
	    	    					data.get(i).get(j) == data.get(i1).get(j1) ) {
	    	    				counterrank = counterrank + 1;
	    	    			}
    	    			}
	    	    		if(list.get(i).getMaxmin().equals("MIN")) {
	    	    			if(data.get(i).get(j) < data.get(i1).get(j1) |
	    	    					data.get(i).get(j) == data.get(i1).get(j1)) {
	    	    				counterrank = counterrank+1;
	    	    			}
	    	      		}
    	    		}
    	    		
    	    	}l.add(counterrank);
    			
    		}datas.add(l);
    	}
    	System.out.println(data.toString());
    	System.out.println(datas.toString() +" datas");
        
        
      List<Double> dom1 = FXCollections.observableArrayList();
      List<Double> dom2 = FXCollections.observableArrayList();
      List<String> domst = FXCollections.observableArrayList();
      for(int i = 0; i < datas.get(0).size();i++)  {
    	  List<Double> l = FXCollections.observableArrayList();
    	  for(int j = 0; j < datas.size();j++) {
    		  l.add(datas.get(j).get(i));
    	  }
    	data_buf.add(l);  
      }
      for(int i = 0; i < datas.size();i++) {
    	 dom1.add(Collections.max(data_buf.get(i)));
    	 dom2.add(Collections.min(data_buf.get(i)));
      }
      String s = "";
      System.out.println(dom1.toString() +" dom1");
      System.out.println(dom2.toString() +" dom2");
      for(int i = 0; i < dom1.size();i++) {
    	  s = "";
    	  for(int j = 0; j < dom1.size();j++) {
    		  if(dom1.get(i) < dom1.get(j) && dom2.get(i) < dom2.get(j)) {
    			  s = s+Controller.altname.get(j);
    			  s = s+";";
    		  }
    	  }
    	  if(s == "") {
    		  s = "Не доминируется";
    	  }
    	  else {
    		  s = "Доминируется:"+s;
    	  }
    	  domst.add(s);
      }
     
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
