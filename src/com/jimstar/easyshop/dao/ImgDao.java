package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.Img;
import com.jimstar.easyshop.util.LogUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class ImgDao {

    public ImgDao() {
    }

    /**
     * @param img Image Object
     * @return String id if success, null for failed
     */
    public String add(Img img) {
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.save(img);
            session.getTransaction().commit();
            LogUtil.log.debug("Add the img successfully");
            return img.getId();
        }catch (Exception e){
            session.getTransaction().rollback();
            LogUtil.log.error("Failed to add " + img.toString(), e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public boolean update(Img img){
        final Session session = getSession();
        try{
            session.beginTransaction();
            session.update(img);
            session.getTransaction().commit();
            LogUtil.log.debug("Update the img successfully");
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            LogUtil.log.error("Failed to update " + img.toString(), e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public Img selectById(String id){
        try (Session session = getSession()) {
            String hql = "from Img as img where img.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List list = query.list();
            return (Img) list.get(0);
        } catch (Exception e) {
            System.out.println("Fail to select the image by id");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(String id){
        final Session session = getSession();
        try{
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
        } finally {
            session.close();
        }
        return false;
    }
}
