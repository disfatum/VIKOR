package org.vikor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.vikor.data.ftabledata.FunctionData;
import org.vikor.data.ftabledata.MaxMin;
import org.vikor.data.ptabledata.TableData;
import org.vikor.view.calculate.Calculate;
import org.vikor.view.domination.Domination;
import org.vikor.view.mainwindow.Main;
import org.vikor.view.qvView.QvView;
import org.vikor.view.settings.SettingsView;
import org.vikor.view.srView.SRview;
import org.vikor.view.valuepath.ValuePath;
import org.vikor.data.SettingsData.SettingsData;
import org.vikor.calculation.VikorCalculation;;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<FunctionData, MaxMin> MaxMinColumn;

    @FXML
    private Button OkDeleteButton;

    @FXML
    private Button SrwButton;

    @FXML
    private ComboBox<String> ComboBoxDelete;

    @FXML
    private Button VpButton;

    @FXML
    private Button QvButton;

    @FXML
    private ComboBox<String> ComboBoxAdd;

    @FXML
    private TextField AddNameButton;

    @FXML
    private Button AddOkButton;

    @FXML
    private TableView<FunctionData> FunctionTable;

    @FXML
    private Button DominateButton;

    @FXML
    private Button SettingsButton;

    @FXML
    private TableView<TableData> PerfomanceTable;

    @FXML
    private TableColumn<FunctionData, String> FnameColumn;

    @FXML
    private MenuItem OpenManualItem;

    @FXML
    private TableColumn<FunctionData, String> WeighColumn;

    @FXML
    private Button CalculateButton;

    @FXML
    private MenuItem ExitItem;
    
    static public ObservableList<FunctionData> list = FXCollections.observableArrayList();
    ObservableList<String> TableView = FXCollections.observableArrayList();
    
    public ObservableList<FunctionData> FunctionList() {
    	 
    	FunctionData Dt = new FunctionData(AddNameButton.getText(), "0.5", MaxMin.MAXIMUM.getCode());
        list.add(Dt);
        return list;
    }
    
    int Altcounter = 0;
    int Fcounter = 0;
    String[] S;
    
    public ObservableList<TableColumn<TableData,String>> pData = FXCollections.observableArrayList();
    public ObservableList<TableData> Data = FXCollections.observableArrayList();
    TableData td;
    
    public void addCol(int g) {
    	if(Fcounter == 0) {
	    	TableColumn<TableData,String> Col0 = new TableColumn<TableData,String>("Альтернативы/Критерии");
	    	pData.add(Col0);
	    	PerfomanceTable.getColumns().add(Col0);
    	}
    	
    	TableColumn<TableData,String> Col1 = new TableColumn<TableData,String>(s2.get(0));
    	pData.add(Col1);
    	PerfomanceTable.getColumns().add(pData.get(pData.size()-1));
    	
    	if (rowcounter>0) {
    		for(int i =0; i < Data.size();i++) {
    			//System.out.println(Data.get(i).getList().toString()+" before");
    			Data.get(i).addtoLast();
    			//System.out.println(Data.get(i).getList().toString()+" after");
    		}
    		pData.get(pData.size()-1).setCellFactory(TextFieldTableCell.<TableData>forTableColumn());
    		pData.get(pData.size()-1).setCellValueFactory(new Callback<CellDataFeatures<TableData, String>, ObservableValue<String>>() {
    		     public ObservableValue<String> call(CellDataFeatures<TableData, String> p) {
					
    		    	 return new ReadOnlyObjectWrapper<String>(p.getValue().GetFromList(pData.size()-1));
    		     }
    		  });
    		
    		pData.get(pData.size()-1).setOnEditCommit((CellEditEvent<TableData, String> event2) -> {
            
    	       		 TablePosition<TableData, String> pos = event2.getTablePosition();
    	                String newFullName = event2.getNewValue();
    	                int row0 = pos.getRow();
    	                int col0  = pos.getColumn();
    	                Data.get(row0).setinlist(col0, newFullName);
    	                
    	            });
    	}
    	PerfomanceTable.setItems(null);
    	PerfomanceTable.setItems(Data);
    }
    
    int rowcounter = 0;
    public static SettingsData f = new SettingsData(0.5,10,10,false,0.01,0.01);
    
    public void addRow() {
    	ObservableList<String> row =  FXCollections.observableArrayList();
    	for(int i =0; i< Fcounter+1;i++ ) {
    		row.add("-/-");
    		row.set(0, AddNameButton.getText());
    	}
    	td = new TableData(row);
    	for(int i = 0; i< pData.size(); i++) {
    		//System.out.println( pData.size()+"pdatasize");
    		final int j = i;
    		pData.get(i).setCellFactory(TextFieldTableCell.<TableData>forTableColumn());
    		pData.get(i).setCellValueFactory(new Callback<CellDataFeatures<TableData, String>, ObservableValue<String>>() {
    		     public ObservableValue<String> call(CellDataFeatures<TableData, String> p) {
					
    		    	 return new ReadOnlyObjectWrapper<String>(p.getValue().GetFromList(j));
    		     }
    		  });
    		
    		pData.get(i).setOnEditCommit((CellEditEvent<TableData, String> event2) -> {
                
       		 TablePosition<TableData, String> pos = event2.getTablePosition();
                String newFullName = event2.getNewValue();
                int row0 = pos.getRow();
                int col0  = pos.getColumn();
                Data.get(row0).setinlist(col0, newFullName);
                
            });
    	}
    	 PerfomanceTable.setItems(null);
    	 Data.add(td);
    	 for(int i =0; i < Data.size();i++) {
    		 System.out.println(Data.get(i).getList().toString()+" dt");
    	 }
    	 PerfomanceTable.setItems(Data);  	
    	 rowcounter++;
    }
    List<String> s2 = FXCollections.observableArrayList();
    static public ObservableList<String> altname ;
    
    public ObservableList<List<Double>> Translation() throws Exception{
    	altname = FXCollections.observableArrayList();
    	ObservableList<List<Double>> data = FXCollections.observableArrayList();
    	try {
    	
    		for(int i = 0; i < Data.size();i++) {
    			List<Double> row = FXCollections.observableArrayList();	
    			altname.add(Data.get(i).getList().get(0));
    			for(int j = 1; j < Data.get(i).getList().size();j++) {
    				row.add(Double.valueOf(Data.get(i).getList().get(j)));
    			}
    			data.add(row);
    		}
    		//System.out.println(data.sorted().toString()+" st");
    	}catch( Exception e ){
    		Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(SettingsView.getPrimaryStage());
	        alert.setTitle("Ошибка");
	        alert.setHeaderText("Неправильное заполнение");
	        alert.setContentText("Введите число");
	        alert.showAndWait();
        }
    	data1 = data;
    	return data;
    }
    public void ftr() throws Exception{
     try {
    	 for(int i = 0 ; i < Fcounter ; i++) {
    		 Double.valueOf(list.get(i).getWeigh());
    	 }
		 
		 } catch (Exception e1) {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Введите вес критерия");
		        alert.showAndWait();
		}
     //return c;
    }
    
