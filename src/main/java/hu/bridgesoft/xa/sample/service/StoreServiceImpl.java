package hu.bridgesoft.xa.sample.service;

import hu.bridgesoft.xa.sample.domain.customer.CapitalAccount;
import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.domain.order.Order;
import hu.bridgesoft.xa.sample.domain.order.RedPacketAccount;
import hu.bridgesoft.xa.sample.exception.NoRollbackException;
import hu.bridgesoft.xa.sample.exception.StoreException;
import hu.bridgesoft.xa.sample.repository.customer.CapitalAccountRepository;
import hu.bridgesoft.xa.sample.repository.customer.CustomerRepository;
import hu.bridgesoft.xa.sample.repository.order.OrderRepository;
import hu.bridgesoft.xa.sample.repository.order.RedPacketAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
    CapitalAccountRepository capitalAccountRepository;
	
	@Autowired
    RedPacketAccountRepository redPacketAccountRepository;
	
	@Override
	@Transactional
	public void store(Customer customer, Order order) {
		customerRepository.save(customer);
		orderRepository.save(order);
	}

	@Transactional(rollbackFor = StoreException.class)
	@Override
	public void storeWithStoreException(Customer customer, Order order) throws StoreException {
		customerRepository.save(customer);
		orderRepository.save(order);
		throw new StoreException();
	}

	@Transactional(noRollbackFor = NoRollbackException.class, rollbackFor = StoreException.class)
	@Override
	public void storeWithNoRollbackException(Customer customer, Order order) throws NoRollbackException {
		customerRepository.save(customer);
		orderRepository.save(order);
		throw new NoRollbackException();
	}

	@Transactional()
	public void transfer() {
		CapitalAccount ca1 = capitalAccountRepository.findById(1l).get();
		CapitalAccount ca2 = capitalAccountRepository.findById(2l).get();
		RedPacketAccount rp1 = redPacketAccountRepository.findById(1l).get();
		RedPacketAccount rp2 = redPacketAccountRepository.findById(2l).get();
		BigDecimal capital = BigDecimal.TEN;
		BigDecimal red = BigDecimal.TEN;
		ca1.transferFrom(capital);
		ca2.transferTo(capital);
		capitalAccountRepository.save(ca1);
		capitalAccountRepository.save(ca2);
//		if (rp2.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
//			throw new RuntimeException("账号异常");
//		}
		rp2.transferFrom(red);
		rp1.transferTo(red);
		redPacketAccountRepository.save(rp1);
		redPacketAccountRepository.save(rp2);
		
	}
	
	@Transactional(rollbackFor = StoreException.class)
	public void transferWithStoreException() throws StoreException {
		CapitalAccount ca1 = capitalAccountRepository.findById(1l).get();
		CapitalAccount ca2 = capitalAccountRepository.findById(2l).get();
		RedPacketAccount rp1 = redPacketAccountRepository.findById(1l).get();
		RedPacketAccount rp2 = redPacketAccountRepository.findById(2l).get();
		
		BigDecimal capital = BigDecimal.TEN;
		BigDecimal red = BigDecimal.TEN;
		
		ca1.transferFrom(capital);
		ca2.transferTo(capital);
		capitalAccountRepository.save(ca1);
		capitalAccountRepository.save(ca2);
//		if (rp2.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
//			throw new RuntimeException("账号异常");
//		}
//		if (rp2.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
//			throw new IllegalArgumentException("账号异常");
//		}
		if (rp2.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new StoreException();
		}
		rp2.transferFrom(red);
		rp1.transferTo(red);
		redPacketAccountRepository.save(rp1);
		redPacketAccountRepository.save(rp2);
		
	}
	
	@Transactional(noRollbackFor = NoRollbackException.class, rollbackFor = StoreException.class)
	public void transferWithNoRollbackException() throws NoRollbackException {
		CapitalAccount ca1 = capitalAccountRepository.findById(1l).get();
		CapitalAccount ca2 = capitalAccountRepository.findById(2l).get();
		RedPacketAccount rp1 = redPacketAccountRepository.findById(1l).get();
		RedPacketAccount rp2 = redPacketAccountRepository.findById(2l).get();
		
		BigDecimal capital = BigDecimal.TEN;
		BigDecimal red = BigDecimal.TEN;
		
		ca1.transferFrom(capital);
		ca2.transferTo(capital);
		capitalAccountRepository.save(ca1);
		capitalAccountRepository.save(ca2);
		if (rp2.getBalanceAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new NoRollbackException();
		}
		rp2.transferFrom(red);
		rp1.transferTo(red);
		redPacketAccountRepository.save(rp1);
		redPacketAccountRepository.save(rp2);
		
	}
}
