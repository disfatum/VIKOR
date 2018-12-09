package org.vikor.data.VikorTableData;

public class VikorTableData {

	private String AltName;
	private Double S;
	private Double R;
	private Double Q;
	
	public VikorTableData(String a,double s,double r,double q) {
		this.setAltName(a);
		this.setS(s);
		this.setR(r);
		this.setQ(q);
	}

	public String getAltName() {
		return AltName;
	}

	public void setAltName(String altName) {
		AltName = altName;
	}

	public Double getS() {
		return S;
	}

	public void setS(Double s) {
		S = s;
	}

	public Double getR() {
		return R;
	}

	public void setR(Double r) {
		R = r;
	}

	public Double getQ() {
		return Q;
	}

	public void setQ(Double q) {
		Q = q;
	}
}
