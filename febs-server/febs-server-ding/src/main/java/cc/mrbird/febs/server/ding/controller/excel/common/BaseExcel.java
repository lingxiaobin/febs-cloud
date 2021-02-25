package cc.mrbird.febs.server.ding.controller.excel.common;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

@Data
public class BaseExcel {

    @ExcelProperty("异常信息")   // IndexedColors.GREEN
    @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor =10)   //设置背景
    private String errorDetail;
}
