package AALComponent;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Mediator.ComponentIf;

/**
 * the component which show the parsed data
 * @author Vlad Herescu
 *
 */
public class AALComponent implements ComponentIf, ActionListener{

	/**
	 * the panel where the data will be shown
	 */
	public Panel m_panel;
	
	Parser m_parser;
	
	public AALComponent() {
		m_panel = initGUI();
	}
	
	  /**
	   * Initialize UI in one Panel
	   */
	public Panel initGUI() {
		Panel gui = new Panel(new BorderLayout());
		JButton buton = new JButton("FileBrowser");
	    buton.addActionListener(this);
		gui.add(buton, BorderLayout.CENTER);
		return gui;
	}
	
	@Override
	public Panel getPanel() {
		// TODO Auto-generated method stub
		return m_panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		m_parser = new FileSystemParser();
		m_parser.factoryMethod();
	}

	@Override
	public void update() {
		
		
	}

}
