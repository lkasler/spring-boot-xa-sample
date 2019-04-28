package hu.bridgesoft.xa.sample.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.bridgesoft.xa.sample.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
