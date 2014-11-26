package GPSComponent;
/**
 * Datum: 10. 5. 2006
 * @author Lin Yen-Chia
 * @version 1.4
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;

/**
 * Diese Klasse parst die eingehenden GPS Datensatz String und speichert sie in NMEAInfo ab.
 * Sie verwendert den Observer Pattern. Nach jeden Parsezyklus wird ein notifyObservers() aufgerufen.
 * Die Observers sind dabei die 2 Views (GPSInfoPanel und GPSSatellitePanel). 
 * Sie werden dann ein repaint() von sich aus ausführen.
 *
 */
public class NMEAParser extends Observable implements Runnable {
	private GPSLogBufferedReader reader;
	private NMEAInfo info;

	/**
	 * Erstellt ein GPS Empfänger Simulator GPSLogBufferedReader mit der Dateiname fileName.
	 * Den Abtastintervall wird dabei auf 1 Sekunden festgelegt.
	 * @param fileName Dateiname für GPSLogBufferedReader
	 * @throws FileNotFoundException
	 */
	public NMEAParser(String fileName) throws FileNotFoundException {
		reader = new GPSLogBufferedReader(new FileReader(fileName), "$GPGGA", 1000);
		info = new NMEAInfo();
	}

	/**
	 * Den String str wird versucht auf INT zu parsen. 
	 * Zuerst wird geschaut ob den String nicht leer ist.
	 * 
	 * @param str String zum Parsen
	 * @return Zahl in int
	 * @throws NumberFormatException
	 */
	private int getInt(String str) throws NumberFormatException {
		if (str.length() != 0) {
			return Integer.parseInt(str);
		}
		return 0;
	}

	/**
	 * Den String str wird versucht auf DOUBLE zu parsen. 
	 * Zuerst wird geschaut ob den String nicht leer ist.
	 * 
	 * @param str String zum Parsen
	 * @return Zahl in double
	 * @throws NumberFormatException
	 */
	private double getDouble(String str) throws NumberFormatException {
		if (str.length() != 0) {
			return Double.parseDouble(str);
		}
		return 0;
	}
	
	/**
	 * <pre>Den String str wird versucht auf DOUBLE zu parsen. 
	 * Zuerst wird geschaut ob den String nicht leer ist.
	 * Dann wird überprüft ob ein * im String vorhanden ist, falls ja, wird es abgeschitten.
	 * zB. 56*69 --> return 56
	 * </pre>
	 * @param str String zum Parsen
	 * @return Zahl in double
	 * @throws NumberFormatException
	 */
	private double getDoubleInLastPosition(String str) throws NumberFormatException {
		if (str.length() != 0) {
			if (str.indexOf('*') == -1) {
				return Double.parseDouble(str);
			} else {
				String temp = str.substring(0, str.indexOf('*'));
				if (temp.length() != 0)
					return Double.parseDouble(temp);
			}
		}
		return 0;
	}

	/**
	 * Startet den GPS Parser
	 */
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			
			String str = "";
			String[] fields;
			SatelliteInfo satInfo;
			int index = 0;
			int dataCount = 0;
			int dataNr = 0;
//			int satCount = 0;
			boolean clearSatFlag = false;

