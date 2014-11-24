package GPSComponent;
/**
 * Datum: 10. 5. 2006
 * @author Lin Yen-Chia
 * @version 1.0
 */
/**
 * Speichert die Daten von einem Satelliten.
 * <ol>
 * <li>ID</li>
 * <li>Winkel vertikal (0° - 90°)</li>
 * <li>Winkel horizontal (0° - 360°)</li>
 * <li>SNR</li>
 * <li>Wurde es zur Berechnung verwendet</li>
 * </ol>
 */
public class SatelliteInfo {
	private int m_id;
	private double m_angleVertical;
	private double m_angleHorizontal;
	private double m_snr;
	private boolean m_used;

	/**
	 * Default Konstruktor
	 *
	 */
	public SatelliteInfo() {
		m_id = 0;
		m_angleHorizontal = 0;
		m_angleVertical = 0;
		m_snr = 0;
		m_used = false;
	}

	////////////// Get und Set Methoden /////////////////
	
	/**
	 * Gibt den horizontalen Winkel zurück (0° - 360°)
	 * @return Winkel von 0° - 360°
	 */
	public double getAngleHorizontal() {
		return m_angleHorizontal;
	}

	/**
	 * 0° - 360°
	 * @param angleHorizontal
	 */
	public void setAngleHorizontal(double angleHorizontal) {
		this.m_angleHorizontal = angleHorizontal;
	}
	
	/**
	 * Gibt den vertikalen Winkel zurück (0° - 90°)
	 * @return Winkel von 0° - 90°
	 */
	public double getAngleVertical() {
		return m_angleVertical;
	}

	/**
	 * 0° - 90°
	 * @param angleVertical
	 */
	public void setAngleVertical(double angleVertical) {
		this.m_angleVertical = angleVertical;
	}

	public int getId() {
		return m_id;
	}

	public void setId(int id) {
		this.m_id = id;
		
	}

	public int getSnr() {
		return (int) m_snr;
	}

	public void setSNR(double snr) {
		this.m_snr = snr;
	}

	public boolean isUsed() {
		return m_used;
	}

	public void setUsed(boolean used) {
		this.m_used = used;
	}
}
