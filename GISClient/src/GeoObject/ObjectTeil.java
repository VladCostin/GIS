package GeoObject;
import java.util.ArrayList;


/**
 * represents a part of a GeoObject
 * a ObjectTeil belongs to a single GeoObject
 * @author Vlad Herescu
 *
 */
public abstract class ObjectTeil implements InterfaceDraw{

	/**
	 * the id of the teil
	 */
	private int _teilNr;
	
	/**
	 * each part of the GeoObject has its own small parts
	 */
	private ArrayList<ObjectTeil> _subParts;
	
	/**
	 * initializing the object
	 */
	public ObjectTeil() {
	
		_subParts = new ArrayList<ObjectTeil>();
	}
	public ObjectTeil(ArrayList<ObjectTeil> children)
	{
		_subParts = children;
	}

	public int get_teilNr() {
		return _teilNr;
	}

	public void set_teilNr(int _teilNr) {
		this._teilNr = _teilNr;
	}

	public ArrayList<ObjectTeil> get_subParts() {
		return _subParts;
	}

	public void set_subParts(ArrayList<ObjectTeil> _subParts) {
		this._subParts = _subParts;
	}

	
	
}
