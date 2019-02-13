/**
 * 
 */
package service;

import contract.Service;
import contract.Constants.ServiceName;

/**
 * @author Rejeesh G.
 *
 */
public class BrokerService implements Service {
	
	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return ServiceName.BROKER.getName();
	}

}
