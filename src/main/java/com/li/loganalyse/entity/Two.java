package com.li.loganalyse.entity;

import java.io.Serializable;

/**
 * Created By HongjianLi
 */
@Table("two")
public class Two implements Serializable {
    @Id
    @Column("num")
    private Integer num;
    @Column("type")
    private String type;
    @Column("id")
    private String id;
    @Column("cishu")
    private Integer cishu;
    @Column("city")
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Two() {
    }

    public Two(Integer num, String type, String id, Integer cishu, String city) {
        this.num = num;
        this.type = type;
        this.id = id;
        this.cishu = cishu;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Two{" +
                "num=" + num +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", cishu=" + cishu +
                ", city='" + city + '\'' +
                '}';
    }
}
