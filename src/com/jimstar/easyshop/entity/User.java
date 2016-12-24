package com.jimstar.easyshop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("user")
public class User implements Serializable {
    private Integer id;
    private String name;
    private Timestamp regTime;
    private String pwdDigest;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false , unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    @Column(nullable = false)
    public String getPwdDigest() {
        return pwdDigest;
    }

    public void setPwdDigest(String pwdDigest) {
        this.pwdDigest = pwdDigest;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regTime=" + regTime +
                ", pwdDigest='" + pwdDigest + '\'' +
                '}';
    }
}
