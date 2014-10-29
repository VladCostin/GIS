package at.fhooe.geo;

/*
 * $Log: EllipsoidParameter.java,v $
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

public abstract class EllipsoidParameter {

	/// Koordinatendatum fuer Oesterreich
	public static final int DA = 0;

	/**
   * Liefert die Groesse der "grossen" Halbachse des Referenzellipsoiden
   * @return den Wert der grossen Halbachse
	 */ 
  public abstract double get_a();
  
	/**
   * Liefert die Exzentrizitaet des Referenzellipsoiden
   * @return die Exzentrizitaet des Ellipsoiden
	 */ 
  public abstract double get_e();
  
  /**
   * Liefert die erste Konstante zur Berechnung des Meridianbogens
   * @return die erste Konstante zur Berechnung des Meridianbogens
   */
  public abstract double get_c1();
  
  /**
   * Liefert die erste Konstante zur Berechnung des Meridianbogens
   * @return die erste Konstante zur Berechnung des Meridianbogens
   */
  public abstract double get_c2();
  
  /**
   * Liefert die erste Konstante zur Berechnung des Meridianbogens
   * @return die erste Konstante zur Berechnung des Meridianbogens
   */
  public abstract double get_c3();
  
  /**
   * Liefert die String-Repraesentation der Parameterwerte
   * @return die Parameterinformationen in String-Form
   * @see java.lang.String
   */
  public abstract String toString();
  
  public static EllipsoidParameter getEllipsoidParameter(int _type) {
    switch (_type) {
      case DA : return new DAEllipsoid();
      default : return new DAEllipsoid();
    }
  }

}