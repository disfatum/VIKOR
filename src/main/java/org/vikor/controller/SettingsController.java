package org.vikor.controller;

import org.vikor.view.settings.SettingsView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SettingsController {

	   @FXML
	    private TextField coef_v;

	    @FXML
	    private TextField QV_counter;

	    @FXML
	    private TextField Qvstep;

	    @FXML
	    private TextField SR_counter;

	    @FXML
	    private TextField SRstep;

	    @FXML
	    private CheckBox BoolButton;

	    @FXML
	    private Button OkButton;

	    @FXML
	    private Button CencelButton;
    
	    public void AddNewData() throws Exception{
	    	try {
	    		
	    	String v = coef_v.getText();
	        Controller.f.setV(Double.valueOf(v));
	        
	        String qv = QV_counter.getText();
	        Controller.f.setQvstep(Integer.valueOf(qv));
	        
	        String sr = SR_counter.getText();
	        Controller.f.setSRstep(Integer.valueOf(sr));
	        
	        String srs = SRstep.getText();
	        Controller.f.setSrs(Double.valueOf(srs));
	        
	        String qvs = Qvstep.getText();
	        Controller.f.setQvs(Double.valueOf(qvs));
	        
	        if(BoolButton.isSelected()) {
	        	Controller.f.setFuzzy(true);
	        }
	        else {
	        	Controller.f.setFuzzy(false);
	        }
	        
	    	}catch( Exception e ){
	    		Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(SettingsView.getPrimaryStage());
		        alert.setTitle("Ошибка");
		        alert.setHeaderText("Неправильное заполнение");
		        alert.setContentText("Укажите число от 0 до 1");
		        alert.showAndWait();
	        }
	    }
 @FXML
 void initialize() {
	
	coef_v.setText(String.valueOf(Controller.f.getV()));
	QV_counter.setText(String.valueOf(Controller.f.getQvstep()));
	SR_counter.setText(String.valueOf(Controller.f.getSRstep()));
	Qvstep.setText(String.valueOf(Controller.f.getQvs()));
	SRstep.setText(String.valueOf(Controller.f.getSrs()));
	
	if(Controller.f.getFuzzy() == false) {
		BoolButton.setSelected(false);
	}else {
		BoolButton.setSelected(true);
	}
	
    CencelButton.setOnAction(event->{
    	Stage ps;
    	ps  = (Stage) CencelButton.getScene().getWindow();
    	ps.close();
    	
    });
    OkButton.setOnAction(event->{
    	try {
			AddNewData();
			Stage ps;
	    	ps  = (Stage) OkButton.getScene().getWindow();
	    	ps.close();
		} catch (Exception e) {
			//do nothing
		}
    });

 }
}