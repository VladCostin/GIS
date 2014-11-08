package GeoObject;
/*
 * $Log: GeoDoublePoint.java,v $
 */

/**
 * Represents a double valued point in space
 */
public class GeoDoublePoint {

  /// the x coordinate
  public double m_x = 0.0;
  /// the y coordinate
  public double m_y = 0.0;

  /**
   * The constructor
   *
   * @param _x the x coordinate
   * @param _y the y coordinate
   */
  public GeoDoublePoint(double _x, double _y) {
    m_x = _x;
    m_y = _y;
  }

  /**
   * calculates the length of the vector from (0,0) to the point in space
   *
   * @return the length of the vector
   */
  public double length() {
    return Math.sqrt((m_x * m_x) + (m_y * m_y));
  }
  
  /**
   * returns a string representation of the point
   *
   * @return the point information in a string
   */
  public String toString() {
    String answer = "POINT (";
    answer += m_x;
    answer += ";";
    answer += m_y;
    answer += ")";
    return answer;
  }

}
