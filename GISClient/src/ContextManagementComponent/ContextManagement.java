package ContextManagementComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.LabelView;

import common.Notifications;
import ContextModel.ElementType;
import ContextModel.LocationContext;
import ContextModel.TemporalContext;
import Mediator.ComponentIf;
import Mediator.Subject;
import Mediator.TypesNotification;

/**
 * component that will manage the context elements received from different packages : GPS, ...
 * @author Vlad Herescu
 *
 */
public class ContextManagement  implements ComponentIf, ChangeListener, ActionListener{

	/**
	 * the instance of the mediator, that will share all the data from each observer 
	 */
	Subject m_subject;
	
	/**
	 * the context elements received from the parser
	 */
	ArrayList<Notifications> m_contextElements;
	
	
	/**
	 * the panel where the data will be shown
	 */
	Panel m_panel;
	
	/**
	 * the notifications the gis component is interested in
	 */
	static HashMap<TypesNotification, ArrayList<Notifications>> m_notifications;
	
	
	/**
	 * label where the current time is shown
	 */
	static JLabel labelTimeCurrent;
	
	/**
	 * label where the frequency to update the map is shown
	 */
	JLabel labelFrequency;
	
	/**
	 * label where the velocity of the user is shown
	 */
	JLabel labelVelocity;
	
	
	/**
	 * text field where the latitude is shown;
	 */
	JTextField textFieldLatitude;
	
	
	/**
	 * text field where the longitude is shown;
	 */
	JTextField textFieldLongitude;
	
	/**
	 * slider where the user can select the frequency to update the map
	 */
	JSlider sliderFrequency;
	
	/**
	 *  slider where the user can select the velocity of the map
	 */
	JSlider sliderVelocity;
	
	
	/**
	 * @param _subject : the instance of Mediator, to which the data will be transmitted 
	 */
	public ContextManagement(Subject _subject) {
		m_subject = _subject;
		m_contextElements = new ArrayList<>();
		m_panel = initGUI();
		m_notifications = initNotificationsInterested();
	}
	
	private HashMap<TypesNotification, ArrayList<Notifications>> initNotificationsInterested() {
		HashMap<TypesNotification, ArrayList<Notifications>> notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
		
		notifications.put(TypesNotification.CONTEXT_ELEMENT, new ArrayList<Notifications>());
		return notifications;
	}
	
	/**
	 * Initialize UI in one Panel
	 * @return : the main panel containing the other panels
	*/
	public Panel initGUI() {
		
		Panel gui = new Panel(new GridLayout(6,1));
		gui.add(initPanelNightDay());
		gui.add(initPanelFrequency());
		gui.add(initPanelVelocity());
		gui.add(initPosition());
		gui.add(initButton());
		return gui;
	}
	

