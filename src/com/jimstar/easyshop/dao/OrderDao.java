package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.Order;
import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class OrderDao {
    public OrderDao() {
    }

    public Order add(Order order){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
            System.out.println("Add the order successfully");
            return order;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add order");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
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
            return session.get(Order.class, id);
        } catch (Exception e) {
            System.out.println("Fail to select the order by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        try (Session session = getSession()) {
            Order order = session.get(Order.class, id);
            return delete(order);
        } catch (Exception e) {
            System.out.println("Fail to delete order " + id);
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Order order) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            System.out.println("Fail to delete order " + order.getId());
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> selectByCustomer(UserCustomer userCustomer) {
        try (Session session = getSession()) {
            String hql = "from Order as order where order.customer=:userCustomer";
            Query query = session.createQuery(hql);
            query.setParameter("userCustomer", userCustomer);
            return query.list();
        } catch (Exception e) {
            System.out.println("Fail to select the order by customer");
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> selectByMerchant(UserMerchant userMerchant) {
        try (Session session = getSession()) {
            String hql = "from Order as order where order.merchant=:userMerchant";
            Query query = session.createQuery(hql);
            query.setParameter("userMerchant", userMerchant);
            return query.list();
        } catch (Exception e) {
            System.out.println("Fail to select the order by merchant");
            e.printStackTrace();
        }
        return null;
    }
}
