package GISCalculation;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Polygon;


public class GeoTransformationMatrix {
	
  /// Zelle(1,1) der 3x3-Matrix
  private double matrix11 = 0.0; 
  /// Zelle(1,2) der 3x3-Matrix
  private double matrix12 = 0.0;  
  /// Zelle(1,3) der 3x3-Matrix
  private double matrix13 = 0.0; 
  
  /// Zelle(2,1) der 3x3-Matrix
  private double matrix21 = 0.0; 
  /// Zelle(2,2) der 3x3-Matrix
  private double matrix22 = 0.0; 
  /// Zelle(2,3) der 3x3-Matrix
  private double matrix23 = 0.0; 
  
  /// Zelle(3,1) der 3x3-Matrix
  private double matrix31 = 0.0;
  /// Zelle(3,2) der 3x3-Matrix
  private double matrix32 = 0.0;
  /// Zelle(3,3) der 3x3-Matrix
  private double matrix33 = 0.0;

  /**
   * Standard-Konstruktor
   */
  public GeoTransformationMatrix() {}

  /**
   * Konstruktor mit den einzelnen Zellenwerten
   * der 3x3-Matrix
   */
  public GeoTransformationMatrix (
    double a1,     double a2,     double a3,
    double b1,     double b2,     double b3,
    double c1,     double c2,     double c3) 
  {
    matrix11 = a1;
    matrix12 = a2;
    matrix13 = a3;

    matrix21 = b1;
    matrix22 = b2;
    matrix23 = b3;

    matrix31 = c1;
    matrix32 = c2;
    matrix33 = c3;
  }
  
  /**
   * Copy-Konstruktor
   * @param _o Die Matrix, deren Werte uebernommen werden sollen
   */
  public GeoTransformationMatrix(GeoTransformationMatrix _o) {
    matrix11 = _o.matrix11;
    matrix12 = _o.matrix12;
    matrix13 = _o.matrix13;
    
    matrix21 = _o.matrix21;
    matrix22 = _o.matrix22;
    matrix23 = _o.matrix23;
    
    matrix31 = _o.matrix31;
    matrix32 = _o.matrix32;
    matrix33 = _o.matrix33;
  }
  
  /**
   * Multipliziert die aktuelle Matrix mit einem konstanten
   * Wert _s und liefert die hieraus entstehende Matrix zurueck
   * @param _s Der Wert mit dem die Matrix multipliziert werden soll
   * @return Die neue Matrix
   */
  public GeoTransformationMatrix multiply(double _s) {
    return new GeoTransformationMatrix(matrix11 * _s, matrix12 * _s, matrix13 * _s,
                                       matrix21 * _s, matrix22 * _s, matrix23 * _s,
                                       matrix31 * _s, matrix32 * _s, matrix33 * _s);
  }
  
	/**
	 * Multipliziert einen Punkt mit der Matrix und liefert das 
	 * Ergebnis der Multiplikation zurueck
	 * 
	 * @param _pt Der Punkt, der mit der Matrix multipliziert
	 *            werden soll
	 * @return Ein neuer Punkt, der das Ergebnis der 
	 *         Multiplikation repraesentiert
	 */
  public Point multiply(Point _pt) {
	  
    double x = _pt.x * matrix11 + _pt.y * matrix12 + matrix13;
    double y = _pt.y * matrix21 + _pt.y * matrix22 + matrix23;
    
    return new Point((int)x,(int)y);
  }

	/**
	 * Multipliziert ein Rechteck mit der Matrix und liefert das 
	 * Ergebnis der Multiplikation zurueck
	 * 
	 * @param _rect Das Rechteck, das mit der Matrix multipliziert
	 *              werden soll
	 * @return Ein neues Rechteck, das das Ergebnis der 
	 *         Multiplikation repraesentiert
	 */
  public Rectangle multiply(Rectangle _rect) {
    Point lb = new Point(_rect.x, _rect.y);
    Point rt = new Point(_rect.x + _rect.width,
                         _rect.y + _rect.height);
                         
    Point convLB = multiply(lb);
    Point convRT = multiply(rt);
    
    Rectangle rect = new Rectangle(Integer.MAX_VALUE,
                                   Integer.MAX_VALUE,
                                   Integer.MIN_VALUE,
                                   Integer.MIN_VALUE);
    rect.add(convLB);
    rect.add(convRT);
    
    return rect;
  }
  
