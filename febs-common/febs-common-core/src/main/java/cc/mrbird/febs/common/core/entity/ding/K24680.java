package cc.mrbird.febs.common.core.entity.ding;

import cc.mrbird.febs.common.core.converter.KUserLeaveTypeExcelConverter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Data
public class K24680 {

    /**
     *
     */
    @ExcelIgnore
    private String id;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;

    /**
     * 工号
     */
    @ExcelProperty("工号")
    private String jobnumber;

    /**
     * 职称
     */
    @ExcelProperty("职称")
    private String position;
    /**
     * 考勤组
     */
    @ExcelProperty("考勤组")
    private String groupName;

    /**
     * 周末排班加班时数
     */
    @ExcelProperty("周末排班加班时数")
    private String jiabanWeekend2;
    /**
     * 加班转调休时数
     */
    @ExcelProperty("周末加班转调休时数")
    private String processWeekendTiaoxiu;
    /**
     * 17:30到晚上打卡时间
     */
    @ExcelProperty("17:30到晚上打卡时间")
    private String freeJiaban;

    @ExcelIgnore
    private String timeFrom;
    @ExcelIgnore
    private String timeTo;

    public void setJiabanWeekend2(String jiabanWeekend2) {
        this.jiabanWeekend2 = jiabanWeekend2 != null && jiabanWeekend2.equals("0.00") ? null : jiabanWeekend2;
    }

    public void setProcessWeekendTiaoxiu(String processWeekendTiaoxiu) {
        this.processWeekendTiaoxiu = processWeekendTiaoxiu != null && processWeekendTiaoxiu.equals("0.00") ? null : processWeekendTiaoxiu;
    }

    public void setFreeJiaban(String freeJiaban) {
        this.freeJiaban = freeJiaban != null && freeJiaban.equals("0.00") ? null : freeJiaban;
    }
}