package com.jimstar.easyshop.service;

import com.jimstar.easyshop.dao.ImgDao;
import com.jimstar.easyshop.entity.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImgService {
    private final ImgDao imgDao;

    @Autowired
    public ImgService(ImgDao imgDao) {
        this.imgDao = imgDao;
    }

    public Img addAnImageByValue(byte[] value) {
        return imgDao.add(value);
    }

    public Img getImgById(String id) {
        return imgDao.selectById(id);
    }

}
