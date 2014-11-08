package ContextModel;


/**
 * the class father of every context element types
 * @author Vlad Herescu
 *
 */
public abstract class ContextElement implements InterfaceContext {
	
	/**
	 * information describing the context element
	 */
	MetadataContext m_data;
	
	/**
	 * the id of the element;
	 */
	int id;

	@Override
	public boolean checkCompatibility(InterfaceContext _context) {
		// TODO Auto-generated method stub
		return false;
	}

	public MetadataContext getM_data() {
		return m_data;
	}

	public void setM_data(MetadataContext _data) {
		m_data = _data;
	}

	public String getId() 
	{
		return Integer.toString(id);
	}

	public void setId(int id) 
	{
		this.id = id;
	}
	

}
