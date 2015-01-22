package com.minorpro.mipro;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.orchestr8.android.api.AlchemyAPI;

public class TextExtractor {

MainActivity mainAct;
String urlField;


public String AlchemyAPI_Key = "0ae084296c02a686f679bc6d3fd06c1de0852fbd";
public String Summarizable_string;



TextExtractor(MainActivity mainAct,String urlField)
{
	this.mainAct=mainAct;
	this.urlField=urlField;
	//SendAlchemyCall(urlField);

}


public String SendAlchemyCall(String urlField)
{
	Document doc = null;
	AlchemyAPI api = null;
	
	
	try
	{
		api = AlchemyAPI.GetInstanceFromString(AlchemyAPI_Key);
		Log.d("Interesting", "I was in sendAlchemyCall() method"+api.toString());
	
		
		
		doc = api.URLGetText(urlField);
	
	if(doc==null)
		Log.d("Interesting", "After calling urlgettext...we get null doc");
		
	
	Summarizable_string=ShowDocInTextView(doc);
	//Log.d("Interesting", "After calling urlgettext"+doc.toString());
	
	}
	catch(Throwable t)
	{
		Log.d("Interesting", t.toString());
	}

	/*catch( IllegalArgumentException ex )
	{
		final_summary.setText("Error loading AlchemyAPI.  Check that you have a valid AlchemyAPI key set in the AlchemyAPI_Key variable.  Keys available at alchemyapi.com.");
		return;
	}*/

	return Summarizable_string;

}	

private String ShowDocInTextView(Document doc)
{
	//final_summary.setText("");
	Log.d("Interesting", "I was in showDocinTextView");
	StringBuilder finalString=new StringBuilder();
	if( doc == null )
		return "";
	Element root = doc.getDocumentElement();
    NodeList items = root.getElementsByTagName("text");


    for (int i=0;i<items.getLength();i++)
    {
    	
    	Node concept = items.item(i);
    	String astring = concept.getNodeValue();
    	astring = concept.getChildNodes().item(0).getNodeValue(); 
    	//final_summary.append(astring+"\n");
    	finalString.append(astring);
    	
    	//	Log.d("Interesting",astring);
    	//Log.d("Interesting",Summarizable_string);
   
    	
    	
    	//new SentenceExtractor(mainAct,Summarizable_string);
    
    }
    Log.d("Interesting","End of Text xtraction");
	
    return finalString.toString();

}






}
