package com.patika.emlakburada_advert.service.impl;

import com.patika.emlakburada_advert.client.user.UserService;
import com.patika.emlakburada_advert.client.user.response.UserResponse;
import com.patika.emlakburada_advert.converter.AdvertConverter;
import com.patika.emlakburada_advert.dto.request.AdvertRequest;
import com.patika.emlakburada_advert.dto.request.AdvertStatusRequest;
import com.patika.emlakburada_advert.dto.response.AdvertResponse;
import com.patika.emlakburada_advert.entity.Advert;
import com.patika.emlakburada_advert.entity.enums.AdvertStatus;
import com.patika.emlakburada_advert.exception.EmlakBuradaException;
import com.patika.emlakburada_advert.queue.RabbitMqService;
import com.patika.emlakburada_advert.repository.AdvertRepository;
import com.patika.emlakburada_advert.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {

    private final AdvertRepository advertRepository;
    private final UserService userService;
    private final RabbitMqService rabbitMqService;

    @Override
    public ResponseEntity<AdvertResponse> save(AdvertRequest request) {
        //TODO =>burada userClient ile userresponse bulunacak bir userREsponse içindeki id toAdvert() içinde gönderilecek.
        //Yani aslında user bu save işlemini yapabilmesi için paket satın alması lazım bunu aklından çıkarma.

        UserResponse userResponse =userService.getUserById(request.getUserId());

        if(userResponse.getListingRights()<=0){
            throw new EmlakBuradaException("User doesn't have enough listing rights", HttpStatus.BAD_REQUEST);
        }

        if(LocalDate.now().isAfter(userResponse.getEndDateOfPackage())){
            throw new EmlakBuradaException("The package has expired.User must purchase new package.",HttpStatus.BAD_REQUEST);
        }

        Advert advert= AdvertConverter.toAdvert(request);
        advert=advertRepository.save(advert);

        //Listing rights azaltılması ve userSErvice üzerinde güncellenmesi
        userService.updateListingRights(request.getUserId(),userResponse.getListingRights()-1);

        AdvertResponse advertResponse=AdvertConverter.toAdvertResponse(advert);

        return new ResponseEntity<>(advertResponse,HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<AdvertResponse>> findAll(){
        List<AdvertResponse> advertResponses=new ArrayList<>();


        for(Advert advert:advertRepository.findAll()){
            AdvertResponse advertResponse=AdvertConverter.toAdvertResponse(advert);
            advertResponses.add(advertResponse);
        }

       advertResponses.sort(Comparator.comparing(advertResponse -> {
           UserResponse userResponse=userService.getUserById(advertResponse.getUserId());
           return userResponse.getIsPrioritized()?0:1;
       }));

        return new ResponseEntity<>(advertResponses,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdvertResponse> findById(Long id){
        Optional<Advert> advertOptional=advertRepository.findById(id);

        if(advertOptional.isEmpty()){
            throw new EmlakBuradaException("Advert with given id cannot be found",HttpStatus.NOT_FOUND);
        }

        AdvertResponse advertResponse=AdvertConverter.toAdvertResponse(advertOptional.get());

        return new ResponseEntity<>(advertResponse,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<AdvertResponse>> findByUserId(Long userId){
        UserResponse userResponse=userService.getUserById(userId);
        List<Optional<Advert>> advertsOptional = advertRepository.findByUserId(userResponse.getId());

        if (advertsOptional.isEmpty()) {
            throw new EmlakBuradaException("Adverts with given user id cannot be found", HttpStatus.NOT_FOUND);
        }

        List<AdvertResponse> advertResponses = new ArrayList<>();

        for (Optional<Advert> advertOpt : advertsOptional) {
            advertOpt.ifPresent(advert -> {
                AdvertResponse advertResponse = AdvertConverter.toAdvertResponse(advert);
                advertResponses.add(advertResponse);
            });
        }

        return new ResponseEntity<>(advertResponses, HttpStatus.OK);


    }


    @Override
    public ResponseEntity<AdvertResponse> delete(Long id){
        Optional<Advert> advertOptional=advertRepository.findById(id);

        if(advertOptional.isEmpty()){
            throw new EmlakBuradaException("Advert with given id cannot be found",HttpStatus.NOT_FOUND);
        }

        advertRepository.delete(advertOptional.get());
        Advert advert=advertOptional.get();
        AdvertResponse advertResponse=AdvertConverter.toAdvertResponse(advert);

        return new ResponseEntity<>(advertResponse,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Advert> updateStatusActiveById(Long id){
        Optional<Advert> optionalAdvert=advertRepository.findById(id);

        if(optionalAdvert.isEmpty()){
            throw new EmlakBuradaException("Advert with given id cannot be found",HttpStatus.NOT_FOUND);
        }
        AdvertStatusRequest request=new AdvertStatusRequest();
        request.setId(optionalAdvert.get().getId());
        request.setAdvertStatus(AdvertStatus.ACTIVE);

        rabbitMqService.sendNotification(request);//to change the advert status asynchronous comm. with rabbitmq

        optionalAdvert.get().setAdvertStatus(request.getAdvertStatus());

        return new ResponseEntity<>(optionalAdvert.get(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Advert> updateStatusPassiveById(Long id){
        Optional<Advert> optionalAdvert=advertRepository.findById(id);

        if(optionalAdvert.isEmpty()){
            throw new EmlakBuradaException("Advert with given id cannot be found",HttpStatus.NOT_FOUND);
        }
        AdvertStatusRequest request=new AdvertStatusRequest();
        request.setId(optionalAdvert.get().getId());
        request.setAdvertStatus(AdvertStatus.PASSIVE);

        rabbitMqService.sendNotification(request);//to change the advert status asynchronous comm. with rabbitmq

        optionalAdvert.get().setAdvertStatus(request.getAdvertStatus());

        return new ResponseEntity<>(optionalAdvert.get(),HttpStatus.OK);
    }
}
