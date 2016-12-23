package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ImgDao;
import com.jimstar.easyshop.entity.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;


@Service
public class ImgService {
    private final ImgDao imgDao;

    @Autowired
    public ImgService(ImgDao imgDao) {
        this.imgDao = imgDao;
    }

    public boolean addAnImageByValue(Blob value) {
        Img img = new Img();
        img.setValue(value);
        return imgDao.add(img);
    }

    public Img getImgById(String id) {
        return imgDao.selectById(id);
    }

}
