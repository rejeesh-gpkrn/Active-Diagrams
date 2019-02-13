/**
 * 
 */
package contract;

/**
 * @author Rejeesh G.
 *
 */
public final class Constants {
	public enum ExecutionStatus {
		UNKNOWN,
		STARTED,
		INTERRUPTED,
		ONGOING,
		FINISHED
	}
	
	/**
	 * All defined services have names here.
	 *
	 */
	public enum ServiceName {
		UNKNOWN ("UNKNOWN"),
		BROKER ("BROKER"),
		READ ("READ"),
		WRITE ("WRITE"),
		EXECUTION ("EXECUTION"),
		LO ("LIBREOFFICE");
		
		private String name;
		
		ServiceName (String name) {
			this.name = name;
		}
		
		/**
		 * @return name of enumeration.
		 */
		public String getName() {
			return name;
		}
	}
}
