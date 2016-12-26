package com.jimstar.easyshop.dao;

import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.util.LogUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Repository
public class ItemDao {
    public ItemDao() {
    }

    public Item add(Item item) {
        final Session session = getSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            LogUtil.log.debug("Add the item successfully");
            return item;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LogUtil.log.error("Failed to add " + item.toString(), e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public boolean update(Item item){
        final Session session = getSession();
        try {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            System.out.println("Update the item successfully");
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Fail to update item");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    /**
     * @param iid iid of item
     * @return list of item ordered by ver, latest at first
     */
    public List<Item> selectByIid(String iid){
        try (Session session = getSession()) {
            String hql = "from Item as item where item.iid=:iid order by item.ver desc";
            Query query = session.createQuery(hql);
            query.setParameter("iid", iid);
            return query.list();
        } catch (Exception e) {
            System.out.println("Fail to select the item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public Item selectLatestByIid(String iid) {
        try (Session session = getSession()) {
            String hql = "from Item as item where item.iid=:iid";
            Query query = session.createQuery(hql);
            query.setParameter("iid", iid);
            List list = query.list();
            Collections.sort(list);
            return (Item) list.get(list.size() - 1);
        } catch (Exception e) {
            System.out.println("Fail to select the last item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> selectAll(){
        try (Session session = getSession()) {
            String hql = "from Item as item order by item.iid";
            Query query = session.createQuery(hql);
            List<Item> result = query.list();
            for (int i = 1; i < result.size(); i++) {
                if (result.get(i - 1).getIid().equals(result.get(i).getIid())) {
                    if (result.get(i - 1).getVer() < result.get(i).getVer())
                        result.remove(i - 1);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Fail to select the last item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> selectRec(){
        try (Session session = getSession()) {
            String hql = "from Item as item order by item.createTime";
            Query query = session.createQuery(hql);
            List<Item> result = query.list();
            for (Item i:result) {
                for (Item j : result) {
                    if ((j.getIid().equals(i.getIid()))&&!(j.getUid().equals(j.getUid()))) {
                        if (j.getVer() < i.getVer()) result.remove(j);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Fail to select the last item by iid");
            e.printStackTrace();
        }
        return null;
    }

    public Item selectByUid(String uid){
        try (Session session = getSession()) {
            /*String hql = "from Item as item where item.uid=:uid";
            Query query = session.createQuery(hql);
            query.setParameter("uid", uid);
            List list = query.list();*/
            return session.get(Item.class, uid);
        } catch (Exception e) {
            System.out.println("Fail to select the item by uid");
            e.printStackTrace();
        }
        return null;
    }
    //模糊查询
    public List<Item> selectByMatchName(String name) {
        try (Session session = getSession()) {
            String hql = "from Item as item where item.name like :name order by item.iid";
            Query query=session.createQuery(hql);
            query.setParameter("name",'%'+name+'%');
            List<Item> result = query.list();
            for (int i = 1; i < result.size(); i++) {
                if (result.get(i - 1).getIid().equals(result.get(i).getIid())) {
                    if (result.get(i - 1).getVer() < result.get(i).getVer())
                        result.remove(i - 1);
                }
            }
            return result;
        }catch (Exception e){
            System.out.println("Fail to select the item by name");
            e.printStackTrace();
        }
        return null;
    }

    public List<Item> selectByMatchMerchant(UserMerchant userMerchant) {
        try (Session session = getSession()) {
            String hql = "from Item as item where item.userMerchant.name like :userMerchantName order by item.iid";
            Query query=session.createQuery(hql);
            query.setParameter("userMerchantName",'%'+userMerchant.getName()+'%');
            List<Item> result = query.list();
            for (int i = 1; i < result.size(); i++) {
                if (result.get(i - 1).getIid().equals(result.get(i).getIid())) {
                    if (result.get(i - 1).getVer() < result.get(i).getVer())
                        result.remove(i - 1);
                }
            }
            return result;
        }catch (Exception e){
            System.out.println("Fail to select the item by merchant");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteByIid(String iid){
        final Session session = getSession();
        try{
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
        } finally {
            session.close();
        }
        return false;
    }

    public boolean deleteByUid(String uid){
        final Session session = getSession();
        try{
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
        } finally {
            session.close();
        }
        return false;
    }


}
