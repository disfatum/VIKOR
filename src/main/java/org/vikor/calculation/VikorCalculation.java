package org.vikor.calculation;

import java.util.Collections;
import java.util.List;

import org.vikor.controller.Controller;
import org.vikor.data.ftabledata.FunctionData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VikorCalculation {
	public ObservableList<Double> S = FXCollections.observableArrayList();
	public ObservableList<Double> R = FXCollections.observableArrayList(); 
	public ObservableList<Double> Q = FXCollections.observableArrayList();
/*
 * ��� ������������	
 */
	public ObservableList<List<Double>> SRw = FXCollections.observableArrayList();
	public ObservableList<Double> Rw = FXCollections.observableArrayList(); 
	public ObservableList<Double> Qv = FXCollections.observableArrayList();
	public ObservableList<String> alt = FXCollections.observableArrayList();
	public Double Rminus; 
	public Double Sminus;
	public Double Rstar; 
	public Double Sstar; 
	public int size;
	public ObservableList<Double> star = FXCollections.observableArrayList();
	public ObservableList<Double> minus = FXCollections.observableArrayList();
	public ObservableList<Double> W = FXCollections.observableArrayList();
	public ObservableList<List<Double>> d = FXCollections.observableArrayList();
	
	public void VikorCalcule(ObservableList<List<Double>> data,
			ObservableList<FunctionData> l) {
		d = data;
		ObservableList<List<Double>> Sdata = FXCollections.observableArrayList();
		
		
		
			for(int j = 0; j < data.get(0).size();j++) {
				List<Double> bf = FXCollections.observableArrayList();
				for(int i = 0; i < data.size();i++) {
					bf.add(data.get(i).get(j));
			}
				Sdata.add(bf);
		}
		System.out.println(Sdata.toString()+"bf");
		int Asize = data.size();//���������� �����������
		size = Asize;
		int Fsize = data.get(0).size();//���������� ���������
		
/*
 * ��� 1; ������ � ������ �������� �������� ��� ��� ��� ����
 */
		
		ObservableList<String> MxMn = FXCollections.observableArrayList();
		ObservableList<Double> S = FXCollections.observableArrayList();
		ObservableList<Double> R = FXCollections.observableArrayList();
		ObservableList<Double> R_buf;
		
		ObservableList<Double> Q = FXCollections.observableArrayList();
		Double V = Controller.f.getV();
		
		for(int i  = 0; i < Fsize; i++) {
			MxMn.add(l.get(i).getMaxmin());
			W.add(Double.valueOf(l.get(i).getWeigh()));
		}System.out.println(MxMn.toString()+" msmn");
		
		//for(int i  = 0; i < Asize; i++) {Collections.sort(Sdata.get(i));}
		
		double stars = 0, starm = 0;
		
		for(int i  = 0; i < Sdata.size(); i++) {
			
			for(int j  = 0; j < Fsize; j++) {
				if(MxMn.get(j) == "MAX") {
					ObservableList<List<Double>> Sdata1 = FXCollections.observableArrayList();
					Sdata1 = Sdata;
					stars = Collections.max(Sdata1.get(i));
					starm = Collections.min(Sdata1.get(i));
				}
				if(MxMn.get(j) == "MIN") {
					ObservableList<List<Double>> Sdata1 = FXCollections.observableArrayList();
					Sdata1 = Sdata;
					starm = Collections.max(Sdata1.get(i));
					stars = Collections.min(Sdata1.get(i));
					
				}
				star.add(stars);
			minus.add(starm);
			}
			
		}
		System.out.println(star.toString()+" star");
		System.out.println(minus.toString()+" minus");
		
/*
 * ��� 2;��������� �������� Si � Ri
 */
		
		for(int i  = 0; i < Asize; i++) {
			double e = 0;
			R_buf = FXCollections.observableArrayList();
			for(int j  = 0; j < Fsize; j++) {
				e = e +( W.get(j)*((star.get(j) - data.get(i).get(j))/(star.get(j) - minus.get(j)))); 
				R_buf.add(W.get(j)*((star.get(j) - data.get(i).get(j))/(star.get(j) - minus.get(j))));
				System.out.println(e+" S\n");
			}
			Collections.sort(R_buf);
			R.add(R_buf.get(R_buf.size()-1));
			S.add(e);
		}
		System.out.println(R.toString()+"-R");
		System.out.println(S.toString()+"-S");
		Double Rminus = Collections.max(R);
		Double Sminus = Collections.max(S);
		Double Rstar = Collections.min(R);
		Double Sstar = Collections.min(S);
	
/*
 * ��� 3;��������� �������� Qi
 */
		for(int i  = 0; i < Asize; i++) {
			Double q = (V * (S.get(i) - Sstar) / (Sminus - Sstar))+((1 - V) * (R.get(i) - Rstar)/(Rminus - Rstar));
			Q.add(q);
		}
		System.out.println(Q.toString()+" Q");
		this.Q = Q;
		this.S = S;
		this.R = R;
		this.Sminus = Collections.max(S);
		this.Sstar = Collections.min(S);
		this.Rminus = Collections.max(R);
		this.Rstar = Collections.min(R);
		
		int o = Controller.f.getQvstep();
		double qvs = Controller.f.getQvs();
		double v = Controller.f.getV();
		ObservableList<Double> v1 = FXCollections.observableArrayList();
		for(int i = 0; i < o /2;i++) {
			v1.add(v - (o/2 - i)*(qvs) );
		}
		int j = 0;
		for (int i = o /2; i < o;i++) {
			
			v1.add(v+j*qvs);
			j++;
		}
		this.Qv = v1;
		System.out.println(v1.toString()+"v1");
		
		
	}
	public ObservableList<Double> VikorV(double v) {
		ObservableList<Double> qq = FXCollections.observableArrayList(); 
		for(int i  = 0; i < S.size(); i++) {
			Double q = (v * (S.get(i) - Sstar) / (Sminus - Sstar))+((1 - v) * (R.get(i) - Rstar)/(Rminus - Rstar));
			qq.add(q);
		}
		return qq;
	}
	public ObservableList<String> altname(){
		ObservableList<String> qq = FXCollections.observableArrayList();
		qq = Controller.altname;
		return qq;
	}
	public Double VikorS(double w,int c){
		
			double S = 0;
			double e = 0;
			for(int j  = 0; j < Controller.list.size(); j++) {
				
				S = e + w*((star.get(j) - d.get(c).get(j))/(star.get(j) - minus.get(j)));
				e = S;
			}
		
		return S;
	}
	public Double VikorR(double w,int c){
		
			double R = 0;
			ObservableList<Double> R1 = FXCollections.observableArrayList();
			for(int j  = 0; j < Controller.list.size(); j++) {
				R1.add(w*((star.get(j) - d.get(c).get(j))/(star.get(j) - minus.get(j))));
			}
		R = Collections.max(R1);
		return R;
	}
}
