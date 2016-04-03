package com.ctguer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

public class FileIO {

	
	private final static String LOGTAG = "FileIO";
	
	public static void saveObjectToFile(Context ct, String filename, Object Object)
	{
		try 
		{  
			if(Object != null)
			{
				FileOutputStream fos = ct.openFileOutput(filename, Context.MODE_PRIVATE);  
		        ObjectOutputStream oos = new ObjectOutputStream(fos);  
		        oos.writeObject(Object);
		        oos.close();
		        fos.close();
		        Log.i(LOGTAG, "save "+filename);
			}
	    } catch (Exception e) 
	    {  
	    	System.out.println("save object ex: " + e.getMessage());
	        e.printStackTrace();  
	    } 
    } 
	
	//É¾³ýÎÄ¼þ
	public static void deleteFile(String filename)
	{
		try 
		{  
			if(filename != null)
			{
				File tmpFile = new File(URLs.fileBasePath +filename);  
				if(tmpFile.exists())
				{
					tmpFile.delete();
				} 
		        Log.i(LOGTAG, "delete "+filename);
			}
	    } catch (Exception e) 
	    {  
	    	Log.e(LOGTAG, "delete object ex: " + e.getMessage());
	        e.printStackTrace();  
	    } 
    } 
	
	public static Object getObjectFromFile(Context ct, String filename)
    { 
		Object object = null;
		try 
		{  
			File tmpFile = new File(URLs.fileBasePath + filename);
			if(tmpFile.exists())
			{
				FileInputStream fis = ct.openFileInput(filename);
	            ObjectInputStream ois = new ObjectInputStream(fis);  
	            object = ois.readObject();  
	            ois.close();
	            fis.close();
	            Log.i(LOGTAG, "get "+filename);
			}
        } catch(Exception e)
        {
        	System.out.println("get object ex: " + e.getMessage());
        	return null;
        }
		return object;
    }
}
