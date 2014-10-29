package at.fhooe.geo;

/*
 * $Log: GeoFunctions.java,v $
 */

/**
 * <dl> 
 *   <dt><b>Copyright:</b></dt> 
 *     <dd> Copyright (c) 2006 </dd> 
 *   <dt><b>Company:</b></dt> 
 *     <dd> FH Oberoesterreich </dd>
 * </dl>
 *
 * @author Jens Kroesche
 * @version $Revision: 1.5 $
 * @created $Date: 2005/05/10 14:56:23 $
 */

public class GeoFunctions {
	
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                  PUBLIC
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------

  /**
   * Der Algorithmus überprueft für drei übergebene Punkte,
   * ob man sich, wenn man vom ersten (p0) zum zweiten (p1) und dann
   * zum dritten (p2) geht, gegen den Uhrzeigersinn bewegt oder nicht.
   * Falls die drei Punkte auf einer Geraden liegen, wird 0 zurück-
   * geliefert.
   * <br>
   *
   * date 6. Mai 1997
   * author Thomas Wilkens
   * @param   _p0 1. Punkt.
   * @param   _p1 2. Punkt.
   * @param   _p2 3. Punkt.
   * @return  Moegliche Rueckgabewerte:
   *         -  1  Man bewegt sich gegen den Uhrzeigersinn.
   *         - -1  Man bewegt sich mit dem Uhrzeigersinn.
   *         -  0  Die Punkte liegen auf einer Geraden.
   */
  public static int checkClockwise(GeoDoublePoint _p0, GeoDoublePoint _p1, GeoDoublePoint _p2) {
    double dx1 = _p1.x - _p0.x;
    double dy1 = _p1.y - _p0.y;
    double dx2 = _p2.x - _p0.x;
    double dy2 = _p2.y - _p0.y;

    double mp0p1 = dy1 * dx2;
    double mp0p2 = dx1 * dy2;

    if (mp0p2 > mp0p1)
      return (1);
    if (mp0p2 < mp0p1)
      return (-1);
    else
      return (0);
  }

  /**
   * Der Algorithmus überprueft, ob ein Punkt auf einer Linie liegt.
   * <br>
   *
   * author Joerg Friebe
   * @param _p Der zu überprüfende Punkt.
   * @param _l Die untersuchte Linie.
   * @return Mögliche Rückgabewerte :
   *        - 1 (Der Punkt liegt auf der Linie)
   *        - 0 (Der Punkt liegt nicht auf der Linie).
   */
  public static int pointOnLine(GeoDoublePoint _p, GeoLine _l) {
    // ----- Wenn die drei Punkte nicht auf einer Linie liegen, dann -----
    // ----- kann der Punkt auch nicht auf der Linie liegen.	   -----
    double dx1 = _l.getP1X() - _p.x;
    double dy1 = _l.getP1Y() - _p.y;
    double dx2 = _l.getP2X() - _p.x;
    double dy2 = _l.getP2Y() - _p.y;

    double m_p_lbeg = dy1 * dx2;
    double m_p_lend = dx1 * dy2;

    if (m_p_lbeg != m_p_lend) // Wenn die drei Punkte auf einer Linie liegen
      return 0;

    if (_l.getP1X() == _l.getP2X() && _l.getP1Y() > _l.getP2Y())
      if (_p.y >= _l.getP2Y() && _p.y <= _l.getP1Y())
        return 1;

    if (_l.getP1X() == _l.getP2X() && _l.getP1Y() < _l.getP2Y())
      if (_p.y >= _l.getP1Y() && _p.y <= _l.getP2Y())
        return 1;

    if (_l.getP1Y() == _l.getP2Y() && _l.getP1X() > _l.getP2X())
      if (_p.x >= _l.getP2X() && _p.x <= _l.getP1X())
        return 1;

    if (_l.getP1Y() == _l.getP2Y() && _l.getP1X() < _l.getP2X())
      if (_p.x >= _l.getP1X() && _p.x <= _l.getP2X())
        return 1;

    if (_l.getP1X() > _l.getP2X() && _l.getP1Y() > _l.getP2Y())
      if (_p.x >= _l.getP2X() && _p.x <= _l.getP1X() && _p.y >= _l.getP2Y() && _p.y <= _l.getP1Y())
        return 1;

    if (_l.getP2X() > _l.getP1X() && _l.getP1Y() > _l.getP2Y())
      if (_p.x >= _l.getP1X() && _p.x <= _l.getP2X() && _p.y >= _l.getP2Y() && _p.y <= _l.getP1Y())
        return 1;

    if (_l.getP1X() > _l.getP2X() && _l.getP2Y() > _l.getP1Y())
      if (_p.x >= _l.getP2X() && _p.x <= _l.getP1X() && _p.y >= _l.getP1Y() && _p.y <= _l.getP2Y())
        return 1;

    if (_l.getP2X() > _l.getP1X() && _l.getP2Y() > _l.getP1Y())
      if (_p.x >= _l.getP1X() && _p.x <= _l.getP2X() && _p.y >= _l.getP1Y() && _p.y <= _l.getP2Y())
        return 1;

    if (_l.getP1X() == _l.getP2X() && _l.getP1Y() == _l.getP2Y())
      if (_p.x == _l.getP1X() && _p.y == _l.getP1Y())
        return 1;

    return 0;
  }


  /**
   * Der Algorithmus überprüft, ob sich zwei Linien schneiden.
   * <br>
   *
   *  date   6. Mai 1997
   *  author Thomas Wilkens
   * @param _line1 Die eine der beiden zu ueberpruefenden Linien.
   * @param _line2 Die ander der beiden zu ueberpruefenden Linien.
   * @return Moegliche Rueckgabewerte :
   *        - 1 (Die Linien schneiden sich)
   *        - 0 (Die Linien schneiden sich nicht).
   */
  public static double linesIntersect(GeoLine _line1, GeoLine _line2) {

    GeoDoublePoint l1b = _line1.getPoint1();
    GeoDoublePoint l1e = _line1.getPoint2();
    GeoDoublePoint l2b = _line2.getPoint1();
    GeoDoublePoint l2e = _line2.getPoint2();

    if (_line1.getPoint1() == _line1.getPoint2())
      return (pointOnLine(_line1.getPoint1(), _line2));

    if (_line2.getPoint1() == _line2.getPoint2())
      return (pointOnLine(_line2.getPoint1(), _line1));

    int v1 = checkClockwise(l1b, l1e, l2b);
    int v2 = checkClockwise(l1b, l1e, l2e);
    int v3 = checkClockwise(l2b, l2e, l1b);
    int v4 = checkClockwise(l2b, l2e, l1e);

    if (v1 * v2 < 0 && v3 * v4 < 0)
      return 1;

    if (v1 == 0 && pointOnLine(l2b, _line1) != 0)
      return 1;

    if (v2 == 0 && pointOnLine(l2e, _line1) != 0)
      return 1;

    if (v3 == 0 && pointOnLine(l1b, _line2) != 0)
      return 1;

    if (v4 == 0 && pointOnLine(l1e, _line2) != 0)
      return 1;

    return 0;
  }

  
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                 PROTECTED
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------


  // --------------------------------------------------------------------------
  //                             SUPPORT METHODS
  // --------------------------------------------------------------------------
	
	public static void main(String[] _argv) {
	}
} // class