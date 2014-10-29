package at.fhooe.geo;

/*
 * $Log: DAEllipsoid.java,v $
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

/** 
 * Eine Hilfsklasse, die die Ellipsoidparameter zur Koordinatentransformation 
 * geographische Koordinaten (WGS84) --> GaussKrueger (Datum Austria)
 * bereitstellt
 *
 * @see EllipsoidParameter
 */

public class DAEllipsoid extends EllipsoidParameter {

  protected double m_grosseHalbachse     = 6377397.16;
  protected double m_exzentrizitaet      = 0.081696831;
  protected double m_meridianBogenConst1 = 111120.62;
  protected double m_meridianBogenConst2 = 15988.64;
  protected double m_meridianBogenConst3 = 16.73;
  
  public double get_a()  { return m_grosseHalbachse; }
  public double get_e()  { return m_exzentrizitaet; }
  public double get_c1() { return m_meridianBogenConst1; }
  public double get_c2() { return m_meridianBogenConst2; }
  public double get_c3() { return m_meridianBogenConst3; }
  
  public String toString() {
  	return "a  --> " + m_grosseHalbachse     + "\n" +
  	       "e  --> " + m_exzentrizitaet      + "\n" +
  	       "c1 --> " + m_meridianBogenConst1 + "\n" +
  	       "c2 --> " + m_meridianBogenConst2 + "\n" +
  	       "c3 --> " + m_meridianBogenConst3 + "\n";
  }
} // class