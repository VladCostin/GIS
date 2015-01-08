package RulesParsing;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import ContextManagementComponent.ContextSituation;
import ContextModel.ElementType;
import ContextModel.InterfaceContext;
import GIS.GISComponent;
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
	Method m_action;
	
	/**
	 * the method to be executed in case the condition is no longer fulfilled by the current context 
	 */
	Method m_reverse;
	
	
	/**
	 * name of the condition, only for testing
	 */
	String m_name_condition;
	
	
	/**
	 * the class the methods are called on
	 */
	Object m_invokeObject;
	
	
	/**
	 * true if the condition was fulfilled, false if the condition was not fulfilled
	 */
	boolean m_lastConditionState;


	/**
	 * @param _conditionFromFile : received the tree made by the aprser
	 * @param _command : the Method to be called if the condition is fulfilled
	 * @param reverseAction 
	 * @param _name_condition : name of the condition which should trigger a method
	 */
	public RuleObject(TreeNode _conditionFromFile, Method _command,
					  Method reverseAction, String _name_condition, Object m_classInvokeMethod)
	{
		m_condition = _conditionFromFile;
		m_action = _command;
		m_reverse = reverseAction;
		m_name_condition = _name_condition;
		m_invokeObject = m_classInvokeMethod;
		m_lastConditionState = false;

	}
	
	/**
	 * @param _situation : the situation given by context Management
	 * @return : true if the situation fullfis the conditions to execute the method of the rule
	 */
	public Boolean valid(ContextSituation _situation)
	{
		
		Boolean result;
		HashMap<ElementType, InterfaceContext>  aa = _situation.getM_contextData();

		m_condition.setVariablesParameters(_situation); 
		result = (Boolean) m_condition.calculate();


		
		return result;

	}
	
	/**
	 * calling the method in case the context situation is valid
	 */
	public void execute()
	{
		
		try {
			if(m_lastConditionState == true)
				m_action.invoke(m_invokeObject, null);
			else
				m_reverse.invoke(m_invokeObject, null);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	/**
	 * @return the m_lastConditionState
	 */
	public boolean isM_lastConditionState() {
		return m_lastConditionState;
	}

	/**
	 * @param m_lastConditionState the m_lastConditionState to set
	 */
	public void setM_lastConditionState(boolean m_lastConditionState) {
		this.m_lastConditionState = m_lastConditionState;
	}
	
}
