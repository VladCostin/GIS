package GPSComponent;
/**
 * Datum: 01. 6. 2006
 * @author Lin Yen-Chia
 * @version 1.3
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ContextModel.LocationContext;

/**
 * Zeigt folgende Informationen an:
 * <ol>
 * <li>Longitude</li>
 * <li>Latitude</li>
 * <li>Attidue</li>
 * <li>Satelliten Anzahl</li>
 * <li>PDOP</li>
 * <li>HDOP</li>
 * <li>VDOP</li>
 * <li>Fix Type</li>
 * <li>Uhrzeit</li>
 * </ol>
 *
 */
public class GPSInfoPanel extends JPanel  {
	/**
	 * Für Serializeable
	 */
	private static final long serialVersionUID = 6217624100301918333L;
	NMEAInfo m_info;			// GPS Daten
	private int m_spalte2 = 200;		// Position für 2. Spalte
	private FontMetrics m_fontMet;		// wird für Linksbündig benötigt
	private int m_rightBound1 = 180;	// Fluchtlinie 1
	private int m_rightBound2 = 300;	// Fluchtlinie 2

	/**
	 * Default Konstruktor. NMEAInfo wird gleich erstellt.
	 *
	 */
	public GPSInfoPanel(NMEAInfo _info) {
		this.m_info = _info;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		
		int startY = 10;
		if( m_info == null)
			return;
		
		String str = "";
		
		System.out.println("intra in paintCOmponent");

		m_fontMet = _g.getFontMetrics();

		_g.drawString("Longitude:", 0, startY+15);
		str = m_info.getLongitudeGrad() + "° " + m_info.getLongitudeMinuten() + "\'";
		drawRightAlignString(_g, str, m_rightBound1, startY+15);

		_g.drawString("Latitude: ", 0, startY + 30);
		str = m_info.getLatitudeGrad() + "° " + m_info.getLatitudeMinuten() + "\'";
		drawRightAlignString(_g, str, m_rightBound1, startY + 30);

		_g.drawString("Altitude: ", 0, startY + 45);
		str = m_info.getSatHoehe() + " m";
		drawRightAlignString(_g, str, m_rightBound1, startY + 45);

		_g.drawString("Satelliten Anzahl: ", 0, startY + 60);
		str = "" + m_info.getSatellitenAnzahl();
		drawRightAlignString(_g, str, m_rightBound1, startY + 60);

		_g.drawString("PDOP: ", m_spalte2, startY + 15);
		str = "" + m_info.getPDOP();
		drawRightAlignString(_g, str, m_rightBound2, startY + 15);

		_g.drawString("HDOP: ", m_spalte2, startY + 30);
		str = "" + m_info.getHDOP();
		drawRightAlignString(_g, str, m_rightBound2, startY + 30);

		_g.drawString("VDOP: ", m_spalte2, startY + 45);
		str = "" + m_info.getVDOP();
		drawRightAlignString(_g, str, m_rightBound2, startY + 45);

		_g.drawString("Fix Type: ", m_spalte2, startY + 60);
		str = "" + m_info.getFixType();
		drawRightAlignString(_g, str, m_rightBound2, startY + 60);
		
		_g.setFont(new Font("RussellSquareRoman", Font.LAYOUT_LEFT_TO_RIGHT, 48));
		str = "" + parseTime(m_info.getZeit());
		_g.drawString(str, 360, 60);
		_g.setColor(Color.blue);
		
	}
	

	/**
	 * Zeichnet den Text Rechtsbündig
	 * @param _g Graphis Object
	 * @param _str anzuzeigenden Text
	 * @param _rightAlignX Fluchtlinie
	 * @param _startY Y-Koordinate
	 */
	private void drawRightAlignString(Graphics _g, String _str, int _rightAlignX, int _startY) {
		_g.drawString(_str, _rightAlignX - m_fontMet.stringWidth(_str), _startY);
	}

	/**
	 * Liefert das Zeit in leserlicher Form zurück. zB: 153448 --> 15:34:48
	 * @param _time GPS Zeit in double
	 * @return Zeit in leserlicher Form 
	 */
	private String parseTime(double _time) {
		int h = (int) _time / 10000;
		int min = (int) (_time - h * 10000) / 100;
		int sec = (int) (_time - h * 10000 - min * 100);
		String hour = h < 10 ? "0" + h : "" + h;
		String minutes = min < 10 ? "0" + min : "" + min;
		String seconds = sec < 10 ? "0" + sec : "" + sec;
		return hour + ":" + minutes + ":" + seconds;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
/*	public void update(Observable _o, Object _arg) {
		// von observer pattern
		if (m_info != null)
			m_info = (NMEAInfo) _arg;
		// System.out.println("UPdate!");
		this.repaint();
	}*/
}
