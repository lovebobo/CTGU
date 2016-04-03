package com.ctguer.model;

import java.io.Serializable;

public class BookDetail  implements Serializable{
	public String place;
	public String status;
	public String indexNumber;

	public BookDetail(String place, String status, String indexNumber) {
		super();
		this.place = place;
		this.status = status;
		this.indexNumber = indexNumber;
	}
}
