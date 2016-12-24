
package com.jimstar.easyshop;

import com.jimstar.easyshop.entity.Item;
import com.jimstar.easyshop.entity.UserCustomer;
import com.jimstar.easyshop.entity.UserMerchant;
import com.jimstar.easyshop.service.ItemService;
import com.jimstar.easyshop.service.UserCustomerService;
import com.jimstar.easyshop.service.UserMerchantService;
import com.jimstar.easyshop.util.UUIDGenerator;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.metamodel.EntityType;
import java.sql.Timestamp;
import java.util.Scanner;

import static com.jimstar.easyshop.util.HibernateUtil.getSession;

@Component
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    @Autowired
    ItemService itemService;
    @Autowired
    private UserCustomerService userCustomerService;
    @Autowired
    private UserMerchantService userMerchantService;

    public static void main(final String[] args) throws Exception {
        Main obj = new Main();
        InitUtil();
        obj.TestMain();
    }

    public static void InitUtil() {
        try (Session session = getSession()) {
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
            System.out.println("Finished!");
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

    public void TestMain() {
        //UserMerchant merchant = createMerchant();
        //UserCustomer customer = createCustomer();
        //Item item = createItem(merchant);

        userCustomerService.addUserCustomerByNameAndPwd("c1", "cp1");
        userMerchantService.addUserMerchantByInfo("m1", "mp1", "shop1", "shopDesc1");
        itemService.createItemByInf("item1", 100.53f, 100, userMerchantService.getUserMerchantByName("m1"), "itemDesc1", null);
    }
}