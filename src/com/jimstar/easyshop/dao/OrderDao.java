package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class OrderDao {
    public boolean add(Order order){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
            System.out.println("Add the order successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add order");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean update(Order order){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
            System.out.println("Update the order successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add order");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public Order selectById(String id){
        try (Session session = getSession()) {
            String hql = "from Order as order where order.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List list = query.list();
            return (Order) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the order by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        final Session session = getSession();
        try{
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
        } finally {
            session.close();
        }
        return false;
    }
}
