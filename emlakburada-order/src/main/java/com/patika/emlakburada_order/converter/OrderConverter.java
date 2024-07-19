package com.patika.emlakburada_order.converter;

import com.patika.emlakburada_order.client.user.response.UserResponse;
import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.entity.Order;

import java.time.LocalDate;

public class OrderConverter {
    public static Order toOrder(Long userId, PackageResponse packageResponse, OrderRequest request) {
        Order order=new Order();
        order.setUserId(userId);
        order.setPackageId(packageResponse.getId());
        order.setTotalAmount(packageResponse.getPrice());
        order.setOrderDate(LocalDate.now());
        order.setCardNumber(request.getCardNumber());
        order.setExpiration(request.getExpiration());
        order.setCvc(request.getCvc());
        return order;
    }

    public static OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse=new OrderResponse(order.getId(),
                order.getUserId(),order.getPackageId(),order.getTotalAmount(),
                order.getStatus(),order.getOrderDate(),order.getCardNumber(),
                order.getExpiration(),order.getCvc());

        return orderResponse;
    }
}