@FXML
void initialize() {
		
    	ObservableList<String> langs = FXCollections.observableArrayList("Альтернативу", "Критерий");
		ComboBoxAdd.setItems(langs);
		ComboBoxAdd.setValue("Критерий");
		PerfomanceTable.setEditable(true);
		ObservableList<String> ListforDelete = FXCollections.observableArrayList();
		ObservableList<String> alt = FXCollections.observableArrayList();
		ObservableList<String> func = FXCollections.observableArrayList();
	
	ExitItem.setOnAction(e ->{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(Main.getPrimaryStage());
        alert.setTitle("Выход из программы");
        alert.setHeaderText("Вы уверены?");
		Optional<ButtonType> option = alert.showAndWait();
		 
	      if (option.get() == null) {
	      } else if (option.get() == ButtonType.OK) {
	         System.exit(0);
	      } 
	});
	AddOkButton.setOnAction(event-> {
		
		if (!AddNameButton.getText().equals("")) {
			ListforDelete.add(AddNameButton.getText());
			ComboBoxDelete.setItems(ListforDelete);
			ComboBoxDelete.setValue(AddNameButton.getText());
			
			for(int i = 0; i< pData.size(); i++) {
				
	    	pData.get(i).setOnEditCommit((CellEditEvent<TableData, String> event2) -> {
	                
	       		 TablePosition<TableData, String> pos = event2.getTablePosition();
	                String newFullName = event2.getNewValue();
	                int row0 = pos.getRow();
	                int col0  = pos.getColumn();
	                Data.get(row0).setinlist(col0, newFullName);
	                
	            });
	    	}
			if(ComboBoxAdd.getValue().equals("Альтернативу")) {
				alt.add(AddNameButton.getText());
				addRow();
				Altcounter++;
			}
			if(ComboBoxAdd.getValue().equals("Критерий")) {
				func.add(AddNameButton.getText());
			    s2.add(AddNameButton.getText());
			    addCol(Fcounter);
				AddDataFtable();
				//addCol();
				Fcounter++;
				s2.remove(0);			
			}
		}
		else {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(Main.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Пустое поле");
		        alert.setContentText("Укажите название критерия/альтернативы");
		        alert.showAndWait();	
		}
	});
		
	
	OkDeleteButton.setOnAction(event ->{
		 System.out.println(ComboBoxDelete.getValue());
		if(ComboBoxDelete.getValue() != null) {
			PerfomanceTable.setItems(null);
			int buf = -1;
			int bufalt = 0;
			
			for(int i =0; i < func.size();i++) {
				if (func.get(i).equals(ComboBoxDelete.getValue())) {
					buf = i;
					for(int j = 0; j < Data.size();j++) {
						Data.get(j).getList().remove(buf+1);
						//PerfomanceTable.getItems().get(j).remove(buf+1);
					}
					func.remove(i);
					list.remove(i);
				}
			}
			for(int i = 0; i < alt.size(); i++) {
				if(alt.get(i).equals(ComboBoxDelete.getValue())) {
					bufalt = i;
					Data.remove(bufalt);
					alt.remove(i);
				}
			}
			
			ListforDelete.remove(ListforDelete.indexOf(ComboBoxDelete.getValue()));
			
			if(buf != -1) {
			PerfomanceTable.getColumns().remove(buf+1);
			pData.remove(buf+1);
			for(int i = 0; i<pData.size();i++ ) {
				final int j = i;
				pData.get(i).setCellValueFactory(new Callback<CellDataFeatures<TableData, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<TableData, String> p) {
					
   		    	 	return new ReadOnlyObjectWrapper<String>(p.getValue().GetFromList(j));
					}
   		  		});
			  }
			}
			//PerfomanceTable.getColumns().add(pData.get(pData.size()-1));
			PerfomanceTable.setItems(Data);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(Main.getPrimaryStage());
	        alert.setTitle("Ошибка");
	        alert.setHeaderText("Ничего не выбрано");
	        alert.setContentText("Выберите из списка или создайте новый критерий или альтернативу");
	        alert.showAndWait();	
		}
	});
	CalculateButton.setOnAction(event ->{
		
	 ObservableList<List<Double>> data = FXCollections.observableArrayList();
	 int c = 0;
	 int c1 = 0;
	 try {
		ftr();
		c++;
	 } catch (Exception e3) {
	 }
	 
	 if (c != 0) {
	  if(Data.size() != 0) {
		try {
			 
			data = Translation();
			t = new VikorCalculation();
			t.VikorCalcule(data, list);
			c1++;
		  } catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		  }
	 }
	 else {
		 	Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(SettingsView.getPrimaryStage());
	        alert.setTitle("Ошибка");
	        alert.setHeaderText("Неправильное заполнение");
	        alert.setContentText("Заполните таблицы");
	        alert.showAndWait();
	  }
	 }else
	 {
		 Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(SettingsView.getPrimaryStage());
	        alert.setTitle("Ошибка");
	        alert.setHeaderText("Неправильное заполнение");
	        alert.setContentText("Заполните вес критериев");
	        alert.showAndWait(); 
	 }
	 
	 if (c1 != 0) {
	    try {
		 		Calculate f = new Calculate();
		 		Stage primaryStage = new Stage();
				f.start(primaryStage);
			} catch (Exception e6) {
				//e1.printStackTrace();
			}
	 }
});
	
	SettingsButton.setOnAction(event ->{
	
		SettingsView f = new SettingsView();
		Stage primaryStage = new Stage();
		try {
			f.start(primaryStage);
			} catch (IOException e1) {
			e1.printStackTrace();
			}
	});	
	
	QvButton.setOnAction(e ->{
		 ObservableList<List<Double>> data = FXCollections.observableArrayList();
		 int c = 0;
		 int c1 = 0;
		 try {
			ftr();
			c++;
		} catch (Exception e3) {
			//e3.printStackTrace();
		}
		 
		 if (c != 0) {
		  if(Data.size() != 0) {
			try {
				 
				data = Translation();
				t = new VikorCalculation();
				t.VikorCalcule(data, list);
				c1++;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				//e2.printStackTrace();
			}
		 }
		 else {
			 	Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните таблицы");
		        alert.showAndWait();
		  }
		 }else
		 {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните вес критериев");
		        alert.showAndWait(); 
		 }
		 
		 
		
		 if (c1 != 0) {
			try {
				QvView f = new QvView();
				Stage primaryStage = new Stage();
				f.start(primaryStage);
				} catch (IOException e1) {
				e1.printStackTrace();
				}
		 }
	});
	SrwButton.setOnAction(e->{
		ObservableList<List<Double>> data = FXCollections.observableArrayList();
		 int c = 0;
		 int c1 = 0;
		 try {
			ftr();
			c++;
		} catch (Exception e3) {
			//e3.printStackTrace();
		}
		 
		 if (c != 0) {
		  if(Data.size() != 0) {
			try {
				 
				data = Translation();
				t = new VikorCalculation();
				t.VikorCalcule(data, list);
				c1++;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 }
		 else {
			 	Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните таблицы");
		        alert.showAndWait();
		  }
		 }else
		 {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните вес критериев");
		        alert.showAndWait(); 
		 }
		 
		 if (c1 != 0) {
			 try {
				SRview f = new SRview();
				Stage primaryStage = new Stage();
				f.start(primaryStage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	 		
		}
		
	});
	VpButton.setOnAction(e->{
		ObservableList<List<Double>> data = FXCollections.observableArrayList();
		 int c = 0;
		 int c1 = 0;
		 try {
			ftr();
			c++;
		} catch (Exception e3) {
			//e3.printStackTrace();
		}
		 
		 if (c != 0) {
		  if(Data.size() != 0) {
			try {
				 
				data = Translation();
				t = new VikorCalculation();
				t.VikorCalcule(data, list);
				c1++;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 }
		 else {
			 	Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните таблицы");
		        alert.showAndWait();
		  }
		 }else
		 {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните вес критериев");
		        alert.showAndWait(); 
		 }
		 
		 if (c1 != 0) {
				// data1 = data;
			 try {
				ValuePath f = new ValuePath();
				Stage primaryStage = new Stage();
				f.start(primaryStage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
		}
	});
	DominateButton.setOnAction(e->{
		ObservableList<List<Double>> data = FXCollections.observableArrayList();
		 int c = 0;
		 int c1 = 0;
		 try {
			ftr();
			c++;
		} catch (Exception e3) {
		}
		 
		 if (c != 0) {
		  if(Data.size() != 0) {
			try {
				 
				data = Translation();
				t = new VikorCalculation();
				t.VikorCalcule(data, list);
				c1++;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 }
		 else {
			 	Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните таблицы");
		        alert.showAndWait();
		  }
		 }else
		 {
			 Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Заполните вес критериев");
		        alert.showAndWait(); 
		 }
		 
		 if (c1 != 0) {
				// data1 = data;
			 try {
				Domination f = new Domination();
				Stage primaryStage = new Stage();
				f.start(primaryStage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	 		
		}
	});
}
	static ObservableList<List<Double>> data1 = FXCollections.observableArrayList();	
	
	public static VikorCalculation t;
   //int set = 0; 
    public void AddDataFtable() {
    	/*
    	 * col 1
    	 */
    	 FnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    	 FnameColumn.setCellFactory(TextFieldTableCell.<FunctionData> forTableColumn());
  
         // On Cell edit commit (for FullName column)
    	 FnameColumn.setOnEditCommit((CellEditEvent<FunctionData, String> event2) -> {
             
    		 TablePosition<FunctionData, String> pos = event2.getTablePosition();
             String newFullName = event2.getNewValue();
             int row = pos.getRow();
             FunctionData person = event2.getTableView().getItems().get(row);
             person.setFullName(newFullName);
             
         });
    	 /*
    	 * col 2
    	 */
    	WeighColumn.setCellValueFactory(new PropertyValueFactory<>("weigh"));
    	WeighColumn.setCellFactory(TextFieldTableCell.<FunctionData> forTableColumn());
    	
        // On Cell edit commit (for FullName column)
    	WeighColumn.setOnEditCommit((CellEditEvent<FunctionData, String> event4) -> {
            
    		TablePosition<FunctionData, String> pos = event4.getTablePosition();
            String newW = event4.getNewValue();
            int row = pos.getRow();
            FunctionData Weigh = event4.getTableView().getItems().get(row);
            Weigh.setWeigh(newW);
            
        });
    	/*
    	 * combobox column 3
    	 */
    	 ObservableList<MaxMin> MxMnList = FXCollections.observableArrayList(MaxMin.values());
	 
    	 	MaxMinColumn.setCellValueFactory(new Callback<CellDataFeatures<FunctionData, MaxMin>,
    	 			ObservableValue<MaxMin>>() {
	 
	            @Override
	            public ObservableValue<MaxMin> call(CellDataFeatures<FunctionData, MaxMin> param) {
	            	
	            	FunctionData Fdata = param.getValue();
	                String MxMnCode = Fdata.getMaxmin();
	                MaxMin maxmin = MaxMin.getByCode(MxMnCode);
	                return new SimpleObjectProperty<MaxMin>(maxmin);
	            }
	        });
	 
	        MaxMinColumn.setCellFactory(ComboBoxTableCell.forTableColumn(MxMnList));
	 
	        MaxMinColumn.setOnEditCommit((CellEditEvent<FunctionData, MaxMin> event1) -> {
	            
	        	TablePosition<FunctionData, MaxMin> pos = event1.getTablePosition();
	            MaxMin newmaxmin = event1.getNewValue();
	            int row = pos.getRow();
	            FunctionData Fdata = event1.getTableView().getItems().get(row);
	            Fdata.setMaxmin(newmaxmin.getCode());
	            
	        });
	        ObservableList<FunctionData> list = FunctionList();
	        FunctionTable.setItems(list); 
    }   
}


