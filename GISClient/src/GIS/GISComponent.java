package GIS;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.io.*;
import java.lang.reflect.Method;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Notifications;
import ContextManagementComponent.ContextSituation;
import GeoObject.GeoObject;
import GeoObject.POIObject;
import Mediator.ComponentIf;
import Mediator.TypesNotification;
import Mediator.Subject;
import RulesParsing.DemoCompiler;
import RulesParsing.ParseException;
import RulesParsing.RuleObject;
import TreeNodeHierarchy.TreeNode;

/** 
 * A class organizing the UI of this GIS client
 */

public class  GISComponent 
  extends    WindowAdapter
  implements ActionListener,
             MouseListener,
             MouseMotionListener,
             KeyListener, ItemListener,
             ComponentIf
             
{
	/// The UI is in zoom mode
	public static final int ZOOM      = 0;
	/// The UI is in drag/pan mode
	public static final int DRAG      = 1;
	/// The UI is in select mode
	public static final int SELECT    = 2;
	public static final int UNDEFINED = 666;
	
	/// member variable storing the current UI mode
	private int m_modus = ZOOM;

	/// the frame representing the UI
	private Frame        m_frame        = null;
	
	/// the drawing canvas 
	public static DrawingPanel m_drawingPanel = new DrawingPanel();
	
	/// store a specific interaction start point
	private Point     m_startPoint      = null;
	/// store a specific interaction end point
	private Point     m_stopPoint       = null;
	
	/**
	 * the location of the user on the map
	 */
	Point	 m_user_current_location = null;
	
	/// store the current zoom rectangle 
	private Rectangle m_zoomRectangle   = null;
	/// store the current sticky rectangle (boundary for loading objects)
	private Rectangle m_stickyRectangle = null;
	
	//
	// some buttons to interact with
	//
	private Button m_ldButton     = new Button("LD");
	private Button m_ztfButton    = new Button("ZTF");
	private Button m_zinButton    = new Button("+");
	private Button m_zoutButton   = new Button("-");
	private Button m_upButton     = new Button("N");
	private Button m_downButton   = new Button("S");
	private Button m_leftButton   = new Button("W");
	private Button m_rightButton  = new Button("E");
	private Button m_poiButton    = new Button("POI");
	
	private TextField m_scale = new TextField("1:unknown");
	
	/**
	 * associate for every server a Check-box with the name being the key of the server name
	 */
	HashMap<String,GeoServerInterface> m_mapOptionsServer;
	
	/**
	 * loads the methods of the current interface to which the use has check his credentials
	 */
	GeoServerInterface m_currentInterface;
	
	/**
	 * the panel where the map is drawn
	 */
	public Panel m_panel;
	
	
	/**
	 * instance of the Mediator, used to communicate with the other components
	 */
	Subject m_subject; 
	
	
	/**
	 * the notifications the gis component is interested in
	 */
	static HashMap<TypesNotification, ArrayList<Notifications>> m_notifications;
	
	/**
	 *  associate for each condition an action
	 */
	ArrayList<RuleObject> m_GISrules;


	/**
	 * @param _subject
	 */
	public GISComponent(Subject _subject, HashMap<String,GeoServerInterface> _servers) {
	  
		m_mapOptionsServer = _servers;
		m_panel = initGUI();
		m_notifications = initNotifications();
		m_subject = _subject;
		m_GISrules = initConditionsActions();

		new CoreData();
	}
  
	
	/** reads the GISRules file to parse the rules and the actions
	 * @return : the map which associates each action to a condition
	 */
	public ArrayList<RuleObject> initConditionsActions() {
		
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		int indexStatement;
		ArrayList<RuleObject> rules = new ArrayList<RuleObject>();
		DemoCompiler rulesCompiler = new DemoCompiler();
		
		try {
				builder =  factory.newDocumentBuilder();
				document =builder.parse(
						ClassLoader.getSystemResourceAsStream("rules/GISRules.xml"));
				
				Element root = document.getDocumentElement();
				NodeList statements = root.getElementsByTagName("statement");
				System.out.println(statements.getLength());
				
				for(indexStatement = 0; indexStatement < statements.getLength(); indexStatement++)
				{
					Node node = statements.item(indexStatement);
					Node condition = ((Element) node).getElementsByTagName("condition").item(0);
					Node action = ((Element) node).getElementsByTagName("action").item(0);
					Node actionReverse = ((Element) node).getElementsByTagName("action").item(1);
					
					String conditionString = condition.getFirstChild().getNodeValue();
					String actionString = action.getFirstChild().getNodeValue();
					String reverseString = actionReverse.getFirstChild().getNodeValue();
					
					System.out.println(actionString + " " + reverseString);
					
				    conditionString = conditionString.replaceAll("\n", "");
					
					
					Method methodAction = this.getClass().getMethod(actionString, null);
					Method reverseAction =  this.getClass().getMethod(reverseString, null);
				

						
					TreeNode tree = rulesCompiler.evaluate(conditionString);
					rules.add(new RuleObject(tree, methodAction,reverseAction, conditionString,this));
					
					
					
				}
				
				
		} 
		catch (ParserConfigurationException e) 
		{	
				e.printStackTrace();
		}
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return rules;
	}


	private HashMap<TypesNotification, ArrayList<Notifications>> initNotifications() {
		HashMap<TypesNotification, ArrayList<Notifications>> notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
		
		notifications.put(TypesNotification.POI_OBJECT, new ArrayList<Notifications>());
		notifications.put(TypesNotification.CONTEXT_SITUATION, new ArrayList<Notifications>());
		return notifications;
	}
	

	/**
   * Initialize UI in one Panel
   */
  public Panel initGUI() {
	Panel gui = new Panel(new BorderLayout());
    Panel buttonBar = initButtonBar();
    Panel optionsMap	= initOptionsServer();
    gui.add(m_drawingPanel, BorderLayout.CENTER);
    gui.add(buttonBar,      BorderLayout.SOUTH);
    gui.add(optionsMap, BorderLayout.NORTH);
    m_drawingPanel.addMouseListener(this);
	m_drawingPanel.addMouseMotionListener(this); 
	return gui;
  }

  
  /**
   * creates the options for the server added from the xml file
   * @return : the panel with the options for the panels avaiblable
   */
  public Panel initOptionsServer() {
	
	  Panel options = new Panel(new FlowLayout());
	  CheckboxGroup cg1 = new CheckboxGroup ();     
	  
	  for(String server : m_mapOptionsServer.keySet())
	  {
		  Checkbox checkBox = new Checkbox(server,cg1, true);
		  options.add(checkBox);
		  checkBox.addItemListener(this);
	  }

	  
	  return options;
  }

  /**
   * Initialize button bar part of the UI
   */
  public Panel initButtonBar() {
  	Panel bar = new Panel(new FlowLayout());
  	bar.add(m_ldButton);
  	bar.add(m_ztfButton);
  	bar.add(m_zinButton);
  	bar.add(m_zoutButton);
  	
  	Panel scroll = new Panel(new FlowLayout());
  	scroll.add(m_leftButton);
  	Panel updownPanel = new Panel(new GridLayout(2,1));
  	updownPanel.add(m_upButton);
  	updownPanel.add(m_downButton);
  	scroll.add(updownPanel);
  	scroll.add(m_rightButton);
  	bar.add(scroll);
  	bar.add(m_scale);
  	bar.add(m_poiButton);
	Panel sdePanel = new Panel(new GridLayout(2,1));
  	bar.add(sdePanel);
  	
	m_ldButton.addActionListener(this);
	m_ztfButton.addActionListener(this);
	m_zinButton.addActionListener(this);
	m_zoutButton.addActionListener(this);
	m_upButton.addActionListener(this);
	m_downButton.addActionListener(this);
	m_leftButton.addActionListener(this);
	m_rightButton.addActionListener(this);
	m_poiButton.addActionListener(this);

	
	m_scale.addKeyListener(this);
  	
  	return bar;
  }

	public void windowClosing(WindowEvent _event) {
		System.exit(0);
	}
	
	public void mouseEntered(MouseEvent _event) {}
	public void mouseExited(MouseEvent _event) {}
	
	// user presses a mouse button "down"
	public void mousePressed(MouseEvent _event) {
		// if click count > 2 mode Select
		// if left mouse clicked(BUTTON1_MASK)  mode Zoom
		// if right mouse clicked(BUTTON3_MASK) mode Drag
		m_startPoint = _event.getPoint();
		if (_event.getClickCount() >= 2) {
			m_modus = SELECT;
		} else {
		  switch (_event.getModifiers()) {
        case MouseEvent.BUTTON1_MASK : { 
        	m_modus = ZOOM; 
        	m_drawingPanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } break;
			  case MouseEvent.BUTTON3_MASK : { 
			  	m_modus = DRAG; 
			  	m_drawingPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			  } break;
			  default : {} 
	    } // switch
    }
    if (_event.isControlDown())
      addToClipboard(getClipboardString(m_drawingPanel.getMapPoint(m_startPoint)));
    else
      copyToClipboard(getClipboardString(m_drawingPanel.getMapPoint(m_startPoint)));
  }

	/*
	 * the user releases the mouse to zoom an area
	 */
	public void mouseReleased(MouseEvent _event) {
		m_stopPoint       = _event.getPoint();
		int       delta   = 6; // 6 dots as delta ... if distance is greater than a drag movement has been detected
		boolean   zittern = false;
		Rectangle test    = new Rectangle(m_startPoint.x - (delta >> 1),
		                                  m_startPoint.y - (delta >> 1),
		                                  delta,
		                                  delta);
		if (! test.contains(m_stopPoint.x, m_stopPoint.y)) {
			zittern = true;
		}
		
		switch (m_modus) 
		{
			case ZOOM : 
			{
				if (zittern) 
				{ // a zoom rectangle has been defined
					
					System.out.println("A FACUT ZOOM " + m_startPoint.x + " " + m_startPoint.y +  " " + m_stopPoint.x + " " + m_stopPoint.y); 
					
					Point p1 = m_drawingPanel.getMapPoint(m_startPoint);
					Point p2 = m_drawingPanel.getMapPoint(m_stopPoint);
					Rectangle mapBounds = new Rectangle(Integer.MAX_VALUE,
					                                    Integer.MAX_VALUE,
					                                    Integer.MIN_VALUE,
					                                    Integer.MIN_VALUE);
					
					System.out.println(p1.x + " " + p1.y +  " " + p2.x + " " + p2.y);
					
					mapBounds.add(p1);
					mapBounds.add(p2);
					m_drawingPanel.zoomToFit(mapBounds);
					m_drawingPanel.repaint();
					int scale = m_drawingPanel.calculateScale();
					m_scale.setText(modifyScaleText(String.valueOf(scale)));
				}
			} 
			break;
			case DRAG : 
			{
			    // a drag movement has been initiated
				int deltaX = - m_startPoint.x + m_stopPoint.x;
				int deltaY = - m_startPoint.y + m_stopPoint.y;
			  m_drawingPanel.scrollHorizontal(deltaX);
			  m_drawingPanel.scrollVertical(deltaY);
	  	  m_drawingPanel.repaint();
			}
			break;
			case SELECT :
			{
				if (! zittern) {
					GeoObject[] select = m_drawingPanel.initSelection(m_stopPoint);
					if (select != null) {
						for (int i = 0 ; i < select.length ; i++){
							System.out.println(i + ". selected object --> " + select[i]);
						} // for i
					} // if select
				} // if zittern
			} break;
		} // switch
	  m_drawingPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	  m_zoomRectangle = null;
	}

	public void mouseClicked(MouseEvent _event) {

	}
	
	// mouse has been moved while pressing and holding the mouse button
	public void mouseDragged(MouseEvent _event) {
		if (m_modus == ZOOM) 
			generateZoomRect(_event.getPoint()); // draw the zoom rectangle
	}
	public void mouseMoved(MouseEvent _event) {}
	
	public void keyPressed(KeyEvent _event)  {}
	
	/*
	 * used when the user zooms an area 
	*/
	public void keyReleased(KeyEvent _event) {
		if (_event.getKeyCode() == KeyEvent.VK_ENTER) 
		{
			
			String scaleS = m_scale.getText();
			m_drawingPanel.zoomToScale(scanScaleText(scaleS));
			int scale = m_drawingPanel.calculateScale();
			m_scale.setText(modifyScaleText(String.valueOf(scale)));
			m_drawingPanel.repaint();
		} // if VK_ENTER
	}
	public void keyTyped(KeyEvent _event)    {}
	
	/**
	 * React to the button pressed events ... 
	 */
	public void actionPerformed(ActionEvent _event) {
		Object source = _event.getSource();

		
		if (source.equals(m_ldButton)) {

			SpecifyData dialog = new SpecifyData(m_frame,m_currentInterface);
			dialog.setVisible(true);

		}
		else if (source.equals(m_ztfButton)) {
			m_drawingPanel.zoomToFit();
			int scale = m_drawingPanel.calculateScale();
			m_scale.setText(modifyScaleText(String.valueOf(scale)));
		} 
		else if (source.equals(m_zinButton)) {
			m_drawingPanel.zoom(1.2);
			int scale = m_drawingPanel.calculateScale();
			m_scale.setText(modifyScaleText(String.valueOf(scale)));
		}
		else if (source.equals(m_zoutButton)) {
      m_drawingPanel.zoom(1/1.2);
			int scale = m_drawingPanel.calculateScale();
			m_scale.setText(modifyScaleText(String.valueOf(scale)));
		} else if (source.equals(m_upButton)) {
      m_drawingPanel.scrollVertical(-20);
		} else if (source.equals(m_downButton)) {
      m_drawingPanel.scrollVertical(20);
		} else if (source.equals(m_leftButton)) {
      m_drawingPanel.scrollHorizontal(-20);
		} else if (source.equals(m_rightButton)) {
      m_drawingPanel.scrollHorizontal(20);
		} else if (source.equals(m_poiButton)) {
			m_drawingPanel.initPOI();
	  }  else {
			System.out.println("Event-Producer UNKNOWN!!!");
		}
		m_drawingPanel.repaint();
	}



	/**
	 * Stores the current data visualized in the canvas in a predefined file
	 */
  public void storeImage() {
  	try {
		  String[] temp = ImageToBitmap.getImageTypes();
		  for (int i = 0 ; i < temp.length ; i++) {
		  	System.out.println(i + ". --> " + temp[i]);
		  } // for i	
		
  		BufferedImage image = 
  		  ImageToBitmap.getImage(m_drawingPanel.getWidth(), 
  														 m_drawingPanel.getHeight());
  		Graphics g = image.getGraphics();
  	  g.setColor(Color.WHITE);
      g.fillRect(0, 0, m_drawingPanel.getWidth(), m_drawingPanel.getHeight());
      
      m_drawingPanel.paint(g);
 
  		g.dispose();
  		
  		String fileType = "png";  		
  		FileOutputStream out = new FileOutputStream( new File( "Test-II." + fileType ) );
			ImageToBitmap.storeImage(image,out,ImageToBitmap.getImageType(fileType));
			out.flush();
			out.close();
		} catch (Exception _e) { _e.printStackTrace(); }
  }

  /**
   * Loads the data from the SDE server ...
   */
  /*private void loadData() {
		SDEServer server = new SDEServer("Remo", "geo_user", "user");
		Vector<GeoObject> objectContainer = server.loadData();
	    m_drawingPanel.setGeoObjects(objectContainer);   
  }*/
  

  private void loadData()
  {
	  OSMServer server = new OSMServer("gisuser","gisuser");
	  Vector<GeoObject> objectContainer = server.typeAreaQuery(null, new Rectangle(15, 49, 2, 1)); 
	  m_drawingPanel.setGeoObjects(objectContainer);   
  }

  
  public String modifyScaleText(String _scale) {
  	return "1 : " + _scale;
  }
  
  public int scanScaleText(String _scale) {
  	int index = _scale.indexOf(":");
  	if (index != -1) {
  		String tmp = (_scale.substring(index + 1,_scale.length())).trim();
  		return Integer.parseInt(tmp);
  	}
  	return Integer.parseInt(_scale.trim());
  }

  /**
   *  Draws the rectangle in XOR mode onto the canvas with a distinct color 
   * @param _rect : the rectangle created by the position where the user started to create the rectangle
   * 				and the current position of the mouse
   * @param _color : the color used to color the lines of the rectangle
   */
   public void drawXORRectangle(Rectangle _rect, Color _color) {
    if (_rect != null) {
      Graphics g = m_drawingPanel.getGraphics();
      g.setXORMode(_color);
      g.drawRect(_rect.x,
		     _rect.y,
		     _rect.width,
		     _rect.height);
      g.setPaintMode();
    }
  }

  /**
   *  moves the active zoom point to new position ...
   *  a new zoom rectangle is drawn, the old one is erased
   * @param _point : the current position of the mouse cursor
   */
  public void generateZoomRect(Point _point) {

    if (m_startPoint != null) 
    {
    	
    	Color col = new Color(Color.red.getRGB() ^ m_drawingPanel.getBackground().getRGB());
    	if (m_zoomRectangle != null)
    		drawXORRectangle(m_zoomRectangle, col);
    	
    	m_zoomRectangle = new Rectangle(m_startPoint);
    	m_zoomRectangle.add(_point);
    	drawXORRectangle(m_zoomRectangle, col);
    }
  }

  /**
   * copies data to the clipboard
   */
  public void copyToClipboard(String _text) {
    try {
      Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
      // StringSelection ss = new StringSelection(toCopy);
      cb.setContents(new StringSelection(_text), 
                     new StringSelection(_text));
    } catch (java.security.AccessControlException sec_e) {
      System.out.println(
        "Zugriff auf Clipboard nicht erlaubt.\nBITTE java.policy ANPASSEN.");
    } catch (Exception _e) {
      _e.printStackTrace();
    }
  }
   
   /**
    * attach data to the current cliboard
	*/
   public void addToClipboard(String _text) {
    try {
      Clipboard    cb      = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable t       = cb.getContents(this);
      String       oldClip = (String)t.getTransferData(DataFlavor.stringFlavor);
      oldClip += "\n";
      copyToClipboard(oldClip+_text);
    } catch (Exception _e) {
      _e.printStackTrace();
    }
  }  // addToClip

  
  public String getClipboardString(Point _pt) {
  	return _pt.x + " " + _pt.y;
  }


  @Override
  public void itemStateChanged(ItemEvent e) {

  	
  	
  /*   if(m_mapOptionsServer.get(e.getItem()).getPassword() == null || m_mapOptionsServer.get(e.getItem()).getUsername() == null) 
     {
    	 CheckCredentialsDialog dialog = new CheckCredentialsDialog(m_frame, m_mapOptionsServer.get(e.getItem()));
    	 dialog.setVisible(true);
    	 
     }
  */
	  
	  Checkbox check = (Checkbox) e.getSource();
	  if(check.getLabel().equals("OSMServer"))
	  {
		  m_mapOptionsServer.get(e.getItem()).checkCredentials("gisuser","gisuser");
		  m_currentInterface = m_mapOptionsServer.get(e.getItem());
		  List<String> selectedItems =m_currentInterface.getTypes();
		  Vector<GeoObject> objectContainer = m_currentInterface.typeAreaQuery(selectedItems, null); 
		  GISComponent.m_drawingPanel.setGeoObjects(objectContainer); 
			
		  m_drawingPanel.zoomToFit();
		  int scale = m_drawingPanel.calculateScale();
		  m_scale.setText(modifyScaleText(String.valueOf(scale)));
		  m_drawingPanel.repaint();
		
	  }
	  else
      m_currentInterface = m_mapOptionsServer.get(e.getItem());

	  
  }

  @Override
  public Panel getPanel() {
	  // TODO Auto-generated method stub
	  return m_panel;
  }

  @Override
  public void update() {
	  // TODO Auto-generated method stub	
  }

   @Override
   public Set<TypesNotification> getNotificationsTypes() {
		return m_notifications.keySet();
  }

  @Override
  public void update(TypesNotification _notification) {
	  
	  
	 if(m_notifications.keySet().contains(_notification))
	 {
		 
		 switch(_notification)
		 {
		 	case CONTEXT_SITUATION: 
		 		checkMethodsToExecute( m_subject.getNotifications(TypesNotification.CONTEXT_SITUATION).get(0));
		 		break;
		 	case POI_OBJECT:
		 		
		 		POIObject object = (POIObject) m_subject.getNotifications(TypesNotification.POI_OBJECT).get(0);
		 		
		 		System.out.println("userul se afla in locul " + object.m_point.x + " " + object.m_point.y);
		 		m_user_current_location = DrawingPanel.m_matrix.multiply(object.m_point);	
		 		
		 		
		 		
		 		m_notifications.get(TypesNotification.POI_OBJECT).clear();
		 		m_notifications.get(TypesNotification.POI_OBJECT).addAll(m_subject.getNotifications(TypesNotification.POI_OBJECT));
		 		
		 		break;
		 }
		 if(m_subject.getPanel() == this.m_panel)
			 m_drawingPanel.repaint();
		 
	 }
	 
	
  }
  
  /**
 * @param _notification : the current context received from context management
   *  
   */
  public void checkMethodsToExecute(Notifications _notification) {
	
	  boolean conditionChecked;
	  ContextSituation situation = (ContextSituation) _notification;
	  
	  
	  for(RuleObject rule : m_GISrules)
	  {
		  conditionChecked = rule.valid(situation);
		  if(conditionChecked != rule.isM_lastConditionState())
		  {
			  rule.setM_lastConditionState(conditionChecked);
			  rule.execute();
		  }
	  }
	
  }


  /**
   * action called when updating, if the condition is fulfilled
   */
  public void changeBackground()
  {
	  System.out.println("schimba backgroundul HA HA HA");
	  m_drawingPanel.drawing.color = new Color(255,240,240);
	  m_drawingPanel.drawing.m_imageCreated = false;
  
  }
  /**
   * action called when updating, if the condition is fulfilled
   */
  public void resetBackground()
  {
	  System.out.println("schimba backgroundul HA HA HA");
	  m_drawingPanel.drawing.color = new Color(0,0,0);
	  m_drawingPanel.drawing.m_imageCreated = false;
  }
  
  /**
  * action called when the temperature is too little and the velocity is too high 
  */
  public void zoomIn()
  {
	  
	//   System.out.println("FACE ZOOM de 2");
	//   m_drawingPanel.zoom(2);
	//   int scale = m_drawingPanel.calculateScale();
	//   m_scale.setText(modifyScaleText(String.valueOf(scale)));
	//   m_drawingPanel.drawing.m_imageCreated = false;
	  
	  System.out.println("intra aici");
	   
	  
	   int x = m_user_current_location.x;
	   int y = m_user_current_location.y;
	   
		Point p1 = m_drawingPanel.getMapPoint(new Point(x- 50, y - 50));
		Point p2 = m_drawingPanel.getMapPoint(new Point(x + 50, y + 50));
		Rectangle mapBounds = new Rectangle(Integer.MAX_VALUE,
		                                    Integer.MAX_VALUE,
		                                    Integer.MIN_VALUE,
		                                    Integer.MIN_VALUE);
		mapBounds.add(p1);
		mapBounds.add(p2);
		m_drawingPanel.drawing.m_imageCreated = false;
		m_drawingPanel.zoomToFit(mapBounds);

		int scale = m_drawingPanel.calculateScale();
		m_scale.setText(modifyScaleText(String.valueOf(scale)));
		
		
  }
  
  /**
  * action called when there is no concern regarding user's velocity
  */
  public void zoomOut()
  {
		m_drawingPanel.zoomToFit();
		int scale = m_drawingPanel.calculateScale();
		m_scale.setText(modifyScaleText(String.valueOf(scale)));
		m_drawingPanel.drawing.m_imageCreated = false;
  }
  
}

