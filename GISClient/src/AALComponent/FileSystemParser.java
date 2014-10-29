package AALComponent;

import ContextModel.InterfaceContext;

/**
 * the concrete creator that will produce a context element
 * @author Vlad Herescu
 *
 */
public class FileSystemParser extends Parser{

	@Override
	public InterfaceContext factoryMethod() {
		System.out.println("intra aici");
		return null;
	}

}
