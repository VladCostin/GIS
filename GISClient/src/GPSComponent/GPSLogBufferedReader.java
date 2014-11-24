package GPSComponent;
/**
 * Datum: 10. 5. 2006
 * @author Lin Yen-Chia
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Hilfe dieser Klasse simuliert man die Kommunikation mit einem realen GPS
 * Empfänger, der im Sekundentakt einen Block an Informationen zeilenweise ausgibt.
 * Die Klasse leitet von BufferedReader ab.
 *
 */
public class GPSLogBufferedReader extends BufferedReader {
	private int m_interval; // 1000 ms
	private String m_filterString; // $GPGGA

	/**
	 * Erzeugt den Reader mit dem filter und Verzögerungszeit
	 * @param _in
	 *            Reader
	 * @param _filter
	 *            Einen Filter-String, der den Anfang eines neuen Blocks
	 *            signalisiert.
	 * @param _time
	 *            Eine Angabe über die Verzögerungszeit, bis der nächste Block
	 *            gesendet werden soll in Millisekunden.
	 */
	public GPSLogBufferedReader(Reader _in, String _filter, int _time) {
		super(_in);
		m_interval = _time;
		m_filterString = _filter;
	}

	/* (non-Javadoc)
	 * @see java.io.BufferedReader#readLine()
	 */
	public String readLine() throws IOException {
		String str = super.readLine();
		if (str != null) {
			if (str.startsWith(m_filterString))
				try {
					Thread.sleep(m_interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		return str;
	}

}
