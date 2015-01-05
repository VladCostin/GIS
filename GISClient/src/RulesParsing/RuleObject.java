package RulesParsing;

import java.lang.reflect.Method;
import java.util.HashMap;

import ContextManagementComponent.ContextSituation;
import ContextModel.ElementType;
import ContextModel.InterfaceContext;
import TreeNodeHierarchy.TreeNode;

/**
 * object consisting in the an action to be executed and the condition needed to be fulfilled
 * @author Vlad Herescu
 *
 */
public class RuleObject 
{
	/**
	 * the tree created after parsing
	 */
	TreeNode m_condition;
	/**
	 * the method to be executed in case the condition is fulfilled by the current context
	 */
	Method m_command;
	
	/**
	 * @param _conditionFromFile : received the tree made by the aprser
	 * @param _command : the Method to be called if the condition is fulfilled
	 */
	public RuleObject(TreeNode _conditionFromFile, Method _command)
	{
		m_condition = _conditionFromFile;
		m_command = _command;
	}
	
	/**
	 * @param _situation : the situation given by context Management
	 * @return : true if the situation fullfis the conditions to execute the method of the rule
	 */
	public boolean valid(ContextSituation _situation)
	{
		
		boolean result;
		
		HashMap<ElementType, InterfaceContext>  aa = _situation.getM_contextData();
//		System.out.println(aa.get(ElementType.TEMP_CTXT).getValue() + " " + aa.get(ElementType.TIME_CTXT).getValue() + " " + aa.get(ElementType.VEL_CTXT).getValue());

		m_condition.setVariablesParameters(_situation); 
		
		result = (Boolean) m_condition.calculate();
		System.out.println(result);
		
		
		return result;
	}
	
	/**
	 * calling the method in case the context situation is valid
	 */
	public void execute()
	{
		
	}
	
}
