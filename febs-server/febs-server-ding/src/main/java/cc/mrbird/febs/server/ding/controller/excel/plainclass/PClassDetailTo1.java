package cc.mrbird.febs.server.ding.controller.excel.plainclass;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PClassDetailTo1 {
    /**
     *
     */

    @ExcelProperty("工号")
    private String jobnumber;

    /**
     * 响应状态
     */
    @ExcelProperty("响应状态")
    private String signInState;


    @ExcelIgnore
    private Date signInTime;
    /**
     * 签到时间
     */
    @ExcelProperty("签到时间")
    private String signInTimeStr;
}
