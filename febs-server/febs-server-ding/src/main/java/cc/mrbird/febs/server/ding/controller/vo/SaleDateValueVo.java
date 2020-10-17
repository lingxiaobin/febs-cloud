package cc.mrbird.febs.server.ding.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor //生成全参数构造函数
public class SaleDateValueVo {
    private String Date;
    private String type;
    private BigDecimal value;
}
