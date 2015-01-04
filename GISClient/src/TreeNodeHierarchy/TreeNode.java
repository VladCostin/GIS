package TreeNodeHierarchy;

import ContextManagementComponent.ContextSituation;

/**
 * @author Vlad Herescu
 *
 */
public abstract class TreeNode 
{
	/**
	 * the two children of the node
	 * method calculate will be called upon them. the values returned will be used
	 * to return the value of the root
	 */
	protected TreeNode[] m_childs;

	
	/**
	 * at the set variable Parameter, it will be checked the type of the node
	 * to know which Context element it will take from the map
	 */
	protected TreeNode m_root;
	
	
	/**
	 * before calculating the value of the tree, the variable nodes must receive the values
	 * from the context situation
	 * @param _contextElements : the map consisting in a set of values for each context element
	 */
	public abstract void setVariablesParameters(ContextSituation _contextElements);
	
	/**
	 * it calculates the value of the tree or of the subtree
	 * if the Node is Comparison or a Connector, it returns true or false
	 * if the Node represents the distance, it will return a float
	 * @return : 
	 */
	public abstract Object calculate();
	
	public TreeNode[] getM_childs() {
		return m_childs;
	}

	public void setM_childs(TreeNode[] m_childs) {
		this.m_childs = m_childs;
	}

	public TreeNode getM_root() {
		return m_root;
	}

	public void setM_root(TreeNode m_root) {
		this.m_root = m_root;
	}
	
	
}
