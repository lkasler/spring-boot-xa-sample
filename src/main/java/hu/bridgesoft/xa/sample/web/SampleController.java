package hu.bridgesoft.xa.sample.web;

import hu.bridgesoft.xa.sample.domain.customer.Customer;
import hu.bridgesoft.xa.sample.domain.order.Order;
import hu.bridgesoft.xa.sample.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SampleController {

    @Autowired
    StoreService storeService;

    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }


    @GetMapping("/store")
    public Object store() {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Customer c = new Customer();
            c.setName("test");
            c.setAge(30);
            Order o = new Order();
            o.setCode(1);
            o.setQuantity(7);
            storeService.store(c, o);

            Assert.notNull(c.getId());
            Assert.notNull(o.getId());
            result.put("status", "0");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "1");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/save")
    public Object save() {
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            storeService.transfer();
//    		storeService.transferWithStoreException();
            result.put("status", "0");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "1");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/saveRuntimeException")
    public Object saveRuntimeException() {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            storeService.transferWithRuntimeException();
            result.put("status", "0");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "1");
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
