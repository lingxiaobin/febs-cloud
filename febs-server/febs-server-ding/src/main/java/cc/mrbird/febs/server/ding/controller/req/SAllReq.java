package cc.mrbird.febs.server.ding.controller.req;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SAllReq extends BaseReq{

    private String[] oaAwardSum;

    private String[] oaKpiNumber;

    private String[] oaTeamPieceRate;

    private String[] eatNum;

}
