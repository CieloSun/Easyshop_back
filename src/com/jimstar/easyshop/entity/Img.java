package com.jimstar.easyshop.entity;

import com.jimstar.easyshop.util.DigestUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@Entity
public class Img implements Serializable {
    private String id;
    private byte[] value;

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
    //@Basic(fetch = FetchType.LAZY)
    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Img{" + id + ":" + DigestUtil.Crc32Encode(getValue()) + '}';
    }
}
