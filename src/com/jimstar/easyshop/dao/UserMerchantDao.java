package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.UserMerchant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class UserMerchantDao {
    public boolean add(UserMerchant userMerchant){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(userMerchant);
            session.getTransaction().commit();
            System.out.println("Add the userMerchant successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userMerchant");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(UserMerchant userMerchant){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(userMerchant);
            session.getTransaction().commit();
            System.out.println("Update the userMerchant successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userMerchant");
            e.printStackTrace();
        }
        return false;
    }

    public UserMerchant selectById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from UserMerchant as userMerchant where userMerchant.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            List list=query.list();
            return (UserMerchant)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the userMerchant by id");
            e.printStackTrace();
        }
        return null;
    }

    public UserMerchant selectByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from UserMerchant as userMerchant where userMerchant.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            List list=query.list();
            return (UserMerchant)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the userMerchant by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
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
        }
        return false;
    }

    public boolean deleteByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
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
        }
        return false;
    }
}
