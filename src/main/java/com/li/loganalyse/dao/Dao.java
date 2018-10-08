package com.li.loganalyse.dao;

import com.li.loganalyse.entity.One;
import com.li.loganalyse.entity.Three;
import com.li.loganalyse.entity.Two;

import java.util.List;

/**
 * Created By HongjianLi
 */
public interface Dao {
   List<One> findOne();

   List<Two> findTwo();

   List<Three> findThree();

   void job();

   void load();
}