  /**
   * @param _interfaceDraw : the component of the geo object that must have it's member multipled
   */
 /* public void multiplyInterfaceDraw(InterfaceDraw _interfaceDraw)
  {
	  if(_interfaceDraw instanceof AreaObj)
	  {
		  AreaObj obj = (AreaObj) _interfaceDraw;
		  obj._polyMultipled =  multiply(obj.get_polygon());
		  return;
	  }
	  if(_interfaceDraw instanceof PointObj)
	  {
		  PointObj obj = (PointObj) _interfaceDraw;
		 
		  Point p_m = multiply(new Point(obj.get_mx(), obj.get_my()));
		  obj.set_multiply_mx(p_m.x);
		  obj.set_multiply_my(p_m.y);
		  
		  
		 
		  return;
	  }
	  if(_interfaceDraw instanceof LineObj)
	  {
		  int i;
		  LineObj obj = (LineObj) _interfaceDraw;
		  obj.setX(new int[obj.get_points().size()]);
		  obj.setY(new int[obj.get_points().size()]);
			
		  for(i = 0; i < obj.get_points().size(); i++)
		  {
				Point point = multiply(obj.get_points().get(i));
				obj.getX()[i] = point.x;
				obj.getY()[i] = point.y;

		  }
		  
		  return;
	  }
	  
  }*/

	/**
	 * Multipliziert ein Polygon mit der Matrix und liefert das 
	 * Ergebnis der Multiplikation zurueck
	 * 
	 * @param _poly Das Polygon, das mit der Matrix multipliziert
	 *              werden soll
	 * @return Ein neues Polygon, das das Ergebnis der 
	 *         Multiplikation repraesentiert
	 */
  public Polygon multiply(Polygon _poly) {
    int[] pointsX = new int[_poly.npoints];
    int[] pointsY = new int[_poly.npoints];
    int   pointsN = _poly.npoints;
    for (int i = 0 ; i < pointsN ; i++) {
      pointsX[i] = (int)(matrix11*(double)_poly.xpoints[i] + 
                         matrix12*(double)_poly.ypoints[i] + 
                         matrix13);
      pointsY[i] = (int)(matrix21*(double)_poly.xpoints[i] + 
                         matrix22*(double)_poly.ypoints[i] + 
                         matrix23);
    } // for i
    Polygon result = new Polygon(pointsX, pointsY, pointsN);
    return result;
  }
  
  /** 
   * Multipliziert den uebergebenen Punkt mit der Matrix
   * @param _pt der Punkt der zu multiplizieren ist
   * @return Das Ergebnis der Multiplikation zwischen Punkt und Matrix,
   *         ein neuer Punkt
   * @see GeoDoublePoint
   */
  public GeoDoublePoint multiply(GeoDoublePoint _pt) {
    double srcx  = _pt.m_x;
    double srcy  = _pt.m_y;
    double destx = matrix11 * srcx + matrix12 * srcy;
    double desty = matrix21 * srcx + matrix22 * srcy;
    // System.out.println("m12    --> " + matrix12);
    // System.out.println("m22    --> " + matrix22);
    // System.out.println("dest x --> " + destx);
    // System.out.println("dest y --> " + desty);
    return new GeoDoublePoint(destx,desty);
  }

    
	/**
	 * Liefert eine Matrix, die das Ergebnis einer Matrizen-
	 * multiplikation zwischen dieser und der uebergebenen Matrix
	 * ist
	 * 
	 * @param _o Die Matrix mit der Multipliziert werden soll
	 * @return Die Ergebnismatrix der Multiplikation
	 */
  public GeoTransformationMatrix multiply(GeoTransformationMatrix _o) {
    GeoTransformationMatrix result = new GeoTransformationMatrix();

    result.matrix11 = matrix11 * _o.matrix11 +
                      matrix12 * _o.matrix21 +
                      matrix13 * _o.matrix31;
    result.matrix12 = matrix11 * _o.matrix12 +
                      matrix12 * _o.matrix22 +
                      matrix13 * _o.matrix32;
    result.matrix13 = matrix11 * _o.matrix13 +
                      matrix12 * _o.matrix23 +
                      matrix13 * _o.matrix33;

    result.matrix21 = matrix21 * _o.matrix11 +
                      matrix22 * _o.matrix21 +
                      matrix23 * _o.matrix31;
    result.matrix22 = matrix21 * _o.matrix12 +
                      matrix22 * _o.matrix22 +
                      matrix23 * _o.matrix32;
    result.matrix23 = matrix21 * _o.matrix13 +
                      matrix22 * _o.matrix23 +
                      matrix23 * _o.matrix33;

    result.matrix31 = matrix31 * _o.matrix11 +
                      matrix32 * _o.matrix21 +
                      matrix33 * _o.matrix31;
    result.matrix32 = matrix31 * _o.matrix12 +
                      matrix32 * _o.matrix22 +
                      matrix33 * _o.matrix32;
    result.matrix33 = matrix31 * _o.matrix13 +
                      matrix32 * _o.matrix23 +
                      matrix33 * _o.matrix33;
    return result;
  }

