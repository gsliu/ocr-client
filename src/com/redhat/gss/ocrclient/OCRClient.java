package com.redhat.gss.ocrclient;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
 
public class OCRClient 
{
	/*
	 * 
	 */
    public String executeMultiPartRequest(String urlString, File file, String fileName) throws Exception 
    {
    	HttpClient client = new DefaultHttpClient() ;
        HttpPost postRequest = new HttpPost (urlString) ;
        String result = "";
        try
        {
        	//Set various attributes 
            MultipartEntity multiPartEntity = new MultipartEntity () ;
            //multiPartEntity.addPart("fileDescription", new StringBody(fileDescription != null ? fileDescription : "")) ;
            multiPartEntity.addPart("fileName", new StringBody(fileName != null ? fileName : file.getName())) ;
 
            FileBody fileBody = new FileBody(file, "application/octect-stream") ;
            //Prepare payload
            multiPartEntity.addPart("image", fileBody) ;
 
            //Set to request body
     
            postRequest.setEntity(multiPartEntity) ;
            
            //System.out.println(postRequest.getAllHeaders().toString());
            
            //Send request
            HttpResponse response = client.execute(postRequest) ;

            //Verify response if any
            if (response != null)
            {
                
                InputStream in = response.getEntity().getContent();
                
                result = convertInputStreamIntoString(in);
                System.out.println(result);
              
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace() ;
        }
		return result;
		
    }
    private String convertInputStreamIntoString(InputStream in) throws IOException {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder out = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        out.append(line+"\n");
	    }
	    return out.toString();
    }
}