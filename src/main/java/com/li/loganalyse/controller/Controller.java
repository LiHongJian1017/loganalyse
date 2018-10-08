package com.li.loganalyse.controller;

import com.li.loganalyse.entity.One;
import com.li.loganalyse.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created By HongjianLi
 */
@RestController
public class Controller {
    @Autowired
    private LogService logService;
    //处理请求的方法
    @RequestMapping(value = "/insertOne")
    public int insertOne(){
        //依赖service
        //json
        return logService.insertOne();
    }

    @RequestMapping(value = "/insertTwo")
    public int insertTwo(){
        //依赖service
        //json
        return logService.insertTwo();
    }

    @RequestMapping(value = "/insertThree")
    public int insertThree(){
        //依赖service
        //json
        return logService.insertThree();
    }

    @RequestMapping(value = "/job")
    public int job(){
        //依赖service
        //json
        logService.job();
        return 1;
    }

    @RequestMapping(value = "/load")
    public int load(){
        //依赖service
        //json
        logService.load();
        return 1;
    }
//    @RequestMapping(value = "/one")
//    public List<One> findOne(){
//        //依赖service
//        return logService.findOne();//json
//    }
}

