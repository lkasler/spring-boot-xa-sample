package hu.bridgesoft.xa.sample;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.domain.order.Order;
import hu.bridgesoft.xa.sample.repository.customer.CustomerRepository;
import hu.bridgesoft.xa.sample.repository.order.OrderRepository;
import hu.bridgesoft.xa.sample.service.StoreService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainConfig.class)
@TransactionConfiguration(transactionManager = "transactionManager")
public class StoreServiceTest {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private StoreService storeService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	@Transactional
	public void testStore() throws Exception {
		Customer c = new Customer();
		c.setName("test");
		c.setAge(30);

		Order o = new Order();
		o.setCode(1);
		o.setQuantity(7);

		storeService.store(c, o);

		Assert.assertNotNull(c.getId());
		Assert.assertNotNull(o.getId());

		Assert.assertTrue(1 <= customerRepository.findAll().size());
		Assert.assertTrue(1 <= orderRepository.findAll().size());
	}


}