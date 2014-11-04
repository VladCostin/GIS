package ContextModel;

/**
 * the class father of every context element types
 * @author Vlad Herescu
 *
 */
public class ContextElement implements InterfaceContext {
	
	/**
	 * information describing the context element
	 */
	private MetadataContext m_data;

	@Override
	public boolean checkCompatibility(InterfaceContext _context) {
		// TODO Auto-generated method stub
		return false;
	}

	public MetadataContext getM_data() {
		return m_data;
	}

	public void setM_data(MetadataContext m_data) {
		this.m_data = m_data;
	}
	

}
