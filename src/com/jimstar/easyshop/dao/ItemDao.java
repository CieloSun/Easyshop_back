package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.Item;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class ItemDao {
    public boolean add(Item item){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            System.out.println("Add the item successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add item");
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> selectByIid(Integer iid){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Item as item where item.iid=:iid";
            Query query=session.createQuery(hql);
            query.setParameter("iid",iid);
            List list=query.list();
            return list;
        }catch (Exception e){
            System.out.println("Fail to select the item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public Item selectLastByIid(Integer iid){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Item as item where item.iid=:iid";
            Query query=session.createQuery(hql);
            query.setParameter("iid",iid);
            List list=query.list();
            Collections.sort(list);
            return (Item)list.get(list.size()-1);
        }catch (Exception e){
            System.out.println("Fail to select the last item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public Item selectByUid(Integer uid){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Item as item where item.uid=:uid";
            Query query=session.createQuery(hql);
            query.setParameter("uid",uid);
            List list=query.list();
            return (Item)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the item by uid");
            e.printStackTrace();
        }
        return null;
    }
    //模糊查询
    public List<Item> selectByName(String name){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Item as item where item.name like :name";
            Query query=session.createQuery(hql);
            query.setParameter("name",'%'+name+'%');
            List list=query.list();
            return list;
        }catch (Exception e){
            System.out.println("Fail to select the item by name");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteByIid(Integer iid){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from Item as item where item.iid=:iid";
            Query query=session.createQuery(hql);
            query.setParameter("iid",iid);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the item by iid");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByUid(Integer uid){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from Item as item where item.uid=:uid";
            Query query=session.createQuery(hql);
            query.setParameter("uid",uid);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the item by uid");
            e.printStackTrace();
        }
        return false;
    }
}
