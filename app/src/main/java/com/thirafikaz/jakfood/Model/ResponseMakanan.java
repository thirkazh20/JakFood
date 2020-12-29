package com.thirafikaz.jakfood.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMakanan{

	@SerializedName("DataMakanan")
	private List<DataMakananItem> dataMakanan;

	public void setDataMakanan(List<DataMakananItem> dataMakanan){
		this.dataMakanan = dataMakanan;
	}

	public List<DataMakananItem> getDataMakanan(){
		return dataMakanan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseMakanan{" + 
			"dataMakanan = '" + dataMakanan + '\'' + 
			"}";
		}
}