/**
 * 
 */
package service;

import contract.Service;
import controller.LOInterfaceService;
import controller.ServiceLocator;
import contract.Constants.ServiceName;

/**
 * @author Rejeesh G.
 *
 */
public class BrokerService implements Service {
	
	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public String getName() {
		return ServiceName.BROKER.getName();
	}
	
	public void addService_LO(Service svcLO) {
		LOInterfaceService documentHandler = (LOInterfaceService)svcLO;
		documentHandler.initializeXComponent();
		
		ServiceLocator.addService(svcLO);
	}
	
	public void addService_Execute() {
		Service srv = ServiceLocator.getService(ServiceName.EXECUTION.getName());
		if (srv == null) { 
			ExecutionService executionSrv = new ExecutionService();
			ServiceLocator.addService(executionSrv);
		}
	}
	
	public void addService_Read() {
		Service srv = ServiceLocator.getService(ServiceName.READ.getName());
		if (srv == null) {
			ReadService readSrv = new ReadService();
			ServiceLocator.addService(readSrv);
		}
	}
}
