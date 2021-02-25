package cc.mrbird.febs.server.ding.controller.excel.salary;

import cc.mrbird.febs.server.ding.controller.excel.common.BaseExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SEatExcel extends BaseExcel {

    @ExcelProperty("工号")
    private String jobnumber;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("消费金额")
    private String useNum;
}
