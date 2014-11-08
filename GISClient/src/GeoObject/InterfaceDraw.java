package GeoObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GISCalculation.GeoTransformationMatrix;


/**
 * a common interface for the classes Area, Line, 
 * @author Vlad Herescu
 */
public interface InterfaceDraw {

	 /**
	 * @param _g : the drawing object
	 * @param fill : the color to fill the object
	 * @param border : the color to draw the border of the object
	 */
	void setColorObject(Graphics _g, Color fill, Color border);

	/**
	 * @param _matrix : the matrix used to bring the data to the frame dimensions
	 */
	//void multiply(GeoTransformationMatrix _matrix);
	
	/**
	 * @return : the bounds of the object
	 */
	Rectangle getBounds();
}
