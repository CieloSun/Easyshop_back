package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class OrderItemDao {
    public OrderItemDao() {
    }

    public boolean add(OrderItem orderItem){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(orderItem);
            session.getTransaction().commit();
            System.out.println("Add the orderItem successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add orderItem");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean update(OrderItem orderItem){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(orderItem);
            session.getTransaction().commit();
            System.out.println("Update the orderItem successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add orderItem");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public OrderItem selectById(String id){
        try (Session session = getSession()) {
            String hql = "from OrderItem as orderItem where orderItem.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List list = query.list();
            return (OrderItem) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the orderItem by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        final Session session = getSession();
        try{
            session.beginTransaction();
            String hql="delete from OrderItem as orderItem where orderItem.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the orderItem by id");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