/**
 * the user specifies which types should be shown on the map : rivers, roads, 
 * @author Vlad Herescu
 *
 */
class SpecifyData extends Dialog implements ActionListener
{

	/**
	 * used to let the user specify the types to be shown, and which tables
	 */
	GeoServerInterface m_currentInterface;
	DefaultListModel model;
	
	JList list;
	
	Label _labelTable;
	Label _labelTypes;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	/**
	 * @param parent : used to call the super method
	 * @param _currentInterface : the server chosen by the user
	 */
	public SpecifyData(Frame parent, GeoServerInterface _currentInterface) 
	{
		  super(parent, true); 
		  m_currentInterface = _currentInterface;

		  
		  setLayout(new BorderLayout());
		  JButton buttonShowMap = new JButton("ShowMap");

		  
		  if(m_currentInterface.getPassword() != null && m_currentInterface.getUsername() != null)
		  {
			  model = new DefaultListModel();
			  list = new JList(model);
			  
			  JScrollPane pane = new JScrollPane(list);
			  
			  for (String type : m_currentInterface.getTypes())
				  model.addElement(type);
			  
			  add(pane, BorderLayout.NORTH);
		  }
		  else
		  {
			  JLabel label = new JLabel("the user is not identified");
			  add(label);
			  buttonShowMap.setText("Close");
			  
		  }
		  buttonShowMap.addActionListener(this);
		  


		  add(buttonShowMap, BorderLayout.WEST);    
		  
		  pack();
		  

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("ShowMap"))
		{
			List<String> selectedItems =  list.getSelectedValuesList();
			Vector<GeoObject> objectContainer = m_currentInterface.typeAreaQuery(selectedItems, null); 
			GISComponent.m_drawingPanel.setGeoObjects(objectContainer); 
		}
		  dispose();
		
	}

}


