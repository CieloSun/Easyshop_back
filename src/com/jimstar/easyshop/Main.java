
package com.jimstar.easyshop;

import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.util.UUIDGenerator;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.sql.Timestamp;
import java.util.Scanner;

public class Main {
    private static final SessionFactory ourSessionFactory;
    private static Scanner scanner = new Scanner(System.in);

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        InitUtil();
        TestMain();
        return;
    }

    public static void InitUtil() {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }

    public static void TestMain() {
        final Session session = getSession();
        try {

            Transaction trans = session.beginTransaction();
            UserMerchant merchant = createMerchant();
            UserCustomer customer = createCustomer();
            Item item = createItem(merchant);
            session.saveOrUpdate(merchant);
            session.saveOrUpdate(customer);
            session.saveOrUpdate(item);
            trans.commit();


        } finally {
            session.close();
        }
    }

    public static UserMerchant createMerchant() {
        System.out.println("UserMerchant:");
        UserMerchant mUser = new UserMerchant();
        System.out.print("Name:");
        mUser.setName(scanner.next());
        System.out.print("PwdDigest:");
        mUser.setPwdDigest(scanner.next());
        mUser.setRegTime(new Timestamp(System.currentTimeMillis()));
        System.out.print("ShopName:");
        mUser.setShopName(scanner.next());
        System.out.print("ShopDesc:");
        mUser.setShopDesc(scanner.next());
        return mUser;
    }

    public static UserCustomer createCustomer() {
        System.out.println("UserCustomer:");
        UserCustomer cUser = new UserCustomer();
        System.out.print("Name:");
        cUser.setName(scanner.next());
        System.out.print("PwdDigest:");
        cUser.setPwdDigest(scanner.next());
        cUser.setRegTime(new Timestamp(System.currentTimeMillis()));
        return cUser;
    }

    public static Item createItem(UserMerchant merchant) {
        System.out.println("Item:");
        Item item = new Item();
        System.out.print("Name:");
        item.setName(scanner.next());
        System.out.print("Price:");
        item.setPrice(scanner.nextFloat());
        System.out.print("Count:");
        item.setCount(scanner.nextInt());
        System.out.print("Desc:");
        item.setDescription(scanner.next());
        item.setCreateTime(new Timestamp(System.currentTimeMillis()));
        item.setUserMerchant(merchant);
        item.setIid(UUIDGenerator.genShort());
        return item;
    }
}