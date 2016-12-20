package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.UserCustomer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class UserCustomerDao {
    public boolean add(UserCustomer userCustomer){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(userCustomer);
            session.getTransaction().commit();
            System.out.println("Add the userCustomer successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userCustomer");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(UserCustomer userCustomer){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(userCustomer);
            session.getTransaction().commit();
            System.out.println("Update the userCustomer successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add userCustomer");
            e.printStackTrace();
        }
        return false;
    }

    public UserCustomer selectById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from UserCustomer as userCustomer where userCustomer.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            List list=query.list();
            return (UserCustomer)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the userCustomer by id");
            e.printStackTrace();
        }
        return null;
    }

    public UserCustomer selectByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from UserCustomer as userCustomer where userCustomer.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            List list=query.list();
            return (UserCustomer)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the userCustomer by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
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
        }
        return false;
    }

    public boolean deleteByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
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
        }
        return false;
    }
}
