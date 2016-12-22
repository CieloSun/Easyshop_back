package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Item implements Serializable, Comparable<Item> {
    /*
        uid 交易快照标识
        iid 同一商品不变化
        ver 修改商品会改变
    */
    private String uid;//修改商品重新生成
    private String iid;//同一商品保持不变
    private Integer ver;//修改时自增
    private String name;
    private Float price;
    private Integer count;
    private UserMerchant userMerchant;
    private Timestamp createTime;
    private String description;
    private List<Img> imgs;

    public Item() {
    }

    @Id
    @GenericGenerator(name = "UUIDgen" , strategy = "uuid")
    @GeneratedValue(generator = "UUIDgen")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    @Column(nullable = false)
    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "count_")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @ManyToOne
    @PrimaryKeyJoinColumn
    public UserMerchant getUserMerchant() {
        return userMerchant;
    }

    public void setUserMerchant(UserMerchant userMerchant) {
        this.userMerchant = userMerchant;
    }

    @Column(nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany
    public List<Img> getImgs() {
        return imgs;
    }

    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }

    /**
     * Compare Ver if Iid is same.
     * Otherwise, compare Name
     */
    @Override
    public int compareTo(Item o) {
        if (getIid().equals(o.getIid())) {
            return getVer() - o.getVer();
        } else {
            return getName().compareTo(o.getName());
        }
    }

}
