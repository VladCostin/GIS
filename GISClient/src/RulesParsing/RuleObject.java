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
	Method m_command;
	
	
	/**
	 * name of the condition, only for testing
	 */
	String m_name_condition;
	
	
	/**
	 * the class the methods are called on
	 */
	Object m_invokeObject;
	
	/**
	 * @param _conditionFromFile : received the tree made by the aprser
	 * @param _command : the Method to be called if the condition is fulfilled
	 * @param _name_condition : name of the condition which should trigger a method
	 */
	public RuleObject(TreeNode _conditionFromFile, Method _command, String _name_condition, Object m_classInvokeMethod)
	{
		m_condition = _conditionFromFile;
		m_command = _command;
		m_name_condition = _name_condition;
		m_invokeObject = m_classInvokeMethod;

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
		System.out.println(result + " " + m_name_condition);
		return result;
		
	//	return false;
	}
	
	/**
	 * calling the method in case the context situation is valid
	 */
	public void execute()
	{
		
		try {
			m_command.invoke(m_invokeObject, null);
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
		
	/*	Class cls = Class.forName("GIS.GISComponent");
		Object obj = cls.newInstance();
 
		//call the printIt method
		Method method = cls.getDeclaredMethod("printIt", );
		method.invoke(obj, null);
		*/
	}
	
}
