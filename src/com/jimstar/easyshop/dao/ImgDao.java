package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.Main;
import com.jimstar.easyshop.entity.Img;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by 63289 on 2016/12/20.
 */
@Repository
public class ImgDao {

    public boolean add(Img img){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.save(img);
            session.getTransaction().commit();
            System.out.println("Add the img successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add img");
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Img img){
        Session session=null;
        try{
            session= Main.getSession();
            session.beginTransaction();
            session.update(img);
            session.getTransaction().commit();
            System.out.println("Update the img successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to add img");
            e.printStackTrace();
        }
        return false;
    }

    public Img selectById(String id){
        Session session=null;
        try{
            session=Main.getSession();
            String hql="from Img as img where img.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            List list=query.list();
            return (Img)list.get(0);
        }catch (Exception e){
            System.out.println("Fail to select the image by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        Session session=null;
        try{
            session=Main.getSession();
            session.beginTransaction();
            String hql="delete from Img as img where img.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",id);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Fail to delete the image by id");
            e.printStackTrace();
        }
        return false;
    }
}
