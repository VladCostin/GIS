package GIS;

import java.awt.Color;
import java.awt.Graphics;

import GeoObject.GeoObject;
import GeoObject.InterfaceDraw;



/**
 * calls the methods that draw the objects : polygons, lines, points
 * @author Vlad Herescu
 *
 */
public class DrawingContext {

  /**
   * Set the color values for the geo objects based on their type
 * @param _obj : the object to be drawn
 * @param _g : used for drawing the object
 * @param _matrix : used to bring the coordinates of the objects to the ones of the frame
   */
  public static void drawObject(GeoObject _obj, 
  								Graphics  _g, 
  								GeoTransformationMatrix _matrix) {
	int type; 
	
	if(_obj instanceof POIObject)
	{
		System.out.println("obiect");
		((POIObject) _obj).paint(_g, _matrix);
		return;
	}
	  
  	if (_obj != null && _g != null && _matrix != null) 
  	{
  		type = _obj.getType();
  		for(InterfaceDraw draw : _obj._components)
  		{	
  	    	//draw.multiply(_matrix);
  			_matrix.multiplyInterfaceDraw(draw); 
  	    	
  	    	if(CoreData._hashMapFillColor.get(type) == null)
  	    		draw.setColorObject(_g, Color.black,null);
  	    	
  	    	draw.setColorObject(_g, CoreData._hashMapFillColor.get(type), CoreData._hashMapBorderColor.get(type));
  		}
  		

  	}
  	else
  	  System.out.println("encountered null object");								 
  }
  
  
  
}


