package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by jim on 2016/12/19.
 */
@Entity
@Table(name = "Img")
public class Img {
    private String id;
    private Blob value;

    @Id
    @GenericGenerator(name = "UUIDgen" , strategy = "uuid")
    @GeneratedValue(generator = "UUIDgen")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    public Blob getValue() {
        return value;
    }

    public void setValue(Blob value) {
        this.value = value;
    }

    public Img() {
    }
}
