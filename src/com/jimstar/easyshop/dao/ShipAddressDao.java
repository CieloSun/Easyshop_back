package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.ShipAddress;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class ShipAddressDao {
    public boolean add(ShipAddress shipAddress){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(shipAddress);
            session.getTransaction().commit();
            System.out.println("Add the shipAddress successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add shipAddress");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean update(ShipAddress shipAddress){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(shipAddress);
            session.getTransaction().commit();
            System.out.println("Update the shipAddress successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add shipAddress");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
