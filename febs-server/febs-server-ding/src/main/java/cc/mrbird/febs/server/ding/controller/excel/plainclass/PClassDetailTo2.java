package cc.mrbird.febs.server.ding.controller.excel.plainclass;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PClassDetailTo2 {


    @ExcelProperty("工号")
    private String jobnumber;
    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */


    @ExcelProperty("通过情况")
    private String fractionState;

    @ExcelProperty("最新一次成绩")
    private BigDecimal fraction;

    @ExcelProperty("最近一次考试时间")
    private Date fractionTime;
}
