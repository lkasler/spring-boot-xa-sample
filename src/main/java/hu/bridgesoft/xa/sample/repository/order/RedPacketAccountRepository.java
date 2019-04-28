package hu.bridgesoft.xa.sample.repository.order;


import org.springframework.data.repository.CrudRepository;

import hu.bridgesoft.xa.sample.domain.order.RedPacketAccount;

public interface RedPacketAccountRepository extends CrudRepository<RedPacketAccount, Long> {


}
