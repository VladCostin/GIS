package at.fhooe.geo;

/*
 * $Log: GeoDoublePoint.java,v $
 */

/**
 * <dl> 
 *   <dt><b>Copyright:</b></dt> 
 *     <dd> Copyright (c) 2006 </dd> 
 *   <dt><b>Company:</b></dt> 
 *     <dd> FH Oberoesterreich </dd>
 * </dl>
 *
 * Repräsentiert einen zwei dimensionalen Punkt, der aus double
 * Werten besteht.
 *
 * @author Jens Kroesche
 * @version $Revision: 1.5 $
 * @created $Date: 2005/05/10 14:56:23 $
 */
public class GeoDoublePoint {

  /// Die x-Koordinate.
  public double x = 0.0;
  /// Die y-Koordinate.
  public double y = 0.0;

  /**
   * Der Konstruktor.
   *
   * @param _x x-Koordinate.
   * @param _y y-Koordinate.
   */
  public GeoDoublePoint(double _x, double _y) {
    x = _x;
    y = _y;
  }

  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                  PUBLIC
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------

  /**
   * Berechnet die Länge des Vektors vom Ursprung
   * zum Punkt.
   *
   * @return Die Länge des Vektors
   */
  public double length() {
    return Math.sqrt((x * x) + (y * y));
  }
  
  /**
   * Gibt den Punkt in Form eines Strings zurück.
   *
   * @return Punkt in Form eines Strings
   */
  public String toString() {
    String answer = "POINT (";
    answer += x;
    answer += ";";
    answer += y;
    answer += ")";
    return answer;
  }
  
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                 PROTECTED
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------


  // --------------------------------------------------------------------------
  //                             SUPPORT METHODS
  // --------------------------------------------------------------------------

}
