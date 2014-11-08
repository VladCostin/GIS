package GeoObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import GISCalculation.GeoTransformationMatrix;


/**
 * a point on the map
 * @author Vlad Herescu
 *
 */
public class PointObj extends ObjectTeil {


	
	
	/**
	 * the coordinate x of the point in Gaus Kruger system
	 */
	private int _mx;
	
	/**
	 * the coordinate y of the point in Gaus Kruger system
	 */
	private int _my;
	
	private int _multiply_mx;

	private int _multiply_my;
	
	
	/**
	 * initializing the point
	 */
	public PointObj() 
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param _x : the coordinate x of the point in Gaus Kruger system
	 * @param _y : the coordinate y of the point in Gaus Kruger system
	 */
	public PointObj(int _x, int _y)
	{
		_mx = _x;
		_my = _y;

	}

	
	@Override
	public void multiply(GeoTransformationMatrix _matrix) {
		Point p_m = _matrix.multiply(new Point(_mx, _my));
		_multiply_mx = p_m.x;
		_multiply_my = p_m.y;
	}

	@Override
	public Rectangle getBounds() {
		
		// TODO Auto-generated method stub
		return new Ellipse2D.Float(_mx, _my, 10.0f, 10.0f).getBounds();
	}

	@Override
	public void setColorObject(Graphics _g, Color fill, Color border) {
	
		
		System.out.println(_multiply_mx + " aaaaaaaaa " + _multiply_my);
		
		_g.setColor(fill);
		_g.fillOval(_multiply_mx, _multiply_my, 10, 10);
	}

	public int get_my() {
		return _my;
	}

	public void set_my(int _my) {
		this._my = _my;
	}

	public int get_mx() {
		return _mx;
	}

	public void set_mx(int _mx) {
		this._mx = _mx;
	}

	public int get_multiply_mx() {
		return _multiply_mx;
	}

	public void set_multiply_mx(int _multiply_mx) {
		this._multiply_mx = _multiply_mx;
	}

	public int get_multiply_my() {
		return _multiply_my;
	}

	public void set_multiply_my(int _multiply_my) {
		this._multiply_my = _multiply_my;
	}

}
