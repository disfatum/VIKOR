package org.vikor.data.ftabledata;

public enum MaxMin {

	 MAXIMUM("MAX", "MAXIMUM"), MINIMUM("MIN", "MINIMUM");
	 
	   private String code;
	   private String text;
	 
	   private MaxMin(String code, String text) {
	       this.code = code;
	       this.text = text;
	   }
	 
	   public String getCode() {
	       return code;
	   }
	 
	   public String getText() {
	       return text;
	   }
	 
	   public static MaxMin getByCode(String maxmin) {
	       for (MaxMin g : MaxMin.values()) {
	           if (g.code.equals(maxmin)) {
	               return g;
	           }
	       }
	       return null;
	   }
	 
	   @Override
	   public String toString() {
	       return this.text;
	   }
}
