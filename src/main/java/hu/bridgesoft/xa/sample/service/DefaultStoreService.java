package hu.bridgesoft.xa.sample.service;

import hu.bridgesoft.xa.sample.JmsConfig;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class DefaultStoreService implements StoreService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    @Autowired
    RedPacketAccountRepository redPacketAccountRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

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
    @Override
    public void transfer() {
        log.debug("Succes Start transfer, find id of capital and redPacket account");
        baseTransfer();
        log.debug("Transfer finished with success");
    }

    private void baseTransfer() {
        CapitalAccount ca1 = capitalAccountRepository.findById(1l).get();
        CapitalAccount ca2 = capitalAccountRepository.findById(2l).get();
        RedPacketAccount rp1 = redPacketAccountRepository.findById(1l).get();
        RedPacketAccount rp2 = redPacketAccountRepository.findById(2l).get();
        log.debug("CapitalAccount ca1: {}, ca2: {}, RedPacketAccount rp1: {}, rp2: {}", ca1, ca2, rp1, rp2);
        BigDecimal capital = BigDecimal.TEN;
        BigDecimal red = BigDecimal.TEN;
        ca1.transferFrom(capital);
        ca2.transferTo(capital);
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_1, "Transferred from ca1 to ca2 ");
        capitalAccountRepository.save(ca1);
        capitalAccountRepository.save(ca2);
        rp2.transferFrom(red);
        rp1.transferTo(red);
        jmsTemplate.convertAndSend(JmsConfig.QUEUE_1, "Transferred from rp2 to rp1 ");
        redPacketAccountRepository.save(rp1);
        redPacketAccountRepository.save(rp2);
    }

    @Transactional
    @Override
    public void transferWithRuntimeException() {
        log.info("Failure test, Throwing runtime exception ");
        baseTransfer();
        throw new RuntimeException("All transactions should be rolled back");
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
