package TreeNodeHierarchy;

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
		// TODO Auto-generated method stub
		return null;
	}

}
