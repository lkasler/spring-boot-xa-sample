package hu.bridgesoft.xa.sample.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bridgesoft.xa.sample.domain.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
