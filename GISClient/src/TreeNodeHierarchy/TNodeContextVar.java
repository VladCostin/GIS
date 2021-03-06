package TreeNodeHierarchy;

import ContextManagementComponent.ContextSituation;
import ContextModel.ElementType;
import ContextModel.InterfaceContext;

/**
 * this node represents a leaf 
 * it gets the value from the context situation, depending on its type
 * @author Vlad Herescu
 *
 */
public class TNodeContextVar extends TreeNode{
	
	/**
	 * 
	 * indicate the type of the variable extracted from the context situation
	 */
	ElementType m_type;
	
	/**
	 * the context represented 
	 */
	InterfaceContext m_context;
	
	
	/**
	 * @param _token : specifiyng the type of the context 
	 */
	public TNodeContextVar(String _token)
	{
		m_type = ElementType.valueOf(_token);
		
		System.out.println("VALOAREA PRIMITA DIN XML ESTE " + m_type);
		
			
	}
	
	@Override
	public Object calculate() {
	
		if(m_type.equals(ElementType.LOC_CTXT) || m_type.equals(ElementType.POI_CTXT)  )
			return m_context;
			
		return m_context.getValue();
		
		
	}


	@Override
	public void setVariablesParameters(ContextSituation _contextElements) {
	
		
	
		m_context = _contextElements.getM_contextData().get(m_type);
	//	System.out.println("   				Context Var:" +  m_context.getValue());
	}

}
