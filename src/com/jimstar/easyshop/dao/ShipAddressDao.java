package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.ShipAddress;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class ShipAddressDao {
    public ShipAddressDao() {
    }

    public ShipAddress add(String name,String address, String phone){
        ShipAddress shipAddress=new ShipAddress();
        shipAddress.setName(name);
        shipAddress.setAddress(address);
        shipAddress.setPhone(phone);
        return shipAddress;
    }
}
