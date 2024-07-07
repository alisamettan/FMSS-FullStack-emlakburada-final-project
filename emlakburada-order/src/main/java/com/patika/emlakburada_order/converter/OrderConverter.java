package com.patika.emlakburada_order.converter;

import com.patika.emlakburada_order.client.user.response.UserResponse;
import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.entity.Order;

import java.time.LocalDate;

public class OrderConverter {
    public static Order toOrder(UserResponse userResponse, PackageResponse packageResponse) {
        Order order=new Order();
        order.setUserId(userResponse.getId());
        order.setPackageId(packageResponse.getId());
        order.setTotalAmount(packageResponse.getPrice());
        order.setOrderDate(LocalDate.now());
        return order;
    }

    public static OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse=new OrderResponse(order.getId(),
                order.getUserId(),order.getPackageId(),order.getTotalAmount(),order.getStatus(),order.getOrderDate());

        return orderResponse;
    }
}
