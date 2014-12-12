package TreeNodeHierarchy;

/**
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
	}
	
	@Override
	public Object calculate() {
		// TODO Auto-generated method stub
		return null;
	}

}
