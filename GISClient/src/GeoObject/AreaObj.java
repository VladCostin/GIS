package GeoObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;


/**
 * represents a polygon on the map
 * @author Vlad Herescu
 */
public class AreaObj extends ObjectTeil{

	/**
	 * the polygon representing the Area object
	 */
	private Polygon _polygon;
	
	/**
	 * the polygon obtained after multiplying with the GeoTransformationMatrix
	 */
	public Polygon _polyMultipled;
	

	@Override
	public void setColorObject(Graphics _g, Color fill,
			Color border) {
		
		
    	_g.setColor(fill);
    	_g.fillPolygon(_polyMultipled);
    	_g.setColor(border);
    	_g.drawPolygon(_polyMultipled);
		
	}

	/*@Override
	public void multiply(GeoTransformationMatrix _matrix) {
		_polyMultipled = _matrix.multiply(_polygon);
		
	}*/

	@Override
	public Rectangle getBounds() {
		return _polygon.getBounds();
	}

	public Polygon get_polygon() {
		return _polygon;
	}

	public void set_polygon(Polygon _polygon) {
		this._polygon = _polygon;
	}

	
	
}
