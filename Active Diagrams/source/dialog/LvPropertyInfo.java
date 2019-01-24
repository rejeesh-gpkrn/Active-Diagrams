package dialog;

public class LvPropertyInfo {
	
	private String m_name;
	
	private String m_value;
	
	public LvPropertyInfo(String name) {
		m_name = name;
	}

	public String getValue() {
		return m_value;
	}

	public void setValue(String value) {
		this.m_value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return m_name;
	}

}
