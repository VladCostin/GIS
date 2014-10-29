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
	
	public TemporalContext() {
		// TODO Auto-generated constructor stub
	}
	
	public TemporalContext(int _day, int _hour, int _minute)
	{
		m_day = _day;
		
		m_hour = _hour;
		
		m_minute = _minute;
	}

}
