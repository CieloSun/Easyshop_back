package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class UserDao {
    public boolean add(User user){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Add the user successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add user");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(User user){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            System.out.println("Update the user successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add user");
            e.printStackTrace();
        }
        return false;
    }

    public User selectById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from User as user where user.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            List list=query.list();
            return (User)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the user by id");
            e.printStackTrace();
        }
        return null;
    }

    public User selectByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from User as user where user.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            List list=query.list();
            return (User)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the user by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(Integer id){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from User as user where user.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the user by id");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from User as user where user.name=:name";
            Query query=session.createQuery(hql);
            query.setParameter("name",name);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the user by name");
            e.printStackTrace();
        }
        return false;
    }
}
