package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.UserCustomer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class UserCustomerDao {
    public UserCustomerDao() {
    }

    public boolean add(UserCustomer userCustomer){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(userCustomer);
            session.getTransaction().commit();
            System.out.println("Add the userCustomer successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userCustomer");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean update(UserCustomer userCustomer){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(userCustomer);
            session.getTransaction().commit();
            System.out.println("Update the userCustomer successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userCustomer");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public UserCustomer selectById(Integer id){
        try (Session session = getSession()) {
            String hql = "from UserCustomer as userCustomer where userCustomer.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List list = query.list();
            return (UserCustomer) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the userCustomer by id");
            e.printStackTrace();
        }
        return null;
    }

    public UserCustomer selectByName(String name){
        try (Session session = getSession()) {
            String hql = "from UserCustomer as userCustomer where userCustomer.name=:name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            List list = query.list();
            return (UserCustomer) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the userCustomer by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(Integer id){
        final Session session = getSession();
        try{
            session.beginTransaction();
            String hql="delete from UserCustomer as userCustomer where userCustomer.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the userCustomer by id");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean deleteByName(String name){
        final Session session = getSession();
        try{
            session.beginTransaction();
            String hql="delete from UserCustomer as userCustomer where userCustomer.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the userCustomer by name");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
