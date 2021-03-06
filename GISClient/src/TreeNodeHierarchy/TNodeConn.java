package TreeNodeHierarchy;

import ContextManagementComponent.ContextSituation;

/**
 * The Node associated with connection tokens such as AND or Or
 * @author Vlad Herescu
 *
 */
public class TNodeConn extends TreeNode {

	/**
	 * the value of the token : Or or And
	 */
	String m_ConnectionValue;
	
	/**
	 * @param _connectionValue : value received from parsing
	 * 
	 */
	public TNodeConn(String _connectionValue)
	{
		m_ConnectionValue = _connectionValue;
	}
	
	
	@Override
	public Object calculate() {
		
		
		Boolean node1Value = (Boolean) this.m_childs[0].calculate();
		Boolean node2Value = (Boolean) this.m_childs[1].calculate();
		
		if(node1Value == null || node2Value == null)
			return null;
		
		switch(m_ConnectionValue)
		{
			case  "AND" :
				if(node1Value == true &&  node2Value == true)
					return true;
				else
					return false;
				
			case "OR" :
				if(node1Value == true ||   node2Value == true)
					return true;
				else
					return false;
	
		
		}
		return true;
	}


	@Override
	public void setVariablesParameters(ContextSituation _contextElements) {
		
		
		
		//System.out.println("Connection:" + m_ConnectionValue);
		
		this.m_childs[0].setVariablesParameters(_contextElements);
		this.m_childs[1].setVariablesParameters(_contextElements);
		
	}

}