	/**
	 * Liefert eine Rotationsmatrix
	 * 
	 * @param _alpha Der Winkel (in rad), um den rotiert werden 
	 *               soll
	 * @return Die Rotationsmatrix
	 */
  public static GeoTransformationMatrix rotate (double _rad) {
    return new GeoTransformationMatrix(Math.cos(_rad), -Math.sin(_rad), 0,
                                       Math.sin(_rad),  Math.cos(_rad), 0,
                                       0,               0,              1);
  }

	/**
	 * Liefert eine Skalierungsmatrix
	 * 
	 * @param _scaleVal Der Skalierungswert der Matrix 
	 * @return Die Skalierungsmatrix
	 */
  public static GeoTransformationMatrix scale (double _scale) {
    return new GeoTransformationMatrix(_scale, 0,     0,
                                       0,     _scale, 0,
                                       0,      0,     1);
  }

	/**
	 * Liefert eine Translationsmatrix
	 * 
	 * @param _x Der Translationswert der Matrix in X-Richtung
	 * @param _y Der Translationswert der Matrix in Y-Richtung
	 * @return Die Translationsmatrix
	 */
  public static GeoTransformationMatrix translate (double _x ,double _y) {
    return new GeoTransformationMatrix(1, 0, _x, 
                                       0, 1, _y,
                                       0, 0,  1);
  }
  
	/**
	 * Liefert eine Translationsmatrix
	 * 
	 * @param _pt Ein Punkt, der die Translationswerte enthaelt
	 * @return Die Translationsmatrix
	 */
  public static GeoTransformationMatrix translate (Point _p) {
    return translate(_p.x, _p.y);
  }

	/**
	 * Liefert eine Spiegelungsmatrix (Y-Achse)
	 * 
	 * @return Die Spiegelungsmatrix
	 */
  public static GeoTransformationMatrix mirrorY() { 
    return new GeoTransformationMatrix(-1, 0, 0,
                                        0, 1, 0,
                                        0, 0, 1);
  }

	/**
	 * Liefert eine Spiegelungsmatrix (X-Achse)
	 * 
	 * @return Die Spiegelungsmatrix
	 */
  public static GeoTransformationMatrix mirrorX() { 
    return new GeoTransformationMatrix(1, 0, 0,
                                       0,-1, 0,
                                       0, 0, 1);
  }

  /**
   * Liefert den Skalierungsfaktor, um das _map-Rechteck
   * "komplett" in das _win-Rechteck einzupassen
   * @param _map Das Kartenrechteck
   * @param _win Das Bildschirmfenster
   * @return der Skalierungsfaktor, um den das _map Rechteck
   *         vergroessert/verkleinert werden muss, damit es 
   *         komplett in _win passt
   * @see java.awt.Rectangle
   */
  public static double getZoomFactor(Rectangle _map,
                                     Rectangle _win) {
    if (_map == null || _win == null) {
    	// besser waere eine Exception zu schmeissen, um den
    	// Anwender/Programmierer darueber zu informieren, dass
    	// ein Fehler aufgetreten ist.
    	return 1.0;
    }
    double scaleX = _map.width  != 0 ? (double)_win.width  / (double)_map.width  : 1.0;
    double scaleY = _map.height != 0 ? (double)_win.height / (double)_map.height : 1.0;
    // Skalierungsfaktor ist der kleinere von beiden
    return scaleX < scaleY ? scaleX : scaleY;
  }

