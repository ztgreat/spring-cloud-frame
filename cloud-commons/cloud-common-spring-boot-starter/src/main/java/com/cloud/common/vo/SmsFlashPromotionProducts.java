package com.cloud.common.vo;


import com.cloud.common.entity.pms.PmsProduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SmsFlashPromotionProducts implements Serializable {
    private Long id;
    private BigDecimal flashPromotionPrice;
    private Integer flashPromotionCount;
    private Integer flashPromotionLimit;
    private Integer sort;
    private PmsProduct product;
}
