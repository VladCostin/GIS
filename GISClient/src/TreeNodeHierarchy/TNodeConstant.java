package TreeNodeHierarchy;

import ContextManagementComponent.ContextSituation;

/**
 * the leaf of the tree
 * it contains the value of a constant, such as Nacht, Tag, 
 * @author Vlad Herescu
 *
 */
public class TNodeConstant extends TreeNode{

	/**
	 * the value of the constant
	 */
	String m_value;
	
	/**
	 * @param _value
	 */
	public TNodeConstant(String _value)
	{
		m_value = _value;
	}
	
	@Override
	public Object calculate() {
		// TODO Auto-generated method stub
		return m_value;
	}

	@Override
	public void setVariablesParameters(ContextSituation _contextElements) {
		
		
		
	}

}
