package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jim on 2016/12/19.
 */
@Entity
@Table(name = "Item")
public class Item {
    /*
        uid 交易快照标识
        iid 同一商品不变化
        ver 修改商品会改变
    */
    private String uid;
    private Integer iid;
    private Integer ver;
    private String name;
    private Float price;
    private Integer cnt;
    private UserMerchant userMerchant;
    private Timestamp createTime;
    private String description;
    private List<Img> imgs;

    @Id
    @GenericGenerator(name = "UUIDgen" , strategy = "uuid")
    @GeneratedValue(generator = "UUIDgen")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

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

    @Column(columnDefinition = "decimal(5,2)")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
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
    @JoinTable(name = "Item_Img")
    public List<Img> getImgs() {
        return imgs;
    }

    public void setImgs(List<Img> imgs) {
        this.imgs = imgs;
    }

    public Item() {
    }

}
