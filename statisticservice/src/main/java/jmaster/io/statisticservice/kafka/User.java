package jmaster.io.statisticservice.kafka;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String name;
    private int age;
    private Date createdDate;
    private String key;
}

