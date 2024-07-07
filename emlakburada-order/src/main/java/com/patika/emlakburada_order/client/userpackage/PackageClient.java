package com.patika.emlakburada_order.client.userpackage;

import com.patika.emlakburada_order.client.userpackage.response.PackageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "emlakburada-package",url = "localhost:8083/emlakburada/api/v1/packages")
public interface PackageClient {

    @GetMapping("/{id}")
    ResponseEntity<PackageResponse> findById(@PathVariable Long id);
}
