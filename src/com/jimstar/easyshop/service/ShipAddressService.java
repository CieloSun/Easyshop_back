package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ShipAddressDao;
import com.jimstar.easyshop.entity.ShipAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipAddressService {
    private final ShipAddressDao shipAddressDao;

    @Autowired
    public ShipAddressService(ShipAddressDao shipAddressDao) {
        this.shipAddressDao = shipAddressDao;
    }
    public ShipAddress getShipAdress(String name,String address,String phone){
        return shipAddressDao.add(name,address,phone);
    }
}
