package org.vikor.data.ftabledata;

public class FunctionData {
	
	 private  String fullName;
	    private  String weigh;
	    private  String maxmin;
	 
	    public FunctionData(String fullName, String weigh, String maxmin) {
	        this.fullName = fullName;
	        this.weigh = weigh;
	        this.setMaxmin(maxmin);
	    }
	 
	    public String getFullName() {
	        return fullName;
	    }
	 
	    public void setFullName(String fullName) {
	    	this.fullName = fullName;
	    }
	 
	    public String getWeigh() {
	        return weigh;
	    }
	 
	    public void setWeigh(String weigh) {
	    	this.weigh = weigh;
	    }
	 
	 
	    public void setMaxMin(String maxmin) {
	        this.setMaxmin(maxmin);
	    }

		public  String getMaxmin() {
			return maxmin;
		}

		public void setMaxmin(String maxmin) {
			this.maxmin = maxmin;
		}
		
}
