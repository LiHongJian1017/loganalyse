package com.li.loganalyse.entity;

import java.io.Serializable;

/**
 * Created By HongjianLi
 */
@Table("three")
public class Three implements Serializable{
    @Id
    @Column("num")
    private Integer num;
    @Column("type")
    private String type;
    @Column("id")
    private String id;
    @Column("traffic")
    private Integer traffic;

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

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Three() {
    }

    public Three(Integer num, String type, String id, Integer traffic) {
        this.num = num;
        this.type = type;
        this.id = id;
        this.traffic = traffic;
    }

    @Override
    public String toString() {
        return "Three{" +
                "num=" + num +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", traffic=" + traffic +
                '}';
    }
}
