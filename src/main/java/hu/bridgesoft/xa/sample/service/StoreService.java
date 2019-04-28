package hu.bridgesoft.xa.sample.service;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.domain.order.Order;
import hu.bridgesoft.xa.sample.exception.NoRollbackException;
import hu.bridgesoft.xa.sample.exception.StoreException;

public interface StoreService {
	
	void store(Customer customer, Order order) throws Exception;
	
	void storeWithStoreException(Customer customer, Order order) throws StoreException;
	
	void storeWithNoRollbackException(Customer customer, Order order) throws NoRollbackException;
	
	void transferWithStoreException() throws StoreException;
	void transferWithNoRollbackException() throws NoRollbackException;
	void transfer();

}
