package AALComponent;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Notifications;
import ContextModel.InterfaceContext;
import ContextModel.LocationContext;
import ContextModel.MetadataContext;
import ContextModel.TemperatureContext;
import ContextModel.TemporalContext;
import ContextModel.VelocityContext;

/**
 * the concrete creator that will produce a context element
 * @author Vlad Herescu
 *
 */
public class FileSystemParser extends Parser{
	
	 /**
	 * object that reads the XML file and parse it into a Document instance
	 */
	DocumentBuilder builder;
	
	

	/**
	 * instantiating the DocumentBuilder
	 */
	public FileSystemParser() {
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
	
		try {
				builder =  factory.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) 
		{
				
				e.printStackTrace();
		}
		m_fileNames = new ArrayList<String>();
		m_fileNames.add("xml/time_context_element.xml");
		m_fileNames.add("xml/location_context_element.xml");
		m_fileNames.add("xml/velocity_context_element.xml");
		m_fileNames.add("xml/temperature_context_element.xml");
		

	}

	
	@Override
	public ArrayList<Notifications> factoryMethod() {
		
		String idValue, keyValue;
		InterfaceContext contextElement = null;
		ArrayList<Notifications> contextElements = new ArrayList<Notifications>();
		for(String fileName : m_fileNames)
		{
		
			try {
				Document document =builder.parse(
						ClassLoader.getSystemResourceAsStream(fileName));
				Node nodeId, nodeKey, nodeValue, nodeMetadata;

				Element root = document.getDocumentElement();
				nodeId = root.getElementsByTagName("contextId").item(0);
			
			
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
					
					case "Temperature" :
						
						String temperature;
					
						NodeList temp = ((Element) nodeValue).getElementsByTagName("value");
						temperature = getValueFromNode(temp, 0).replace("\"", ""); 
						contextElement = new TemperatureContext(temperature);
					
					break;
					
					
			}
			
				nodeMetadata  = root.getElementsByTagName("contextMetadata").item(0);
				contextElement.setM_data(parseMetadata(nodeMetadata));
				
				nodeId = root.getElementsByTagName("contextId").item(0);
				idValue = nodeId.getChildNodes().item(0).getNodeValue();
				contextElement.setId(Integer.parseInt(idValue));

			
			} catch (SAXException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			contextElements.add(contextElement);
		}
		return contextElements;
		
	}


	/**
	 * @param _nodeMetadata : contains the father node where the metadata is extracted from
	 * @return : the metadata of the current context element 
	 */
	public MetadataContext parseMetadata(Node _nodeMetadata)
	{	
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
		
		return metadata;
	}
	
	/**
	 * @param _node : returns the attributes of a node
	 * @return : an Arraylist containing the coordinates of the context element
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
	

}
