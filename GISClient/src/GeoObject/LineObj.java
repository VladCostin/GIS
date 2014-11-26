package GeoObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import GISCalculation.GeoTransformationMatrix;



/**
 * a line on the map
 * @author Vlad Herescu
 *
 */
public class LineObj extends ObjectTeil{
	
	/**
	 * the points representing the line
	 */
	ArrayList<Point> _points;
	
	/**
	 * the points made after multiplying
	 */
	ArrayList<Point> _points_multiply;
	
	/**
	 * the set of coordinates x of the points 
	 */
	private int x[];
	
	/**
	 * the set of coordinates y of the points
	 */
	private int y[];

	@Override
	public void setColorObject(Graphics _g, Color fill, Color border) {
		
	/*	Graphics2D g2d = (Graphics2D) _g;
		
		g2d.setColor(fill);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawPolyline(x, y, x.length); */
		
		_g.drawPolyline(x, y, x.length);
		
	}

	@Override
	public void multiply(GeoTransformationMatrix _matrix) {
		
		int i;
		//_points_multiply = new ArrayList<Point>();
		
		x = new int[_points.size()];
		y = new int[_points.size()];
		
		for(i = 0; i < _points.size(); i++)
		{
			Point point = _matrix.multiply(_points.get(i));
			x[i] = point.x;
			y[i] = point.y;
		}	
	}

	@Override
	public Rectangle getBounds() {
		int xMinim, yMinim, xMaxim,yMaxim;
		
		xMaxim = xMinim = _points.get(0).x;
		yMaxim = yMinim = _points.get(0).y;
		
		for(Point p : _points)
		{
			if(p.x > xMaxim)
				xMaxim = p.x;
			if(p.x < xMinim)
				xMinim = p.x;
			
			if(p.y > yMaxim)
				yMaxim = p.y;
			if(p.y < yMinim)
				yMinim = p.y;
			
			
		}
		return new Rectangle(xMinim, yMinim, xMaxim - xMinim, yMaxim - yMinim);
		
	}

	public ArrayList<Point> get_points() {
		return _points;
	}

	public void set_points(ArrayList<Point> _points) {
		this._points = _points;
	}

	public int[] getX() {
		return x;
	}

	public void setX(int x[]) {
		this.x = x;
	}

	public int[] getY() {
		return y;
	}

	public void setY(int y[]) {
		this.y = y;
	}
	

}
