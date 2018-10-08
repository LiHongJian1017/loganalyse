package com.li.loganalyse.utils;

import com.ggstar.util.ip.IpHelper;

import static com.li.loganalyse.utils.trd.ip;

/**
 * Created By HongjianLi
 */
public class IpUtils {
public String ip(String ip){
        return IpHelper.findRegionByIp(ip);
    }
}
