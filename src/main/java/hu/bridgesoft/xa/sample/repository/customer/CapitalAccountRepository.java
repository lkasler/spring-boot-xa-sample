package hu.bridgesoft.xa.sample.repository.customer;


import org.springframework.data.repository.CrudRepository;

import hu.bridgesoft.xa.sample.domain.customer.CapitalAccount;

public interface CapitalAccountRepository extends CrudRepository<CapitalAccount, Long> {


}
