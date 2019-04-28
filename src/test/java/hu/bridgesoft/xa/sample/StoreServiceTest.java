package hu.bridgesoft.xa.sample;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.domain.order.Order;
import hu.bridgesoft.xa.sample.repository.customer.CustomerRepository;
import hu.bridgesoft.xa.sample.repository.order.OrderRepository;
import hu.bridgesoft.xa.sample.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainConfig.class)
@Transactional
@Commit
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

		assertNotNull(c.getId());
		assertNotNull(o.getId());

		assertTrue(1 <= customerRepository.findAll().size());
		assertTrue(1 <= orderRepository.findAll().size());
	}


}
