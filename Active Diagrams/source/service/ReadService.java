/**
 * 
 */
package service;

import contract.Service;
import contract.Constants.ServiceName;
import controller.ServiceLocator;

/**
 * @author Rejeesh G.
 *
 */
public class ReadService implements Service {

	/* (non-Javadoc)
	 * @see contract.Service#isReady()
	 */
	@Override
	public boolean isReady() {
		Service srvLO = ServiceLocator.getService(ServiceName.LO.getName());
		return (srvLO != null);
	}

	/* (non-Javadoc)
	 * @see contract.Service#getName()
	 */
	@Override
	public String getName() {
		return ServiceName.READ.getName();
	}

}
