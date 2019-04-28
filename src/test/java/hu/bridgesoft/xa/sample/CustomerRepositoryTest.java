package hu.bridgesoft.xa.sample;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.repository.customer.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainConfig.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void testCustomerConfig() {

	}

	@Test
	public void save() {
		Customer c = new Customer();
		c.setName("test-name");
		c.setAge(30);
		Customer cust = customerRepository.save(c);
		Assert.assertNotNull(cust.getId());
	}

}
