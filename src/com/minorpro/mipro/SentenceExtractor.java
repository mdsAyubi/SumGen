package com.minorpro.mipro;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import android.app.Activity;
import android.util.Log;

public class SentenceExtractor {
String summarizableString;
String[] sentences;
//Context myContext;
Activity mainActivity;

public SentenceExtractor(Activity mainActivity,String summarizableString) {

	this.summarizableString=summarizableString;
	this.mainActivity=mainActivity;
 
}
public String[] action()
{
 Log.d("Interesting","Sentence detector demo");
try{	
	train("sent.train", "sent.bin");
	//detect("sent.bin");
	detect("en-sent.bin");

	}catch(Exception e){Log.d("Interesting",e.toString());}
return sentences;
}



private void train(String trainfilename, String modelfilename) throws IOException {
	System.out.println("Training");
	InputStream fs= mainActivity.getAssets().open(trainfilename);
	
	ObjectStream<String> lineStream = new PlainTextByLineStream(fs, "UTF-8");
	ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream);

	SentenceModel model = SentenceDetectorME.train("en",sampleStream, true, null, /*cutoff*/5, /*iterations*/100);
	//OutputStream modelOut = new BufferedOutputStream(new FileOutputStream(modelfilename));
	//model.serialize(modelOut);
}



private void detect(String filename) throws IOException {
	InputStream modelIn = mainActivity.getAssets().open(filename);
	SentenceModel model = new SentenceModel(modelIn);
	SentenceDetector sentenceDetector = new SentenceDetectorME(model);
	
	/*
	String sourceUrlString="http://www.paulgraham.com/icad.html";
	if (sourceUrlString.indexOf(':')==-1) sourceUrlString="file:"+sourceUrlString;
	MicrosoftConditionalCommentTagTypes.register();
	PHPTagTypes.register();
	PHPTagTypes.PHP_SHORT.deregister(); // remove PHP short tags for this example otherwise they override processing instructions
	MasonTagTypes.register();
	Source source=new Source(new URL(sourceUrlString));
	
	// Call fullSequentialParse manually as most of the source will be parsed.
			source.fullSequentialParse();
			
			
			TextExtractor textExtractor=new TextExtractor(source) {
				public boolean excludeElement(StartTag startTag) {
					return startTag.getName()==HTMLElementName.P || "control".equalsIgnoreCase(startTag.getAttributeValue("class"));
				}
			};
			//System.out.println(textExtractor.setIncludeAttributes(true).toString());
	  
			
	*/
/*	System.out.println("Detecting using "+filename+" - easy");
	String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");
	for (String sentence: sentences) 
		System.out.println("\t'"+sentence+"'");
	*/
			
	Log.d("Interesting","Detecting using "+filename+" - hard");
	sentences = sentenceDetector.sentDetect(summarizableString);	
	for (String sentence: sentences) 
		Log.d("Interesting","["+sentence+"]");
	
	//String key="sent";
	//Bundle bundle=new Bundle();
	//bundle.putStringArray(key, sentences);
	//Intent i = new Intent(mainActivity, OutputActivity.class);

	//i.putExtra(key, sentences);
	//mainActivity.startActivity(i);

}


	
}
