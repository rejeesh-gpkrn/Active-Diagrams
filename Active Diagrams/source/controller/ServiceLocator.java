/**
 * 
 */
package controller;

import contract.Service;

/**
 * @author Rejeesh G.
 *
 */
public class ServiceLocator {
	private static Cache m_cache;
	
	static {
		m_cache = new Cache();
	}
	
	public static Service getService(String serviceName) {
		
		Service recvService = m_cache.getService(serviceName);
		if (recvService != null && recvService.isReady()) {
			return recvService;
		}
		
		return null;
	}
	
	public static void addService(Service newService) {
		
		m_cache.addService(newService);
	}
}
