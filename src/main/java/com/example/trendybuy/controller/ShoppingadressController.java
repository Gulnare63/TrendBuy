package com.example.trendybuy.controller;

import com.example.trendybuy.dao.entity.ShoppingAddresEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class ShoppingadressController {

    @GetMapping("/user/{userId}")
    public List<ShoppingAddresEntity> getAddressesByUserId(@PathVariable Long userId) {
        // TODO: implement
        return null;
    }


    @GetMapping("/{id}")
    public ShoppingAddresEntity getAddressById(@PathVariable Long id) {
        // TODO: implement
        return null;
    }



    @PostMapping("/user/{userId}")
    public ShoppingAddresEntity createAddress(@PathVariable Long userId, @RequestBody ShoppingAddresEntity address) {
        // TODO: implement
        return null;
    }

    @PutMapping("/{id}")
    public ShoppingAddresEntity updateAddress(@PathVariable Long id, @RequestBody ShoppingAddresEntity address) {
        // TODO: implement
        return null;
    }


    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        // TODO: implement
    }


    @PostMapping("/{id}/set-default")
    public void setDefaultAddress(@PathVariable Long id) {
        // TODO: implement
    }


    @GetMapping("/user/{userId}/default")
    public ShoppingAddresEntity getDefaultAddressByUserId(@PathVariable Long userId) {
        // TODO: implement
        return null;
    }
//getAddressesByUserId
//
//getAddressById
//
//createAddress
//
//updateAddress
//
//deleteAddress
//
//setDefaultAddress
//
//getDefaultAddressByUserId
}
