package hu.bridgesoft.xa.sample;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.repository.customer.CustomerRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainConfig.class)
@Transactional
@Ignore
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
		assertNotNull(cust.getId());
	}

}