	/**
	 * @return : the button for sending the Context Situation
	 */
	public Component initButton() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Simulate Context");
		button.addActionListener(this);
		panel.add(button);
		return panel;
	}

	/**
	 * @return : the panel where 
	 */
	public JPanel initPosition() {
		
		JPanel panel = new JPanel(new GridLayout(2,2));
		textFieldLatitude = new JTextField();
		textFieldLongitude = new JTextField();
		
		textFieldLatitude.setEditable(false);
		textFieldLongitude.setEditable(false);
		
		textFieldLatitude.setBackground(Color.white);
		textFieldLongitude.setBackground(Color.white);
		
		panel.add(new JLabel("Latitude"));
		panel.add(textFieldLatitude);
		panel.add(new JLabel("Longitude"));
		panel.add(textFieldLongitude);
		
		return panel;
		
	}

	/**
	 * @return : the panel where the user can select the moment of the day : if 
	 * it is night or day
	 */
	public JPanel initPanelNightDay()
	{
		JPanel panel = new JPanel();

		JLabel labelTimeSelect =  new JLabel("Select night or day :  ");
		JLabel labelCurrentTime =  new JLabel("              Current time : ");
		labelTimeCurrent = new JLabel("day");
		
		JSwitchBox box = new JSwitchBox( "night", "day" );
		
		
		panel.add(labelTimeSelect);
		panel.add( box );
		panel.add(labelCurrentTime);
		panel.add(labelTimeCurrent);

		return panel;
	}
	
	/**
	 * @return : the panel where the user can select the frequency to send data to the user
	 */
	public JPanel initPanelFrequency()
	{

		JPanel panel = new JPanel();

		JLabel labelTimeSelect =  new JLabel("Select frequency (ms) :  ");
		JLabel labelCurrentTime =  new JLabel("              Current frequency : ");
		labelFrequency = new JLabel("25");
		
		sliderFrequency = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
	    sliderFrequency.setMinorTickSpacing(2);
	    sliderFrequency.setMajorTickSpacing(10);
	    sliderFrequency.setPaintTicks(true);
	    sliderFrequency.setPaintLabels(true);

		
		sliderFrequency.setLabelTable(sliderFrequency.createStandardLabels(10));
		sliderFrequency.addChangeListener(this);
		
		panel.add(labelTimeSelect);
		panel.add( sliderFrequency );
		panel.add(labelCurrentTime);
		panel.add(labelFrequency);

		return panel;
	}
	
	public JPanel initPanelVelocity()
	{
		JPanel panel = new JPanel();

		JLabel labelTimeSelect =  new JLabel("Select velocity (km/h) : ");
		JLabel labelCurrentTime =  new JLabel("              Current velocity : ");
		labelVelocity = new JLabel("25");
		
		sliderVelocity = new JSlider(JSlider.HORIZONTAL, 0, 80, 40);
	    sliderVelocity.setMinorTickSpacing(2);
	    sliderVelocity.setMajorTickSpacing(10);
	    sliderVelocity.setPaintTicks(true);
	    sliderVelocity.setPaintLabels(true);

		    // We'll just use the standard numeric labels for now...
		sliderVelocity.setLabelTable(sliderVelocity.createStandardLabels(40));
		sliderVelocity.addChangeListener(this);
		
		panel.add(labelTimeSelect);
		panel.add( sliderVelocity );
		panel.add(labelCurrentTime);
		panel.add(labelVelocity);

		return panel;
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
	public void update(TypesNotification _notification) {
		  
		  System.out.println("intra si aici, in Contextmanagement " + _notification);
		  
		 if(m_notifications.keySet().contains(_notification))
		 {
			 m_notifications.put(_notification, m_subject.getNotifications(_notification));
			 
			 
			ArrayList<Notifications> notifications =  m_subject.getNotifications(_notification);
			LocationContext location = (LocationContext) notifications.get(0);
			System.out.println( location.m_latitudeGrad + "  " + location.m_longitudeGrad);
			textFieldLatitude.setText(location.m_latitudeGrad + "" + location.m_latitudeMinuten);
			textFieldLongitude.setText(location.m_longitudeGrad + "" + location.m_longitudeMinuten);
			
			 
		 }
		 
		 System.out.println(m_notifications.get(_notification));
	  }

	@Override
	public Set<TypesNotification> getNotificationsTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		JSlider slider = (JSlider) e.getSource();
		if(slider == sliderFrequency)
			labelFrequency.setText(sliderFrequency.getValue()+"");
		if(slider == sliderVelocity)
			labelVelocity.setText(sliderVelocity.getValue()+"");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		System.out.println("Frequency: " + labelVelocity.getText() );
		System.out.println("Time: " + labelTimeCurrent.getText() );
	
		
		ContextSituation situation = new ContextSituation();
		ArrayList<Notifications> contextElements = new ArrayList<Notifications>();
		TemporalContext temporalContext = new TemporalContext(labelTimeCurrent.getText());
		
		
		situation.getM_contextData().put(ElementType.TIME_CTXT, temporalContext);
		
		
		contextElements.add(situation);
		m_subject.communicateContextSituation(contextElements);
		
	}

}





class JSwitchBox extends AbstractButton{
    private Color colorBright = new Color(220,220,220);
    private Color colorDark = new Color(150,150,150);
    private Color black  = new Color(0,0,0,100);
    private Color white = new Color(255,255,255,100);
    private Color light = new Color(220,220,220,100);
    private Color yellow = new Color(250,240,97);
    private Color blue = new Color(5,6,74);
    private Font font = new JLabel().getFont();
    private int gap = 5;
    private int globalWitdh = 0;
    private final String trueLabel;
    private final String falseLabel;
    private Dimension thumbBounds;
    private Rectangle2D bounds;
    private int max;
    boolean night;


