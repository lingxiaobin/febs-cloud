package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
*
* @author MrBird
* @date 2020-12-04 14:33:28
*/
@Data
@TableName("s_all")
public class SAll {

    /**
     * 
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * userId
     */
    @ExcelProperty("员工UserID")
    @TableField("userid")
    private String userid;

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
    @TableField("work_date")
    private Date workDate;

    /**
     * 白班出勤天数
     */
    @ExcelProperty("出勤天数-白班")
    @TableField("go_work_day_bai")
    private BigDecimal goWorkDayBai;
    /**
     * 夜班出勤天数
     */
    @ExcelProperty("出勤天数-夜班")
    @TableField("go_work_day_ye")
    private BigDecimal goWorkDayYe;
    /**
     * 夜班出勤天数
     */
    @ExcelProperty("出勤天数-夜班(补贴)")
    @TableField("go_work_day_yebu")
    private BigDecimal goWorkDayYebu;

    /**
     * 早退工时
     */
    @ExcelProperty("早退工时(m)")
    private BigDecimal gongshiBaseZaotui;
    /**
     * 迟到工时
     */
    @ExcelProperty("迟到工时(m)")
    @TableField("gongshi_base_chidao")
    private BigDecimal gongshiBaseChidao;
    /**
     * 旷工工时
     */
    @ExcelProperty("旷工工时(H)")
    private BigDecimal gongshiBaseKuang;
    /**
     * 调整后平时出勤天数
     */
    @ExcelProperty("工作日-出勤天数")
    @TableField("go_work_day2")
    private BigDecimal goWorkDay2;

    /**
     * 调整后工时(H)
     */
    @ExcelProperty("工作日-工时(H)")
    @TableField("gongshi_base2")
    private BigDecimal gongshiBase2;
    /**
     * 加班工时
     */
    @ExcelProperty("工作日-加班工时")
    @TableField("jiaban2")
    private BigDecimal jiaban2;
    /**
     * 周末出勤天数
     */
    @ExcelProperty("周末出勤天数")
    @TableField("go_work_day_weekend2")
    private BigDecimal goWorkDayWeekend2;
    /**
     * 周末加班工时
     */
    @ExcelProperty("周末加班工时")
    private BigDecimal jiabanWeekend2;
    /**
     * 已休法定假天数
     */
    @ExcelProperty("已休法定假天数")
    @TableField("go_work_day_holiday_base")
    private BigDecimal goWorkDayHolidayBase;
    /**
     * 已休法定假日时数
     */
    @ExcelProperty("已休法定假日时数")
    @TableField("jiaban_holiday_base")
    private BigDecimal jiabanHolidayBase;
    /**
     * 法定假日加班天数
     */
    @ExcelProperty("法定假日加班天数")
    @TableField("go_work_day_holiday2")
    private BigDecimal goWorkDayHoliday2;
    /**
     * 法定假加班时数
     */
    @ExcelProperty( "法定假加班时数")
    @TableField("jiaban_holiday2")
    private BigDecimal jiabanHoliday2;

    /**
     * 审批加班相关(H)
     */
    @ExcelProperty("特批加班-平时工作日工时(H)")
    @TableField("process_base_jiaban")
    private BigDecimal processBaseJiaban;
    /**
     * 审批加班相关(H)
     */
    @ExcelProperty("特批加班-周末工时(H)")
    @TableField("process_weekend_jiaban")
    private BigDecimal processWeekendJiaban;
    /**
     * 审批加班相关(H)
     */
    @ExcelProperty("特批加班-节假日工时(H)")
    @TableField("process_holiday_jiaban")
    private BigDecimal processHolidayJiaban;

    @ExcelProperty("年假")
    @TableField("form_nianjia")
    private BigDecimal formNianjia;
    @ExcelProperty("事假")
    @TableField("form_shijia")
    private BigDecimal formShijia;
    @ExcelProperty("病假")
    @TableField("form_bingjia")
    private BigDecimal formBingjia;
    @ExcelProperty( "产检假")
    @TableField("form_chanjianjia")
    private BigDecimal formChanjianjia;
    @ExcelProperty("产假")
    private BigDecimal formChanjia;
    @ExcelProperty("陪产假")
    private BigDecimal formPeichanjia;
    @ExcelProperty("婚假")
    @TableField("form_hunjia")
    private BigDecimal formHunjia;
    @ExcelProperty("工伤假")
    private BigDecimal formLijia;
    @ExcelProperty("丧假")
    @TableField("form_sangjia")
    private BigDecimal formSangjia;
    @ExcelProperty("哺乳假")
    @TableField("form_burujia")
    private BigDecimal formBurujia;

    @ExcelProperty("补卡次数")
    @TableField("buka_num")
    private Integer bukaNum;


    @ExcelProperty("OA品质奖罚")
    @TableField("oa_award_sum")
    private BigDecimal oaAwardSum;


    @ExcelProperty("OA品质奖罚")
    @TableField("oa_kpi_number")
    private BigDecimal oaKpiNumber;


    @ExcelProperty("OA团体计件")
    @TableField("oa_team_piece_rate")
    private BigDecimal oaTeamPieceRate;

    @ExcelProperty("伙食补助")
    @TableField("eat_num")
    private BigDecimal eatNum;

    @ExcelProperty("工资核算地")
    @TableField(exist = false)
    private String payPlace;
    /**
     * 是否可以修改 0 可以修改 1 不能修改
     */
    @TableField("is_update")
    @ExcelIgnore
    private Integer isUpdate;

    /**
     * 分析是否有异常
     */
    @TableField("is_error")
    @ExcelIgnore
    private Byte isError;

    /**
     * 异常详情
     */
    @ExcelIgnore
    @TableField("error_detail")
    private String errorDetail;

}