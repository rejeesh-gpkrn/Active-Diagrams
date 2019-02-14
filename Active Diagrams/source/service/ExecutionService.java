/**
 * 
 */
package service;

import contract.Constants.ExecutionStatus;
import contract.Constants.ServiceName;
import controller.ServiceLocator;
import contract.Service;
import utilities.MessageUtil;

/**
 * @author Rejeesh G.
 *
 */
public class ExecutionService implements Service {
	
	@Override
	public boolean isReady() {
		Service srvLO = ServiceLocator.getService(ServiceName.LO.getName());
		return (srvLO != null);
	}

	@Override
	public String getName() {
		return ServiceName.EXECUTION.getName();
	}
	
	public ExecutionStatus execute() {
		ExecutionStatus status = ExecutionStatus.UNKNOWN;
		
		MessageUtil.showWarningBox("Execution under construction.");
		
		return status;
	}
	
}
