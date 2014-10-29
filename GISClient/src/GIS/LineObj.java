package GIS;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;



/**
 * a line on the map
 * @author Vlad Herescu
 *
 */
public class LineObj extends ObjectTeil{
	
	/**
	 * the points representing the line
	 */
	private ArrayList<Point> _points;
	
	/**
	 * the points made after multiplying
	 */
	ArrayList<Point> _points_multiply;
	
	/**
	 * the set of coordinates x of the points 
	 */
	int x[];
	
	/**
	 * the set of coordinates y of the points
	 */
	int y[];

	@Override
	public void setColorObject(Graphics _g, Color fill, Color border) {
		
		int i;
		int x1, y1,x2,y2;
		
		Graphics2D g2d = (Graphics2D) _g;
		/*int x[] = new int[_points_multiply.size()];
		int y[] = new int[_points_multiply.size()];
		
		for(i = 0; i <_points_multiply.size() ; i++)
		{
			/*x1 = _points_multiply.get(i).x;
			y1 = _points_multiply.get(i).y;
			
			x2 = _points_multiply.get(i+1).x;
			y2 = _points_multiply.get(i+1).y;
			
	    	g2d.setColor(fill);
	    	 g2d.setStroke(new BasicStroke(2));
	    	g2d.drawLine(x1, y1, x2, y2);
			
			x[i] = _points_multiply.get(i).x;
			y[i] = _points_multiply.get(i).y;

		}*/
		
		g2d.setColor(fill);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawPolyline(x, y, x.length); 
		
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
		
		/*for(Point _point : _points)
		{
			_points_multiply.add(_matrix.multiply(_point));
		}*/
		
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
	

}