	/**
	 * Liefert den Faktor, der benoetigt wird, um das _world-
	 * Rechteck in das _win-Rechteck zu skalieren (einzupassen) 
	 * bezogen auf die X-Achse --> Breite 
	 * 
	 * @param _world Das Rechteck in Weltkoordinaten
	 * @param _win   Das Rechteck in Bildschirmkoordinaten
	 * @return Der Skalierungsfaktor
	 */
  public static double getZoomFactorX(Rectangle _map,
                                      Rectangle _win) {
    if (_map == null || _win == null) {
    	// besser waere eine Exception zu schmeissen, um den
    	// Anwender/Programmierer darueber zu informieren, dass
    	// ein Fehler aufgetreten ist.
    	return 1.0;
    }
    return _map.width  != 0 ? (double)_win.width  / (double)_map.width  : 1.0;
  }

	/**
	 * Liefert den Faktor, der benoetigt wird, um das _world-
	 * Rechteck in das _win-Rechteck zu skalieren (einzupassen) 
	 * bezogen auf die Y-Achse --> Hoehe 
	 * 
	 * @param _world Das Rechteck in Weltkoordinaten
	 * @param _win   Das Rechteck in Bildschirmkoordinaten
	 * @return Der Skalierungsfaktor
	 */
  public static double getZoomFactorY(Rectangle _map,
                                      Rectangle _win) {
    if (_map == null || _win == null) {
    	// besser waere eine Exception zu schmeissen, um den
    	// Anwender/Programmierer darueber zu informieren, dass
    	// ein Fehler aufgetreten ist.
    	return 1.0;
    }
    return _map.height != 0 ? (double)_win.height / (double)_map.height : 1.0;
  }

	/**
	 * Liefert die Invers-Matrix der Transformationsmatrix 
	 * @return Die Invers-Matrix
	 */
  public GeoTransformationMatrix invers() {
    double determinante = det();
    if (determinante == 0) {
    	// besser waere eine Exception zu schmeissen, um den
    	// Anwender/Programmierer darueber zu informieren, dass
    	// ein Fehler aufgetreten ist, aber so geht es auch ;-)
    	return null;
    }
    double u = 1/determinante;

    double m11 = det(matrix22, matrix23, matrix32, matrix33);
    double m12 = det(matrix13, matrix12, matrix33, matrix32);
    double m13 = det(matrix12, matrix13, matrix22, matrix23);
    
    double m21 = det(matrix23, matrix21, matrix33, matrix31);
    double m22 = det(matrix11, matrix13, matrix31, matrix33);
    double m23 = det(matrix13, matrix11, matrix23, matrix21);
    
    double m31 = det(matrix21, matrix22, matrix31, matrix32);
    double m32 = det(matrix12, matrix11, matrix32, matrix31);
    double m33 = det(matrix11, matrix12, matrix21, matrix22);
    
    GeoTransformationMatrix matrix =
      new GeoTransformationMatrix(m11, m12, m13, 
                                  m21, m22, m23, 
                                  m31, m32, m33);
    return matrix.multiply(u);
  }

	/**
	 * Liefert die Determinate der aktuellen 3x3 Matrix
	 * @return Die Determinate
	 */
  public double det() {
    return (matrix11 * matrix22 * matrix33) + 
           (matrix12 * matrix23 * matrix31) + 
           (matrix13 * matrix21 * matrix32) -
           (matrix11 * matrix23 * matrix32) - 
           (matrix12 * matrix21 * matrix33) - 
           (matrix13 * matrix22 * matrix31);
  }
  
  /**
   * Liefert die Determinate einer 2x2-Matrix
   * @param _m11 Zelle(1,1) der Matrix
   * @param _m12 Zelle(1,2) der Matrix
   * @param _m21 Zelle(2,1) der Matrix
   * @param _m22 Zelle(2,2) der Matrix
   * @return die Determinate der 2x2 Matrix
   */
  public double det(double _m11, double _m12,
                    double _m21, double _m22) {
    return _m11*_m22 - _m12*_m21;
  }

