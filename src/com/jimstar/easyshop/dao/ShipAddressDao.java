package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.ShipAddress;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class ShipAddressDao {
    public boolean add(ShipAddress shipAddress){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(shipAddress);
            session.getTransaction().commit();
            System.out.println("Add the shipAddress successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add shipAddress");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ShipAddress shipAddress){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(shipAddress);
            session.getTransaction().commit();
            System.out.println("Update the shipAddress successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add shipAddress");
            e.printStackTrace();
        }
        return false;
    }
}
