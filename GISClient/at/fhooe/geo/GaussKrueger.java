package at.fhooe.geo;

/*
 * $Log: GaussKrueger.java,v $
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

public class GaussKrueger {
	
	/// Der Rechtswert in cm
	protected int m_rechts   = 0;
	/// Der Hochwert   in cm
	protected int m_hoch     = 0;
	/// Der Bezugsmeridian 
	protected int m_meridian = 0;
	

  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                  PUBLIC
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------

  public int getRechts()   { return m_rechts;   }
  public int getHoch()     { return m_hoch;     }
  public int getMeridian() { return m_meridian; }
  
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