/**
 * 
 * 
 * @author  Vlad Herescu
 *
 */
class CheckCredentialsDialog extends Dialog implements ActionListener
{
	/**
	 * I don't know wht is this for
	 */
	private static final long serialVersionUID = 1L;
	static int H_SIZE = 200;
	static int V_SIZE = 200;
	
	TextField _textFieldUsername;
	TextField _textFieldPassword;
	
	Label _labelUsername;
	Label _labelPassword;
	GeoServerInterface _server;

	public CheckCredentialsDialog(Frame parent, GeoServerInterface server)
	{

         super(parent, true); 
         Panel p = new Panel(); 
         
        
         _server = server;
 		_textFieldUsername = new TextField();
 		_textFieldPassword = new TextField();
 		_labelPassword = new Label("Password");
 		_labelUsername = new Label("Username");
 		
 		_textFieldPassword.setColumns(30);
 		_textFieldUsername.setColumns(30);
         
		setBackground(Color.gray);
		setLayout(new BorderLayout());
		
		// Two buttons "Close" and "Help"
		p.add(_labelUsername);
		p.add(_labelPassword);
		p.add(_textFieldUsername);
		p.add(_textFieldPassword);
		
		Button login = new Button("Login");
		Button close = new Button("Close");
		
		login.addActionListener(this);
		close.addActionListener(this);
		
		p.add(login);
		p.add(close);
		
        add("South", p);
		pack();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// If action label(i.e arg) equals 
				// "Close" then dispose this dialog
		
		String arg = e.getActionCommand();
		System.out.println(arg);
		if(arg.equals("Login"))
		{
		        	
		     if( _server.checkCredentials(
		        _textFieldUsername.getText(),_textFieldPassword.getText()) == true)
		         dispose();

		}
		 if(arg.equals("Close"))
		 {
		   dispose();

		}

		
	}

}