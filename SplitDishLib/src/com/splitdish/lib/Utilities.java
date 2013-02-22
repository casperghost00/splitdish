package com.splitdish.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.content.Context;

public class Utilities {

    public static String getJSONFromRawResource(Context context, int rawResource) throws IOException{
	    InputStream is = context.getResources().openRawResource(rawResource);
	    Writer writer = new StringWriter();
	    char[] buffer = new char[2048];
	    try {
	        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        int n;
	        while ((n = reader.read(buffer)) != -1) {
	            writer.write(buffer, 0, n);
	        }
	    } finally {
	        is.close();
	    }
	
	    return writer.toString();
    }
}
