package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ImgDao;
import com.jimstar.easyshop.entity.Img;
import com.jimstar.easyshop.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;

/**
 * Created by 63289 on 2016/12/21.
 */
@Service
public class ImgService {
    @Autowired
    private ImgDao imgDao;
    public boolean addAnImageByValue(Blob value){
        Img img=new Img();
        String uid= UUIDGenerator.genShort();
        img.setId(uid);
        img.setValue(value);
        return imgDao.add(img);
    }

    public boolean addAnImageByUidAndValue(String uid, Blob value){
        Img img=new Img();
        img.setId(uid);
        img.setValue(value);
        return imgDao.add(img);
    }
}
