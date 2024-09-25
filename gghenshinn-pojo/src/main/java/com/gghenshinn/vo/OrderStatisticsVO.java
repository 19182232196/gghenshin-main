package com.gghenshinn.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    private Integer toBeConfirmed;

    private Integer confirmed;

    private Integer deliveryInProgress;
}
