package org.vikor.data.ptabledata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableData {
	
	private ObservableList<String> list;

	public TableData(ObservableList<String > list) {
		this.list = list;
		//System.out.println(this.list.toString()+"List from class");
	}
	public ObservableList<String > getList() {
		return list;
	}
	public String GetFromList(int index) {
		
		return this.list.get(index);
	}
	public void add(String s) {
		this.list.add(s);
	}
	public void addtoLast() {
		this.list.add("-/-");
	}
	public void setinlist(int index,String s) {
		this.list.set(index, s);
	}

	public void setList(ObservableList<String> list) {
		this.list = list;
	}
	public void remove(int index) {
		ObservableList<String> l = FXCollections.observableArrayList();
		
		for(int i =0; i < this.list.size();i++) {
			if (i != index) {
			l.add( this.list.get(i));
			}
			 this.list = l;
			 System.out.println(this.list.toString()+"List from class");
		}
		
	}
	
	
}