	/**
	 * Liefert eine String-Repraesentation der Matrix
	 * 
	 * @return Ein String mit dem Inhalt der Matrix
	 */
  public String toString() {
    String ret = new String(matrix11 + "\t" + matrix12  + "\t" + matrix13 + "\n" +
                            matrix21 + "\t" + matrix22  + "\t" + matrix23 + "\n" +
                            matrix31 + "\t" + matrix32  + "\t" + matrix33 + "\n");
    return ret;
  }

  public double getMatrix11() { return matrix11; }
  public double getMatrix12() { return matrix12; }
  public double getMatrix13() { return matrix13; }
  
  public double getMatrix21() { return matrix21; }
  public double getMatrix22() { return matrix22; }
  public double getMatrix23() { return matrix23; }
  
  public double getMatrix31() { return matrix31; }
  public double getMatrix32() { return matrix32; }
  public double getMatrix33() { return matrix33; }

  public void setMatrix11(double _val) {  matrix11 = _val; }
  public void setMatrix12(double _val) {  matrix12 = _val; }
  public void setMatrix13(double _val) {  matrix13 = _val; }
  
  public void setMatrix21(double _val) {  matrix21 = _val; }
  public void setMatrix22(double _val) {  matrix22 = _val; }
  public void setMatrix23(double _val) {  matrix23 = _val; }
  
  public void setMatrix31(double _val) {  matrix31 = _val; }
  public void setMatrix32(double _val) {  matrix32 = _val; }
  public void setMatrix33(double _val) {  matrix33 = _val; }

  /**
   * Erzeugt eine Kopie der aktuellen Matrix
   * @return Die Kopie der Matrix als Objekt
   */
  public Object clone() {
    return copy();
  }

  /**
   * Erzeugt eine Kopie der aktuellen Matrix
   * @return Die Kopie der Matrix
   */
  public GeoTransformationMatrix copy() {
    return new GeoTransformationMatrix(this);
  }

	/**
	 * Liefert eine Matrix, die alle notwendigen Transformationen 
	 * beinhaltet (Translation, Skalierung, Spiegelung und 
	 * Translation), um ein _map-Rechteck in ein _win-Rechteck 
	 * abzubilden
	 * 
	 * @param _map Das Rechteck in Weltkoordinaten
	 * @param _win Das Rechteck in Bildschirmkoordinaten
	 * @return Die Transformationsmatrix
	 */
  public static GeoTransformationMatrix zoomToFit(Rectangle _map,
                                                  Rectangle _win) {
    double scaleFactor = getZoomFactor(_map, _win);
    
    GeoTransformationMatrix translateA = 
      GeoTransformationMatrix.translate(-_map.x, -_map.y);
    GeoTransformationMatrix scale =
      GeoTransformationMatrix.scale(scaleFactor);
    GeoTransformationMatrix mirrorX =
      GeoTransformationMatrix.mirrorX();
    GeoTransformationMatrix translateB = 
      GeoTransformationMatrix.translate(0,_win.height);
      
    // Matrizen in umgekehrter Reihenfolge zusammenmultiplizieren
    return translateB.multiply(mirrorX.multiply(scale.multiply(translateA)));
  }

	/**
	 * Liefert eine Matrix, die alle notwendigen Transformationen 
	 * beinhaltet (Translation, Skalierung und Translation), um ein 
	 * _map-Rechteck in ein _win-Rechteck abzubilden
	 * 
	 * @param _map Das Rechteck in Weltkoordinaten
	 * @param _win Das Rechteck in Bildschirmkoordinaten
	 * @return Die Transformationsmatrix
	 */
  public static GeoTransformationMatrix zoomToFitNoMirror(Rectangle _map,
                                                          Rectangle _win) {
    double scaleFactor = getZoomFactor(_map, _win);
    
    GeoTransformationMatrix translateA = 
      GeoTransformationMatrix.translate(-_map.x, -_map.y);
    GeoTransformationMatrix scale =
      GeoTransformationMatrix.scale(scaleFactor);
    GeoTransformationMatrix translateB = 
      GeoTransformationMatrix.translate(0,_win.height);
      
    // Matrizen in umgekehrter Reihenfolge zusammenmultiplizieren
    return translateB.multiply(scale.multiply(translateA));
  }

