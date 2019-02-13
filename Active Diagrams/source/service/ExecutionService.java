/**
 * 
 */
package service;

import contract.Constants.ExecutionStatus;
import contract.Constants.ServiceName;
import contract.Service;
import utilities.MessageUtil;

/**
 * @author Rejeesh G.
 *
 */
public class ExecutionService implements Service {
	
	@Override
	public boolean isReady() {
		// TODO Modify to check whether the system is 
		//		ready to execute.
		return true;
	}

	@Override
	public String getName() {
		return ServiceName.EXECUTION.getName();
	}
	
	public ExecutionStatus execute() {
		ExecutionStatus status = ExecutionStatus.UNKNOWN;
		
		MessageUtil.showMessageBox("Execution under construction.");
		
		return status;
	}
	
}
