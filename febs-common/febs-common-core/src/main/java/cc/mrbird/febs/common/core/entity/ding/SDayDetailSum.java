package cc.mrbird.febs.common.core.entity.ding;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Entity
 *
 * @author MrBird
 * @date 2020-05-23 14:08:32
 */
@Data
@TableName("s_day_detail_sum")
public class SDayDetailSum  {

    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 考勤组名称
     */
    @ExcelProperty("考勤组")
    private String groupName;

    /**
     * 班次名称
     */
    @ExcelProperty("班次")
    private String className;

    /**
     * 职称
     */
    @ExcelIgnore
    @TableField(exist = false)
    private String position;
    /**
     * 部门
     */
    @ExcelProperty("部门")
    @TableField(exist = false)
    private String deptName="";


    /**
     * 工号
     */
    @ExcelProperty("工号")
    @TableField(exist = false)
    private String jobnumber = "";

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    @TableField(exist = false)
    private String name;



    /**
     * 时间
     */
    @ExcelProperty("时间")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date workDate;

    @TableField(exist = false)
    private String workDateStr;
    /**
     * 分析是否有异常
     */
    @ExcelIgnore
    private Integer isError;

    /**
     * 异常详情
     */
    @ExcelProperty("异常详情")
    private String errorDetail;

    /**
     * 白班出勤天数
     */
    @ExcelProperty({"出勤天数","白班"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayBai;

    /**
     * 夜班出勤天数
     */
    @ExcelProperty({"出勤天数","夜班"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayYe;

    /**
     * 夜班出勤天数
     */
    @ExcelProperty({"出勤天数","夜班(补贴)"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayYebu;
    /**

     /**
     * 迟到工时
     */
    @ExcelProperty("迟到工时(m)")
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal gongshiBaseChidao=BigDecimal.ZERO;

    /**
     * 早退工时
     */
    @ExcelProperty("早退工时(m)")
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal gongshiBaseZaotui=BigDecimal.ZERO;


    /**
     * 旷工工时
     */
    @ExcelProperty("旷工工时(H)")
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal gongshiBaseKuang=BigDecimal.ZERO;

    /**
     * 调整后平时出勤天数
     */
    @ExcelProperty({"工作日", "出勤天数"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDay2;

    /**
     * 调整后工时(H)
     */
    @ExcelProperty({"工作日", "工时(H)"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal gongshiBase2;

    /**
     * 加班工时
     */
    @ExcelProperty({"工作日", "加班工时"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal jiaban2;

    /**
     * 周末出勤天数
     */
    @ExcelProperty({"周末", "周末出勤天数"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayWeekend2;

    /**
     * 周末加班工时
     */
    @ExcelProperty({"周末", "周末加班工时"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal jiabanWeekend2;

    /**
     * 已休节假日出勤天数
     */
    @ExcelProperty({"节假日", "已休节假日出勤天数"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayHolidayBase;

    /**
     * 已休节假日加班工时
     */
    @ExcelProperty({"节假日", "已休节假日加班工时"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal jiabanHolidayBase;

    /**
     * 节假日出勤天数
     */
    @ExcelProperty({"节假日", "节假日出勤天数"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal goWorkDayHoliday2;

    /**
     * 周末加班工时
     */
    @ExcelProperty({"节假日", "节假日加班工时"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal jiabanHoliday2;

    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","平时工作日工时(H)"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal processBaseJiaban;

    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","周末工时(H)"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal processWeekendJiaban;
    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","节假日工时(H)"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal processHolidayJiaban;

    @ExcelProperty({"请假相关", "年假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formNianjia;

    @ExcelProperty({"请假相关", "事假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formShijia;

    @ExcelProperty({"请假相关", "病假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formBingjia;

    @ExcelProperty({"请假相关", "调休"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formTiaoxiu;

    @ExcelProperty({"请假相关", "产检假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formChanjianjia;

    @ExcelProperty({"请假相关", "产假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formChanjia;

    @ExcelProperty({"请假相关", "陪产假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formPeichanjia;

    @ExcelProperty({"请假相关", "婚假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formHunjia;

    @ExcelProperty({"请假相关", "工伤假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formLijia;

    @ExcelProperty({"请假相关", "丧假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formSangjia;

    @ExcelProperty({"请假相关", "哺乳假"})
    @TableField(updateStrategy = FieldStrategy.IGNORED )
    private BigDecimal formBurujia;


    /**
     * userId
     */
    @ExcelIgnore
    private String userid;

    /**
     * 是否可以修改 0 可以修改 1 不能修改(H)
     */
    @ExcelIgnore
    private Integer isUpdate;

    @TableField(exist = false)
    private String payPlace;
    /**
     * 当天补卡次数
     */
    @ExcelProperty("补卡次数")
    private Integer bukaNum=0;

}
