package TreeNodeHierarchy;

public abstract class TreeNode 
{
	protected TreeNode[] m_childs;

	protected TreeNode m_root;
	
	public abstract Object calculate();
	
	
//	public abstract void setVariablesParameters(ContextObject[] _contextElements);
	
	
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
