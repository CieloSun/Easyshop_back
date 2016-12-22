package com.jimstar.easyshop.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
public class Img implements Serializable {
    private String id;
    private Blob value;

    public Img() {
    }

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
}