	/**
	 * Liefert eine Matrix, die eine vorhandene Transformations-
	 * matrix erweitert, um an einem bestimmten Punkt um einen 
	 * bestimmten Faktor in die Karte hinein- bzw. heraus zu 
	 * zoomen
	 * 
	 * @param _old       Die zu erweiternde Transformationsmatrix
	 * @param _zoomPt    Der Punkt an dem gezoomt werden soll
	 * @param _zoomScale Der Zoom-Faktor um den gezoomt werden 
	 *                   soll
	 * @return Die neue Transformationsmatrix
	 */
  public static GeoTransformationMatrix zoomToPoint(
    GeoTransformationMatrix _matrix,
    Point                   _p,
    double                  _scale) 
  {
    // auf den 0-Punkt verschieben ... 
    GeoTransformationMatrix t1 = GeoTransformationMatrix.translate(-_p.x, -_p.y);
    // auf die richtige Groesse zoomen ... 
    GeoTransformationMatrix z  = GeoTransformationMatrix.scale((double)_scale);
    // zurueck zum Punkt verschieben ... 
    GeoTransformationMatrix t2 = GeoTransformationMatrix.translate(_p.x, _p.y);
    // Matrizen in umgekehrter Reihenfolge zusammenmultiplizieren
    return t2.multiply(z.multiply(t1.multiply(_matrix)));
  }
  
	/**
	 * Liefert eine Matrix, die eine vorhandene Transformations-
	 * matrix erweitert, um an einem bestimmten Punkt um einen 
	 * bestimmten Faktor in die Karte hinein- bzw. heraus zu 
	 * zoomen und diesen Ausschitt anschliessend an einen anderen Punkt
	 * auf dem Bildschirm zu verschieben (i.d.R. den Bildschirmmittelpunkt).
	 * 
	 * @param _old       Die zu erweiternde Transformationsmatrix
	 * @param _pt1       Der Punkt an dem gezoomt werden soll
	 * @param _pt2       Der Punkt an den der Ausschnitt verschoben werden soll. I.d.R.
	 *                   der Bildschirmmittelpunkt
	 * @param _zoomScale Der Zoom-Faktor um den gezoomt werden 
	 *                   soll
	 * @return Die neue Transformationsmatrix
	 */
  public static GeoTransformationMatrix zoomToPoint(
    GeoTransformationMatrix _matrix,
    Point                   _p1,
    Point                   _p2,
    double                  _scale) 
  {
    // auf den 0-Punkt verschieben ... 
    GeoTransformationMatrix t1 = GeoTransformationMatrix.translate(-_p1.x, -_p1.y);
    // auf die richtige Groesse zoomen ... 
    GeoTransformationMatrix z  = GeoTransformationMatrix.scale((double)_scale);
    // zurueck zum Punkt verschieben ... 
    GeoTransformationMatrix t2 = GeoTransformationMatrix.translate(_p2.x, _p2.y);
    // Matrizen in umgekehrter Reihenfolge zusammenmultiplizieren
    return t2.multiply(z.multiply(t1.multiply(_matrix)));
  }

  public static void main(String[] _argv) {
    GeoTransformationMatrix matrixA = new GeoTransformationMatrix(7, 2 , 4, 12, -4, 1, 23, 3,3);
    GeoTransformationMatrix matrixI = matrixA.invers();
    
    System.out.println(matrixA);
    System.out.println(matrixI);
    
    Rectangle world = new Rectangle(47944531,
                                    608091485,
                                    234500,
                                    213463);
    
    Rectangle win = new Rectangle(0, 0, 640, 480);
    
    GeoTransformationMatrix matrix = GeoTransformationMatrix.zoomToFit(world,win);
    matrixI = matrix.invers();
    
    Rectangle transform  = matrix.multiply(world);
    Rectangle transformI = matrixI.multiply(transform);
    
    System.out.println("world bounds         --> " + world);
    System.out.println("win   bounds         --> " + win);
    System.out.println("transformed bounds   --> " + transform);
    System.out.println("retransformed bounds --> " + transformI);                               
  }
}
