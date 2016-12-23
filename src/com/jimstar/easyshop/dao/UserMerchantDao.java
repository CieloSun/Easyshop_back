package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.UserMerchant;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class UserMerchantDao {
    public UserMerchantDao() {
    }

    public boolean add(UserMerchant userMerchant){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(userMerchant);
            session.getTransaction().commit();
            System.out.println("Add the userMerchant successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userMerchant");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean update(UserMerchant userMerchant){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(userMerchant);
            session.getTransaction().commit();
            System.out.println("Update the userMerchant successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userMerchant");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public UserMerchant selectById(Integer id){
        try (Session session = getSession()) {
            String hql = "from UserMerchant as userMerchant where userMerchant.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List list = query.list();
            return (UserMerchant) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the userMerchant by id");
            e.printStackTrace();
        }
        return null;
    }

    public UserMerchant selectByName(String name){
        try (Session session = getSession()) {
            String hql = "from UserMerchant as userMerchant where userMerchant.name=:name";
            Query query = session.createQuery(hql);
            query.setParameter("name", name);
            List list = query.list();
            return (UserMerchant) list.get(0);

        } catch (HibernateException e) {
            System.out.println("Fail to select the userMerchant by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(Integer id){
        final Session session = getSession();
        try{
            session.beginTransaction();
            String hql="delete from UserMerchant as userMerchant where userMerchant.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the userMerchant by id");
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
            String hql="delete from UserMerchant as userMerchant where userMerchant.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the userMerchant by name");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