			for (;;) {
//				info = new NMEAInfo();
				str = reader.readLine();

				// EOF reached
				if (str == null)
					break;
				
				fields = str.split(",");

				if (str.startsWith("$GPGSV")) {
					// n Datenfelder (3 + ein Vielfaches von 4) + Sentence Type
					// = 4 + ein Vielfaches von 4 = Vielfaches von 4
					if (fields.length % 4 != 0) {
						System.out.println("Wrong GSV Data!!!");
					} else { // Everything is correct
						index = 0;
						try {
							// Anzahl der Datensätze
							index = 1;
							dataCount = getInt(fields[index]);
							
							// Datensatznummer
							index = 2;
							dataNr = getInt(fields[index]);
							
							// Anzahl der sicthbaren Satelliten
//							index = 3;
//							satCount = getInt(fields[index]);
							
						} catch (NumberFormatException e) {
							switch (index) {
								case 1: System.out.println("Wrong Data Count!!!"); break;
								case 2: System.out.println("Wrong Data Number!!!"); break;
								case 3: System.out.println("Wrong Satellite Count Data!!!"); break;
								default: break;
							}
							continue;
						}
						// wenn Flag gesetzt, dann lösche alle Satelliteninfos
						if (clearSatFlag)
							info.clearSatelliteInfos();

						if (dataCount == dataNr)
							clearSatFlag = true; // wird dann erst in der nächste runde gelöscht
						else
							clearSatFlag = false;

						for (int i = 4; i < fields.length; i++) {
							try {
								satInfo = new SatelliteInfo();

								// Nummer des Satelliten
								satInfo.setId(getInt(fields[i++]));

								// Winkelabstand bezogen zur Horizontalebene (vertikale Richtung)
								satInfo.setAngleVertical(getDouble(fields[i++]));

								// Winkelabstand bezogen auf Norden (horizontale Richtung)
								satInfo.setAngleHorizontal(getDouble(fields[i++]));

								// Signal to Noise Ration (SNR) in dB
								satInfo.setSNR(getDoubleInLastPosition(fields[i]));

								// Schaut ob den Satellite verwendet wurde
								for (int j = 0; j < info.getSatelliteIDs().size(); j++) {
									if (satInfo.getId() == info.getSatelliteIDs().get(j)) {
										satInfo.setUsed(true);
										break;
									}
								}

								// Neue Satellit hinzufügen
								info.addSatellite(satInfo);

							} catch (NumberFormatException e) {
								switch (index % 4) {
									case 0: System.out.println("Wrong Satellite ID Data!!!"); break;
									case 1: System.out.println("Wrong Verticale Angle Data!!!"); break;
									case 2: System.out.println("Wrong Horizontale Angle Data!!!"); break;
									case 3: System.out.println("Wrong SNR Data!!!"); break;
									default: break;
								} // case
							} // try
						} // for
					} // else

				} // $GPGSV

				if (str.startsWith("$GPGSA")) {
					// 17 Datenfelder + Sentence Type = 18
					if (fields.length != 18) {
						System.out.println("Wrong GSA Data!!!");
					} else { // Everything is correct
						// ID der Satelliten, die zur Berechnung verwendet wurde
						info.clearSatelliteIDs();
						try {
							for (int i = 3; i < fields.length - 4; i++) {
								info.addSatelliteIDs(getInt(fields[i]));
							}
						} catch (NumberFormatException e) {
							System.out.println("Wrong Satellite ID Data!!!");
						}

						try {
							// PDOP ausrechnen
							index = 15;
							info.setPDOP(getDouble(fields[index]));

							// HDOP ausrechnen
							index = 16;
							info.setHDOP(getDouble(fields[index]));

							// VDOP ausrechnen
							index = 17;
							info.setVDOP(getDoubleInLastPosition(fields[index]));

						} catch (NumberFormatException e) {
							switch (index) {
								case 15: System.out.println("Wrong PDOP Data!!!"); break;
								case 16: System.out.println("Wrong HDOP Data!!!"); break;
								case 17: System.out.println("Wrong VDOP Data!!!"); break;
								default: break;
							} // switch
						} // try
					} // else
				} // $GPGSA

				if (str.startsWith("$GPGGA")) {
					// 14 Datenfelder + Sentence Type = 15
					if (fields.length != 15) {
						System.out.println("Wrong GGA Data!!!");
					} else { // Everything is correct
						try {
							// Zeit ausrechnen
							index = 1;
							info.setZeit(Double.parseDouble(fields[index]));

							// Latitude ausrechnen
							index = 2;
							String temp = fields[index];
							if (temp.length() != 0) {
								info.setLatitudeGrad(getInt(temp.substring(0, 2)));
								info.setLatitudeMinuten(getDouble(temp.substring(2)));
							}

							// Longitude ausrechnen
							index = 4;
							temp = fields[index];
							if (temp.length() != 0) {
								info.setLongitudeGrad(getInt(temp.substring(0, 3)));
								info.setLongitudeMinuten(getDouble(temp.substring(3)));
							}

							// Fix Type ausrechnen
							index = 6;
							info.setFixType(getInt(fields[index]));

							// Satellitenanzahl ausrechnen
							index = 7;
							info.setSatellitenAnzahl(getInt(fields[index]));

							// Satteliten Höhe ausrechnen
							index = 9;
							info.setSatHight(getDouble(fields[index]));

						} catch (NumberFormatException e) {
							switch (index) {
								case 1: System.out.println("Wrong Time Data!!!"); break;
								case 2: System.out.println("Wrong Latitude Data!!!"); break;
								case 4: System.out.println("Wrong Longitude Data!!!"); break;
								case 6: System.out.println("Wrong Fix Type Data!!!"); break;
								case 7: System.out.println("Wrong Satelite Count Data!!!"); break;
								case 9: System.out.println("Wrong Satelite Hight Data!!!"); break;
								default: break;
							} // switch
						} // try
					} // else
				} // $GPGGA
//				info=tempInfo.cloneInfo();
				this.setChanged();
				this.notifyObservers(info);
			} // for-schleife
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
