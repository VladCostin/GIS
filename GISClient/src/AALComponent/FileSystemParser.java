package AALComponent;

import java.io.IOException;
import java.io.InputStream;



















import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;













import org.kxml2.io.KXmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import ContextModel.InterfaceContext;
import ContextModel.LocationContext;
import ContextModel.MetadataContext;
import ContextModel.TemporalContext;
import ContextModel.VelocityContext;

/**
 * the concrete creator that will produce a context element
 * @author Vlad Herescu
 *
 */
public class FileSystemParser extends Parser{
	
	 DocumentBuilder builder;
	
	
	public FileSystemParser() {
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
	//	DocumentBuilderFactoryImpl factory = new DocumentBuilderFactoryImpl();
	
		try {
				builder =  factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		
	}

	
	@Override
	public InterfaceContext factoryMethod() {
		
		String idValue, keyValue;
		InterfaceContext contextElement = null;
		
		
		try {
			Document document =builder.parse(
					 ClassLoader.getSystemResourceAsStream("xml/time_context_element.xml"));
			Node nodeId, nodeKey, nodeValue, nodeMetadata;
			
			

			Element root = document.getDocumentElement();
			nodeId = root.getElementsByTagName("contextId").item(0);
			idValue = nodeId.getChildNodes().item(0).getNodeValue();
			
			
			nodeKey = root.getElementsByTagName("contextKey").item(0);
			keyValue = nodeKey.getChildNodes().item(0).getNodeValue();

			
			nodeValue = root.getElementsByTagName("contextValue").item(0);
			
			switch(keyValue)
			{
				case "Location" :
					
					double lat, lon;
					
					NodeList position = ((Element) nodeValue).getElementsByTagName("value");
					lat = Double.parseDouble(getValueFromNode(position, 0).replace("\"", "")); 
					lon = Double.parseDouble(getValueFromNode(position, 1).replace("\"", ""));
					contextElement = new LocationContext(lat,lon); 
					
					break;
					
				case "Time" :
					
					int hour, minute;
					
					NodeList time = ((Element) nodeValue).getElementsByTagName("value");
					hour = Integer.parseInt(getValueFromNode(time, 0).replace("\"", ""));   
					minute = Integer.parseInt(getValueFromNode(time, 1).replace("\"", "")); 
					contextElement = new TemporalContext(hour, minute);
					
					break;
					
				case "Velocity" :
					
					int speed;
					
					NodeList velocity = ((Element) nodeValue).getElementsByTagName("value");
					speed = Integer.parseInt(getValueFromNode(velocity, 0).replace("\"", "")); 
					contextElement = new VelocityContext(speed);
					
					break;
					
					
			}
			
			nodeMetadata  = root.getElementsByTagName("contextMetadata").item(0);
			contextElement.setM_data(parseMetadata(nodeMetadata));
			
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contextElement;
		
	}


	/**
	 * @param _nodeMetadata : contains the father node where the metadata is extracted from
	 * @return : the metadata of the current context element 
	 */
	public MetadataContext parseMetadata(Node _nodeMetadata) {
		
		
		MetadataContext metadata = new MetadataContext();
		ArrayList<Double> location;
		
		metadata.setM_description( getValueFromNode( ((Element) _nodeMetadata).getElementsByTagName("description"),0));
		metadata.setM_time( getValueFromNode( ((Element) _nodeMetadata).getElementsByTagName("temporalInfo"),0));
		metadata.setM_unit( getValueFromNode( ((Element) _nodeMetadata).getElementsByTagName("unit"),0));
		metadata.setM_version( getValueFromNode( ((Element) _nodeMetadata).getElementsByTagName("version"),0));
		
		location =  getValuesAttributes(((Element) _nodeMetadata).getElementsByTagName("positionInfo"));
		metadata.setM_longitude(location.get(0));
		metadata.setM_latitude(location.get(0));
		
		
		System.out.println(metadata.getM_description());
		System.out.println(metadata.getM_time());
		System.out.println(metadata.getM_version());
		System.out.println(metadata.getM_unit());
		
		
		
		return null;
	}
	
	/**
	 * @param _node : returns the attributes of a node
	 * @return
	 */
	public ArrayList<Double> getValuesAttributes(NodeList _node)
	{
		NamedNodeMap map = _node.item(0).getAttributes();
		ArrayList<Double> attribtues = new ArrayList<Double>();
		
		
		attribtues.add(Double.parseDouble(  map.getNamedItem("lat").getNodeValue()));
		attribtues.add(Double.parseDouble(  map.getNamedItem("long").getNodeValue()));
		
		
		
		return attribtues;
	}
	
	
	/**
	 * @param _node : a node from the xml file
	 * @param _position : in case it is an array, extract element from this position
	 * @return : the value between tags : <>value</>
	 */
	public String getValueFromNode(NodeList _node,int _position)
	{
		
		return _node.item(_position).getChildNodes().item(0).getNodeValue();
	}
	
	/*
	@Override
	public InterfaceContext factoryMethod()
	{
		
		InterfaceContext contextElement = null;
		Reader dataReader = new InputStreamReader( ClassLoader.getSystemResourceAsStream("xml/velocity_context_element.xml"));
		KXmlParser myParser = new KXmlParser();
		String type;
		
	    try {
	    	// metadata of the context element
	    	
			myParser.setInput(dataReader);
			myParser.nextTag();
			myParser.nextTag();
			System.out.println( myParser.getName());
			
			System.out.println( myParser.nextText());
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
			}
			// </contextValue>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			 contextElement.setM_data( parseMetadata(myParser));
			
			//</contextElement>
			myParser.nextTag();
			System.out.println(myParser.getName());
			
			
			
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return contextElement;
	}
	*/
	
	/**
	 * has the purpose of obtaining the metadata of the context element
	 * @param myParser : instance used to read the xml file
	 * @return : the metadata of the context element object
	 */
	/*public MetadataContext parseMetadata(KXmlParser myParser)
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
			
			metadata.setM_latitude( myParser.getAttributeValue(0));
			metadata.setM_longitude( myParser.getAttributeValue(1));
			
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
		
	}*/

}
