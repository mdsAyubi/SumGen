package com.minorpro.mipro;


import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;





public class MainActivity extends Activity implements OnClickListener {
	
	Button summarize;
	EditText url,finalSummaryArea;
	String summary;
	String[] sentences;
	double[] result,scores;
	int[] sentenceIndices;
	String finalSummary;
	
public static final int NUMBER_OF_SENTENCES=6;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        

        
        
        setContentView(R.layout.activity_main);
        
    	sentenceIndices=new int[6];
        summarize=(Button)findViewById(R.id.summarize);
        url=(EditText)findViewById(R.id.url);
        url.setText("http://en.wikipedia.org/wiki/Yacc");
        
        finalSummaryArea=(EditText)findViewById(R.id.summaryArea);
       // finalSummaryArea.setScroller(new Scroller(myContext)); 
       // finalSummaryArea.setMaxLines(1); 
        finalSummaryArea.setVerticalScrollBarEnabled(true); 
        finalSummaryArea.setMovementMethod(new ScrollingMovementMethod()); 
        
        
        summarize.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
        
    }

	public void onClick(View v) {
	
		final String urlField=url.getText().toString();
		Log.d("Interesting", "I was in onClick() method"+urlField);
		//SendAlchemyCall(urlField);
		new Thread() {
			public void run() {
				
				int i,j,index=0;
				double t;
				StringBuilder finalSummary=new StringBuilder();
				
				try {
				TextExtractor tx=new TextExtractor(MainActivity.this, urlField);
				summary=tx.SendAlchemyCall(urlField);
				
				SentenceExtractor sx=new SentenceExtractor(MainActivity.this,summary);
				sentences=sx.action();
				
				Document1 d=new Document1(sentences);
				result=d.action();
		
				
				Log.d("Interesting", "Result MAtrix copied in MainActivity ");
				
				/*
				i=0;
				for(double temp:result)
				{
					
					Log.d("Interesting", " "+temp);
					
					scores[i]=temp;
					i++;
				}
				*/
				scores=Arrays.copyOfRange(result, 0, result.length);
				
				
				Arrays.sort(scores);
				Log.d("Interesting", "After copying and sorting the resultant ");
				
				
				for(double temp:scores)
				{
					Log.d("Interesting", " "+temp);
					
				}
				
				Log.d("Interesting", "Going to reverse the scores..... ");
				
				for(i=0,j=scores.length-1;i<scores.length/2;i++,j--)
				{
					t=scores[i];
					scores[i]=scores[j];
					scores[j]=t;
				}
					
				Log.d("Interesting", "Reversed scores ");
				
				for(double temp:scores)
				{
					Log.d("Interesting", " "+temp);
					
				}
				
				
				Log.d("Interesting", "Building summary now.... ");
				
				
				for(i=0,j=0;i<NUMBER_OF_SENTENCES;i++,j++)
				{
					Log.d("Interesting", " Building.......i="+i+"j="+j);
						
					index=search(result, scores[i]);
					
					Log.d("Interesting","binary search after j= "+j+"index="+index);

					sentenceIndices[j]=index;
					
					Log.d("Interesting", "After copying index");
					
					Log.d("Interesting", " "+index);
				}
				
				
				Log.d("Interesting", "Top Indices");
				for(int temp:sentenceIndices)
					Log.d("Interesting", " "+temp);
				
				
				
				Arrays.sort(sentenceIndices);
				
				Log.d("Interesting", "Sorted Indices");
				for(int temp:sentenceIndices)
					Log.d("Interesting", " "+temp);
				
				
				for(int k:sentenceIndices)
				{
					finalSummary.append(sentences[k]);
					finalSummary.append(".");
					
				}
				MainActivity.this.finalSummary=finalSummary.toString();
				Log.d("Interesting", MainActivity.this.finalSummary);
				
				
				runOnUiThread(new Runnable() {
				     public void run() {

				//stuff that updates ui
				 		finalSummaryArea.setText(MainActivity.this.finalSummary);

				    }
				});
		
				
				
				
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.d("Interesting",e.toString());
					e.printStackTrace();
				}
			}

		
		}.start();


	}

private int search(double[] arr,double value)
{
	int index=0;
	for(int i=0;i<arr.length-1;i++)
	if(arr[i]==value)
		{index=i; break;}
	
	
   
return index;
}
}