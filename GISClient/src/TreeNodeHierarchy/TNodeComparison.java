package TreeNodeHierarchy;

import ContextManagementComponent.ContextSituation;
import ContextModel.ElementType;

/**
 * this node will return the value true or false
 * @author Vlad Herescu
 *
 */
public class TNodeComparison extends TreeNode{

	/**
	 * the value of the node : <, ==, >
	 */
	String m_compareValue;
	
	/**
	 * @param _compareValue : received value from parsing
	 */
	public TNodeComparison(String _compareValue)
	{
		m_compareValue = _compareValue;
		m_compareValue = m_compareValue.replaceAll(" ", "");
	}
	
	@Override
	public Object calculate() {
		
		TNodeConstant constant;
		
		if(m_childs[0]  instanceof TNodeContextVar)
		{
			TNodeContextVar var;
			
			var = (TNodeContextVar) m_childs[0];
			constant = (TNodeConstant) m_childs[1];
			
			String valueVar = (String) var.calculate();
			String valueConst = (String) constant.calculate();
			
			if(valueVar == null || valueConst == null)
				return null;
			
			if(var.m_type == ElementType.TIME_CTXT)
				return calculateValueString(valueVar, valueConst);
			return calculateValueFloat(Float.valueOf(valueVar), Float.valueOf(valueConst));
			
		}
		if(m_childs[1] instanceof TNodeContextVar)
		{
			TNodeContextVar var;
		
			
			var = (TNodeContextVar) m_childs[1];
			constant = (TNodeConstant) m_childs[0];
			
			String valueVar = (String) var.calculate();
			String valueConst = (String) constant.calculate();
			
			if(valueVar == null || valueConst == null)
				return null;
			
			if(var.m_type == ElementType.TIME_CTXT)
				return calculateValueString(valueVar, valueConst);
			return calculateValueFloat(Float.valueOf(valueConst), Float.valueOf(valueVar));
		}
		if(m_childs[0] instanceof TNodeDistance)
		{
			TNodeDistance var;
			
			var = (TNodeDistance) m_childs[0];
			constant = (TNodeConstant) m_childs[1];
			
			String valueVar = (String) var.calculate();
			String valueConst = (String) constant.calculate();
			
			if(valueVar == null || valueConst == null)
				return null;


			return calculateValueFloat(Float.valueOf(valueVar), Float.valueOf(valueConst));
		}
		return true;
		
		
		
		
	}

	private Object calculateValueString(String firstValue, String constantValue) {

		if(firstValue.equals(constantValue))
				return true;
		return false;
			

	}
	private Object calculateValueFloat(Float value1, Float value2)
	{
				
	//	System.out.println("valorile float sunt : " + value1 + " " + value2 + " -" + m_compareValue+"-");
		
		
		switch(m_compareValue)
		{
			case "LESS":
				if(value1 < value2)
					return true;
				return false;
			case "GR":
				if(value1 > value2)
					return true;
				return false;
			case "EQ":
				if(value1 == value2)
					return true;
				return false;
				
		}
		
		return true;
	}

	@Override
	public void setVariablesParameters(ContextSituation _contextElements) {
		
	//	System.out.println("    Comparison:" + m_compareValue);
		m_childs[0].setVariablesParameters(_contextElements);
		m_childs[1].setVariablesParameters(_contextElements);
		
	}

}
