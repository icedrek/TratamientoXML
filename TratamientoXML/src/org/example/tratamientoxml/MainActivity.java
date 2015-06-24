package org.example.tratamientoxml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String TAG = "TratamientoXML";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		leerXML();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Lectura del XML
	private void leerXML() {
		FileInputStream fin = null;
		try {
			fin = openFileInput("test.xml");
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();	
		}
		
		//Parser para el fichero
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(fin, "UTF-8");	
			//avanzamos por el fichero
			int event = parser.next();
			while(event != XmlPullParser.END_DOCUMENT) {
				if(event == XmlPullParser.START_TAG) {
					Log.d(TAG, "<" + parser.getName() + ">");
					for(int i = 0; i < parser.getAttributeCount(); i++) {
						Log.d(TAG, "\t" + parser.getAttributeName(i) + " = " + 
					          parser.getAttributeValue(i));	
					}	
				}
				if(event == XmlPullParser.TEXT && parser.getText().trim().length() != 0)
					Log.d(TAG, "\t\t" + parser.getText());
				
				if(event == XmlPullParser.END_TAG)
					Log.d(TAG, "</" + parser.getName() + ">");

				event = parser.next();
			}
			fin.close();
			Toast.makeText(this, "Leido correctamente", Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();	
		}
	}

	//Escritura del XML
	private void escribirXML(){
		FileOutputStream fout = null;
		
		try {
			fout = openFileOutput("test.xml", MODE_PRIVATE);			
		} catch (FileNotFoundException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();		
		}
		
		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(fout, "UTF-8");
			serializer.startDocument(null, true);
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
			
			//Crear Fichero
			serializer.startTag(null, "vidasConcurrentes");
			serializer.attribute(null, "numero_autores", "2");
			serializer.startTag(null, "autor");
			serializer.attribute(null, "nombre", "meta");
			serializer.text("Programación en diferentes lenguajes");
			serializer.endTag(null, "autor");
			serializer.startTag(null, "autor");
			serializer.attribute(null, "nombre", "sshMan");
			serializer.text("Seguridad Informática y Pentesting");
			serializer.endTag(null, "autor");
			serializer.endTag(null, "vidasConcurrentes");
			serializer.endDocument();
			serializer.flush();
			fout.close();
			Toast.makeText(getApplicationContext(), "Escrito correctamente", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();	
		}


	}

}
