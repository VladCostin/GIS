package ContextModel;

/**
 * specifies when the time of the event
 * @author Vlad Herescu
 *
 */
public class TemporalContext extends ContextElement{
	
	/**
	 * the day of the context 
	 */
	int m_day;
	
	/**
	 * the hour of the context
	 */
	int m_hour;
	
	/**
	 * the minute of the context
	 */
	int m_minute;
	
	/**
	 * if the day is night or day;
	 */
	String m_momentDay;
	
	/**
	 * initiating the context, default constructor
	 */
	public TemporalContext() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor, specifing the hour and the minute
	 * @param _hour 
	 * @param _minute
	 */
	public TemporalContext(int _hour, int _minute)
	{
		m_hour = _hour;
		m_minute = _minute;
	}
	/**
	 * constructor, specifying the hour and the minute
	 * @param _day 
	 * @param _hour 
	 * @param _minute
	 */
	public TemporalContext(int _day, int _hour, int _minute)
	{
		m_day = _day;
		
		m_hour = _hour;
		
		m_minute = _minute;
	}
	
	/**
	 * @param _momentDay
	 */
	public TemporalContext(String _momentDay)
	{
		m_momentDay = _momentDay;
	}

	@Override
	public String getValue() {
		
		return m_momentDay;
		
		// TODO Auto-generated method stub
		//return Integer.toString(m_day)+":"+ Integer.toString(m_hour)+":"+Integer.toString(m_minute);
	}

	@Override
	public String getType() {
		return ElementType.TEMP_CTXT.name();
	}

	

}
