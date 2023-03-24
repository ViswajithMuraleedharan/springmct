package com.example.orderApi.controller;

import com.example.orderApi.dao.orderRepository;
import com.example.orderApi.model.ordertable;
import org.hibernate.annotations.NotFound;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import java.util.List;

@RestController
public class orderController {
    @Autowired
    orderRepository repository;
    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestBody String orderRequest){
        JSONObject object=new JSONObject(orderRequest);
        ordertable orders=new ordertable();
        orders.setUserId(object.getInt("userId"));
        orders.setPaymentId(object.getInt("paymentId"));
        orders.setProductId(object.getInt("productId"));
        orders.setProductCount(object.getInt("productCount"));
        repository.save(orders);
        return new ResponseEntity<>("order created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<String> getOrder(@Nullable @RequestParam String orderId){
        JSONArray array=new JSONArray();
        List<ordertable> orderlist=repository.findAll();
        if(orderId==null){
            for(ordertable order:orderlist){
                JSONObject jsonObject=getResponse(order);
                array.put(jsonObject);
            }
            return new ResponseEntity<>(array.toString(),HttpStatus.OK);
        }
        else{
            for (ordertable order:orderlist){
                if(order.getOrderId()==Integer.valueOf(orderId)){
                    JSONObject jsonObject=getResponse(order);
                    array.put(jsonObject);
                }
                return new ResponseEntity<>(array.toString(),HttpStatus.OK);
            }
            return new ResponseEntity<>("orderId not found",HttpStatus.BAD_REQUEST);
        }
    }

    private JSONObject getResponse(ordertable order) {
        JSONObject object=new JSONObject();
        object.put("orderId",order.getOrderId());
        object.put("productId",order.getProductId());
        object.put("paymentId",order.getPaymentId());
        object.put("productCount",order.getProductCount());
        return object;
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderId,@RequestBody String orderRequest) {

            JSONObject object=new JSONObject(orderRequest);
            ordertable orders=new ordertable();
            orders.setUserId(object.getInt("userId"));
            orders.setPaymentId(object.getInt("paymentId"));
            orders.setProductId(object.getInt("productId"));
            orders.setProductCount(object.getInt("productCount"));
            orders.setOrderId(Integer.valueOf(orderId));
            repository.save(orders);
            return new ResponseEntity<>("Order updated succesffully",HttpStatus.OK);
    }
    @DeleteMapping("deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestParam String orderId){
        if(orderId!=null){
            repository.deleteById(Integer.valueOf(orderId));
            return new ResponseEntity<>("Order deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("orderId not found",HttpStatus.BAD_REQUEST);
    }
}

