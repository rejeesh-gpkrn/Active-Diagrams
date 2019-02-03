/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;

import contract.Service;

/**
 * @author Rejeesh G.
 *
 */
public class Cache {
	private List<Service> m_services;
	
	public Cache() {
		m_services = new ArrayList<Service>();
	}
	
	public Service getService(String requestServiceName) {
		
		for(Service service : m_services) {
			if (service.getName().equalsIgnoreCase(requestServiceName)) {
				return service;
			}
		}
		
		return null;
	}
	
	public void addService(Service newService) {
		
		boolean newServiceExists = false;
		
		for(Service service : m_services) {
			if (service.getName().equalsIgnoreCase(newService.getName())) {
				newServiceExists = true;
			}
		}
		
		if (!newServiceExists) {
			m_services.add(newService);
		}
	}
}
