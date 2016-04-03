package com.ctguer.controller;

/**
 *
 *
 */
public class ImageInfo {

	public String imageMsg;		
	public int imageId;			
	public int bgId;			

	public ImageInfo(String msg, int id1,int id2) {
		imageId = id1;
		imageMsg = msg;
		bgId = id2;
	}
}
