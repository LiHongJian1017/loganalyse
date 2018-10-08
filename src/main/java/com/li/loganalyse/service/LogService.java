package com.li.loganalyse.service;

import com.li.loganalyse.dao.Dao;
import com.li.loganalyse.entity.One;
import com.li.loganalyse.entity.Three;
import com.li.loganalyse.entity.Two;
import com.li.loganalyse.utils.JDBCUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By HongjianLi
 */
@Service
public class LogService {
    @Autowired
    private Dao dao;

    public List<One> findOne(){ return dao.findOne(); }
    public int insertOne(){
        return JDBCUtil.insert1(dao.findOne());
    }

    public List<Two> findTwo(){
        return dao.findTwo();
    }
    public int insertTwo(){
        return JDBCUtil.insert2(dao.findTwo());
    }

    public List<Three> findThree(){
        return dao.findThree();
    }
    public int insertThree(){
        return JDBCUtil.insert3(dao.findThree());
    }

    public void job(){
        dao.job();
    }

    public void load(){
        dao.load();
    }
}

