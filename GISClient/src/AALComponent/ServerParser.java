package AALComponent;

import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParserException;

import common.Notifications;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import ContextModel.InterfaceContext;
import ContextModel.LocationContext;
import ContextModel.MetadataContext;
import ContextModel.TemperatureContext;
import ContextModel.TemporalContext;
import ContextModel.VelocityContext;

/**
 * class that parses the xml using the kXML
 * @author Vlad Herescu
 *
 */
public class ServerParser extends Parser{
	
	/**
	 * initializing the list of files to be parsed
	 */
	public ServerParser() 
	{
		 m_fileNames = new ArrayList<String>();
		 m_fileNames.add("xml/time_context_element.xml");
		 m_fileNames.add("xml/location_context_element.xml");
	     m_fileNames.add("xml/velocity_context_element.xml");
	     m_fileNames.add("xml/temperature_context_element.xml");
	}
	

	@Override
	public ArrayList<Notifications> factoryMethod()
	{
		ArrayList<Notifications> contextElements = new ArrayList<Notifications>();
		KXmlParser myParser = new KXmlParser();
		String type;
		Reader dataReader;
		
		for(String fileName : m_fileNames)
		{
		
			InterfaceContext contextElement = null;
			int id;
			dataReader = new InputStreamReader( ClassLoader.getSystemResourceAsStream(fileName));
			
			try {
	    	
				myParser.setInput(dataReader);
				myParser.nextTag();
				myParser.nextTag();
				System.out.println( "nume " + myParser.getName());
			
				id = Integer.parseInt( myParser.nextText());
				myParser.nextTag();
				System.out.println( myParser.getName());
				type = myParser.nextText();
			
			
				// contextValue tag
				myParser.nextTag();
				System.out.println( myParser.getName());
			
				switch(type)
				{
					case "Time":
					
						int hour, minute;
					
						myParser.nextTag();
						hour = Integer.parseInt( myParser.nextText());
						myParser.nextTag();
						minute = Integer.parseInt( myParser.nextText());
					
						System.out.println(minute + " " + hour);
						contextElement = new TemporalContext(hour, minute);
					
						break;
				
					case "Location":
					
						double lon, lat;
					
						myParser.nextTag();
						lat = Double.parseDouble( myParser.nextText().replace("\"", ""));
						myParser.nextTag();
						lon = Double.parseDouble( myParser.nextText().replace("\"", ""));
					
					
						System.out.println(lon + " " + lat);
						contextElement = new LocationContext(lon, lat);
					
						break;
					
					case "Velocity":
					
						int speed;
					
						myParser.nextTag();
						speed = Integer.parseInt( myParser.nextText());
					
						System.out.println(speed);
						contextElement = new VelocityContext(speed);
					
						break;
						
					case "Temperature":
						
						String temperature;
						
						myParser.nextTag();
						temperature =  myParser.nextText();
					
						System.out.println(temperature);
						contextElement = new TemperatureContext(temperature);
					
						break;
				}
				
				// </contextValue>
				myParser.nextTag();
				System.out.println(myParser.getName());
			
				contextElement.setM_data( parseMetadata(myParser));
				contextElement.setId(id);
			
				//</contextElement>
				myParser.nextTag();
				System.out.println(myParser.getName());
			
			
				contextElements.add(contextElement);
			} 
			catch (XmlPullParserException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return contextElements;

	}
	
	/**
	 * has the purpose of obtaining the metadata of the context element
	 * @param myParser : instance used to read the xml file
	 * @return : the metadata of the context element object
	 */
	public MetadataContext parseMetadata(KXmlParser myParser)
	{
		MetadataContext metadata = new MetadataContext();
		
		//<contextMetadata>
		try {
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			//<description>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			metadata.setM_description( myParser.nextText());
			
			//<positionInfo>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			metadata.setM_latitude( Double.parseDouble( myParser.getAttributeValue(0)));
			metadata.setM_longitude( Double.parseDouble( myParser.getAttributeValue(1)));
			
			//</positionInfo>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			//<temporalInfo>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			
			metadata.setM_time( myParser.nextText());
			
			//<version>
			myParser.nextTag();
			metadata.setM_time( myParser.nextText());
			
			
			//<unit>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			metadata.setM_unit(myParser.nextText());
			
			//</contextMetadata>
			myParser.nextTag();
			System.out.println(myParser.getName());
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return metadata;
		
	}
}
