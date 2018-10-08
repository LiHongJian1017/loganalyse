package com.li.loganalyse.entity;

import java.io.Serializable;

/**
 * Created By HongjianLi
 */
@Table("one")
public class One implements Serializable {
    @Id
    @Column("num")
    private Integer num;
    @Column("type")
    private String type;
    @Column("id")
    private String id;
    @Column("cishu")
    private Integer cishu;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCishu() {
        return cishu;
    }

    public void setCishu(Integer cishu) {
        this.cishu = cishu;
    }

    public One(Integer num, String type, String id, Integer cishu) {
        this.num = num;
        this.type = type;
        this.id = id;
        this.cishu = cishu;
    }

    public One() {

    }

}
