package org.vikor.data.SettingsData;

public class SettingsData {

	private double v;
	private int Qvstep;
	private double Qvs;
	private double Srs;
	private int SRstep;
	private boolean Fuzzy;
	
	public SettingsData(double v,int qv, int sr, boolean f,double Qvs,double Srs) {
		this.v = v;
		this.Qvstep = qv;
		this.SRstep = sr;
		this.Fuzzy = f;
		this.Qvs = Qvs;
		this.Srs = Srs;
	}
	public double getV() {
		return v;
	}
	public void setV(double v) {
		this.v = v;
	}
	
	public int getQvstep() {
		return Qvstep;
	}
	public void setQvstep(int qvstep) {
		Qvstep = qvstep;
	}
	public int getSRstep() {
		return SRstep;
	}
	public void setSRstep(int sRstep) {
		SRstep = sRstep;
	}
	public boolean getFuzzy() {
		return Fuzzy;
	}
	public void setFuzzy(boolean fuzzy) {
		Fuzzy = fuzzy;
	}
	public double getQvs() {
		return Qvs;
	}
	public void setQvs(double qvs) {
		Qvs = qvs;
	}
	public double getSrs() {
		return Srs;
	}
	public void setSrs(double srs) {
		Srs = srs;
	}
}