    public JSwitchBox(String trueLabel, String falseLabel) {
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
        double trueLenth = getFontMetrics( getFont() ).getStringBounds( trueLabel, getGraphics() ).getWidth();
        double falseLenght = getFontMetrics( getFont() ).getStringBounds( falseLabel, getGraphics() ).getWidth();
        max = (int)Math.max( trueLenth, falseLenght );
        gap =  Math.max( 5, 5+(int)Math.abs(trueLenth - falseLenght ) ); 
        thumbBounds  = new Dimension(max+gap*2,20);
        globalWitdh =  max + thumbBounds.width+gap*2;
        setModel( new DefaultButtonModel() );
        setSelected( false );
        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseReleased( MouseEvent e ) {
                if(new Rectangle( getPreferredSize() ).contains( e.getPoint() )) {
                    setSelected( !isSelected() );
                }
            }
        });
        night = false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(globalWitdh, thumbBounds.height);
    }

    @Override
    public void setSelected( boolean b ) {
        if(b){
        	this.setForeground(new Color(0,0,0));
            setText( trueLabel );
            setBackground( blue );
            night = true;
        } else {
        	this.setForeground(new Color(0,0,0));
            setBackground( yellow );
            setText( falseLabel );
            night = false;
        }
        super.setSelected( b );
    }
    @Override
    public void setText( String text ) {
        super.setText( text );
    }

    @Override
    public int getHeight() {
        return getPreferredSize().height;
    }

    @Override
    public int getWidth() {
        return getPreferredSize().width;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    protected void paintComponent( Graphics g ) {
        g.setColor( getBackground() );
        g.fillRoundRect( 1, 1, getWidth()-2 - 1, getHeight()-2 ,2 ,2 );
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor( black );
        g2.drawRoundRect( 1, 1, getWidth()-2 - 1, getHeight()-2 - 1, 2,2 );
        g2.setColor( white );
        g2.drawRoundRect( 1 + 1, 1 + 1, getWidth()-2 - 3, getHeight()-2 - 3, 2,2 );

        int x = 0;
        int lx = 0;
        if(isSelected()) {
            lx = thumbBounds.width;
        } else {
            x = thumbBounds.width;
        }
        int y = 0;
        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g2.setPaint( new GradientPaint(x, (int)(y-0.1*h), colorDark , x, (int)(y+1.2*h), light) );
        g2.fillRect( x, y, w, h );
        g2.setPaint( new GradientPaint(x, (int)(y+.65*h), light , x, (int)(y+1.3*h), colorDark) );
        g2.fillRect( x, (int)(y+.65*h), w, (int)(h-.65*h) );

        if (w>14){
            int size = 10;
            g2.setColor( colorBright );
            g2.fillRect(x+w/2-size/2,y+h/2-size/2, size, size);
            g2.setColor( new Color(120,120,120));
            g2.fillRect(x+w/2-4,h/2-4, 2, 2);
            g2.fillRect(x+w/2-1,h/2-4, 2, 2);
            g2.fillRect(x+w/2+2,h/2-4, 2, 2);
            g2.setColor( colorDark );
            g2.fillRect(x+w/2-4,h/2-2, 2, 6);
            g2.fillRect(x+w/2-1,h/2-2, 2, 6);
            g2.fillRect(x+w/2+2,h/2-2, 2, 6);
            g2.setColor( new Color(170,170,170));
            g2.fillRect(x+w/2-4,h/2+2, 2, 2);
            g2.fillRect(x+w/2-1,h/2+2, 2, 2);
            g2.fillRect(x+w/2+2,h/2+2, 2, 2);
        }

        g2.setColor( black );
        g2.drawRoundRect( x, y, w - 1, h - 1, 2,2 );
        g2.setColor( white );
        g2.drawRoundRect( x + 1, y + 1, w - 3, h - 3, 2,2 );

        
        if(night == true)
        {
        	ContextManagement.labelTimeCurrent.setText("night");
        	g2.setColor(Color.white);
        }
        else
        {
        	ContextManagement.labelTimeCurrent.setText("day");
        	g2.setColor(Color.BLACK);
        }
        g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.setFont( getFont() );
        g2.drawString( getText(), lx+gap, y+h/2+h/4 );
    }
}
