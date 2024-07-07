package com.patika.emlakburada_order.service.impl;

import com.patika.emlakburada_order.client.user.UserService;
import com.patika.emlakburada_order.client.user.response.UserResponse;
import com.patika.emlakburada_order.client.userpackage.PackageService;
import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import com.patika.emlakburada_order.client.userpackage.response.enums.PackageType;
import com.patika.emlakburada_order.converter.OrderConverter;
import com.patika.emlakburada_order.dto.request.OrderRequest;
import com.patika.emlakburada_order.dto.response.OrderResponse;
import com.patika.emlakburada_order.entity.Order;
import com.patika.emlakburada_order.exception.EmlakBuradaException;
import com.patika.emlakburada_order.repository.OrderRepository;
import com.patika.emlakburada_order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final PackageService packageService;

    @Override
    public ResponseEntity<OrderResponse> save(OrderRequest request) {
        UserResponse userResponse=userService.getUserById(request.getUserId());
        PackageResponse packageResponse=packageService.getPackageById(request.getPackageId());

        Order order= OrderConverter.toOrder(userResponse,packageResponse);



        boolean paymentSuccess=processPayment(order.getTotalAmount());
        if(paymentSuccess){
            order.setStatus("SUCCESS");

            updateUserPackage(userResponse,packageResponse);

            //TODO rabbit ile başka servise mesaj gönderilecek ve orada purschase database e kaydedilecek
        }else {
            order.setStatus("FAILED");
        }


        order=orderRepository.save(order);

        OrderResponse orderResponse=OrderConverter.toOrderResponse(order);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }


    //Users can see their orders and Packages.
    @Override
    public ResponseEntity<List<OrderResponse>> findAllByUserId(Long userId){
        UserResponse userResponse=userService.getUserById(userId);
        List<Order> orders = orderRepository.findAllByUserId(userResponse.getId());

        if(orders.isEmpty()){
            throw new EmlakBuradaException("Orders with given user id cannot be found.",HttpStatus.NOT_FOUND);
        }

        List<OrderResponse> orderResponses =orders.stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }



    private boolean processPayment(Double totalAmount) {
        //Here,payment might be processing synchronous.
        //For example,we can send a HTTP request to a payment service
        //Assuming that it is successfull everytime.
        return true;
    }

    private void updateUserPackage(UserResponse userResponse, PackageResponse packageResponse) {
        // Update listing rights
        if (packageResponse.getPackageType() == PackageType.ECO) {
            userResponse.setListingRights(userResponse.getListingRights() + 10);
        } else if (packageResponse.getPackageType() == PackageType.ULTIMATE) {
            userResponse.setListingRights(userResponse.getListingRights() + 20);
        } else if (packageResponse.getPackageType() == PackageType.SUPER_ULTIMATE) {
            userResponse.setListingRights(userResponse.getListingRights() + 20);
            userResponse.setIsPrioritized(true);
            // Handle prioritization or other specific actions for SUPER_ULTIMATE package
        }

        // Calculate package end date (30 days from now)
        LocalDate endDate = LocalDate.now().plusDays(30);
        userResponse.setEndDateOfPackage(endDate);

        // Update user using UserService
        userService.updateUser(userResponse);
    }
}
