
package GPSComponent;

/**
 * Datum: 10. 5. 2006
 * @author Lin Yen-Chia
 * @version 1.0
 */
import java.util.Vector;

/**
 * Speichert die Daten der GPS Datensatz. 
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
 * <li>Einzelne Satelliten Infos</li>
 * <li>verwendeten Satelliten IDs</li>
 * </ol>
 */
public class NMEAInfo {
	private int m_latitudeGrad; 
	private double m_latitudeMinuten;
	private int m_longitudeGrad;
	private double m_longitudeMinuten;
	private double m_zeit;
	private int m_satellitenAnzahl;
	private double m_PDOP;
	private double m_HDOP;
	private double m_VDOP;
	private int m_fixType;
	private double m_satHight;
	private Vector<SatelliteInfo> m_satinfos;
	private Vector<Integer> m_satelliteIDs;

	/**
	 * Standard Konstruktor.
	 *
	 */
	public NMEAInfo() {
		m_latitudeGrad = 0;
		m_longitudeGrad = 0;
		m_satinfos = new Vector<SatelliteInfo>(12);
		m_satelliteIDs = new Vector<Integer>(12);
	}

	/////////////////// Get und Set Methoden ///////////////////
	public int getLatitudeGrad() {
		return m_latitudeGrad;
	}

	public void setLatitudeGrad(int latitude) {
		this.m_latitudeGrad = latitude;
	}

	public int getLongitudeGrad() {
		return m_longitudeGrad;
	}

	public void setLongitudeGrad(int longitude) {
		this.m_longitudeGrad = longitude;
	}

	public double getLatitudeMinuten() {
		return m_latitudeMinuten;
	}

	public void setLatitudeMinuten(double latitudeMinuten) {
		this.m_latitudeMinuten = latitudeMinuten;
	}

	public double getLongitudeMinuten() {
		return m_longitudeMinuten;
	}

	public void setLongitudeMinuten(double longitudeMinuten) {
		this.m_longitudeMinuten = longitudeMinuten;
	}

	public double getZeit() {
		return m_zeit;
	}

	public void setZeit(double zeit) {
		this.m_zeit = zeit;
	}

	public int getSatellitenAnzahl() {
		return m_satellitenAnzahl;
	}

	public void setSatellitenAnzahl(int satellitenAnzahl) {
		this.m_satellitenAnzahl = satellitenAnzahl;
	}

	public double getHDOP() {
		return m_HDOP;
	}

	public void setHDOP(double hdop) {
		m_HDOP = hdop;
	}

	public int getFixType() {
		return m_fixType;
	}

	public void setFixType(int fixType) {
		this.m_fixType = fixType;
	}

	public double getSatHoehe() {
		return m_satHight;
	}

	public void setSatHight(double satHight) {
		this.m_satHight = satHight;
	}

	public double getPDOP() {
		return m_PDOP;
	}

	public void setPDOP(double pdop) {
		m_PDOP = pdop;
	}

	public double getVDOP() {
		return m_VDOP;
	}

	public void setVDOP(double vdop) {
		m_VDOP = vdop;
	}

	public Vector<SatelliteInfo> getSatinfos() {
		return m_satinfos;
	}

	public void setSatinfos(Vector<SatelliteInfo> satinfos) {
		this.m_satinfos = satinfos;
	}

	public void addSatellite(SatelliteInfo satinfos) {
		this.m_satinfos.add(satinfos);
	}

	/**
	 * Löscht alle Satelliteninfo in der Vektor.
	 * 
	 */
	public void clearSatelliteInfos() {
		this.m_satinfos.clear();
	}

	public Vector<Integer> getSatelliteIDs() {
		return m_satelliteIDs;
	}

	public void setSatelliteIDs(Vector<Integer> satelliteIDs) {
		this.m_satelliteIDs = satelliteIDs;
	}

	public void addSatelliteIDs(int id) {
		if (id != 0)
			this.m_satelliteIDs.add(id);
	}

	public void clearSatelliteIDs() {
		this.m_satelliteIDs.clear();
	}
}
