package com.jimstar.easyshop.controller;

import com.jimstar.easyshop.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 63289 on 2016/12/23.
 */
@Controller
@RequestMapping("/Main")
public class ImgController {
    @Autowired
    private ImgService imgService;

}
