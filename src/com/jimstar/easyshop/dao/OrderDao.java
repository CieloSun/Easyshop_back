package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class OrderDao {
    public boolean add(Order order){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
            System.out.println("Add the order successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add order");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Order order){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
            System.out.println("Update the order successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add order");
            e.printStackTrace();
        }
        return false;
    }

    public Order selectById(String id){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Order as order where order.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            List list=query.list();
            return (Order)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the order by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from Order as order where order.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the order by id");
            e.printStackTrace();
        }
        return false;
    }
}
