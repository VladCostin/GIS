package at.fhooe.geo;

/*
 * $Log: GeoLine.java,v $
 * Revision 1.2  2004/04/16 09:10:07  dthobe
 * Aktuallisierte Klasse aus db.index übernommen GeoLine in db.index nicht mehr vorhanden
 *
 * Revision 1.1  2004/03/24 08:36:07  jkroesche
 * xPOI - MAPGIS ... initiales checkin ...
 *
 */

import java.io.Serializable;


/**
 * Die Klasse <code>GeoLine</code> repräsentiert eine Linie, die mit
 * double-Werten initialisiert wird.
 *
 *
 *
 * <dl>
 *   <dt><b>Copyright:</b></dt>
 *     <dd> Copyright (c) 2004 </dd>
 *   <dt><b>Company:</b></dt>
 *     <dd> MAPGIS </dd>
 * </dl>
 *
 * @author  Daniel Thobe,
 *
 * @version $Revision: 1.2 $
 */
public class GeoLine implements Serializable {

  /**
   * Die x-Koordinate des 1. Punktes.
   */
  protected double m_xp1 = 0;

  /**
   * Die y-Koordinate des 1. Punktes.
   */
  protected double m_yp1 = 0;

  /**
   * Die x-Koordinate des 2. Punktes.
   */
  protected double m_xp2 = 0;

  /**
   * Die y-Koordinate des 2. Punktes.
   */
  protected double m_yp2 = 0;


  /**
   * Konstruktor, der die Linie mit double-Werten initialisiert.
   *
   * @param _xp1 die x-Koordinate des 1. Punktes.
   * @param _yp1 die y-Koordinate des 1. Punktes.
   * @param _xp2 die x-Koordinate des 2. Punktes.
   * @param _yp2 die y-Koordinate des 2. Punktes.
   */
  public GeoLine(double _xp1, double _yp1, double _xp2, double _yp2) {
    m_xp1 = _xp1;
    m_yp1 = _yp1;
    m_xp2 = _xp2;
    m_yp2 = _yp2;
  }


  /**
   * Gibt den einen Punkt der Linie zurück.
   *
   * @return Der eine Punkt der Linie.
   */
  public GeoDoublePoint getPoint1() {
    return new GeoDoublePoint(m_xp1, m_yp1);
  }

  /**
   * Gibt den anderen Punkt der Linie zurück.
   *
   * @return Der andere Punkt der Linie.
   */
  public GeoDoublePoint getPoint2() {
    return new GeoDoublePoint(m_xp2, m_yp2);
  }

  /**
   * Gibt die x-Koordinate vom einen Punkt zurück.
   *
   * @return x-Koordinate vom einen Punkt.
   */
  public double getP1X() {
    return m_xp1;
  }

  /**
   * Gibt die y-Koordinate vom einen Punkt zurück.
   *
   * @return y-Koordinate vom einen Punkt.
   */
  public double getP1Y() {
    return m_yp1;
  }

  /**
   * Gibt die x-Koordinate vom anderen Punkt zurück.
   *
   * @return x-Koordinate vom anderen Punkt.
   */
  public double getP2X() {
    return m_xp2;
  }

  /**
   * Gibt die y-Koordinate vom anderen Punkt zurück.
   *
   * @return y-Koordinate vom anderen Punkt.
   */
  public double getP2Y() {
    return m_yp2;
  }
}
