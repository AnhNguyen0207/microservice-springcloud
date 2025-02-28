package jmaster.io.statisticservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticDTO {
    private Long id;
    private String message;
    private Date createdDate;
}
