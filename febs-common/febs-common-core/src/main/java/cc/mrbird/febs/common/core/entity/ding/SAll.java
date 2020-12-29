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
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * userId
     */
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

    @TableField(exist = false)
    private String payPlace;
    /**
     * 时间
     */
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @TableField("work_date")
    private Date workDate;

    /**
     * 出勤天数-白班
     */
    @TableField("go_work_day_bai")
    private BigDecimal goWorkDayBai;
    /**
     * 出勤天数-晚班
     */
    @TableField("go_work_day_ye")
    private BigDecimal goWorkDayYe;
    /**
     * 
     */
    @TableField("go_work_day_yebu")
    private BigDecimal goWorkDayYebu;
    /**
     * 
     */
    @TableField("go_work_day2")
    private BigDecimal goWorkDay2;

    /**
     * 
     */
    @TableField("gongshi_base2")
    private BigDecimal gongshiBase2;
    /**
     * 
     */
    @TableField("jiaban2")
    private BigDecimal jiaban2;
    /**
     * 
     */
    @TableField("go_work_day_weekend2")
    private BigDecimal goWorkDayWeekend2;
    /**
     * 
     */
    @TableField("jiaban_weekend2")
    private BigDecimal jiabanWeekend2;
    /**
     * 已休节假日出勤天数
     */
    @TableField("go_work_day_holiday_base")
    private BigDecimal goWorkDayHolidayBase;
    /**
     * 已休节假日工时
     */
    @TableField("jiaban_holiday_base")
    private BigDecimal jiabanHolidayBase;
    /**
     * 节假日出勤天数
     */
    @TableField("go_work_day_holiday2")
    private BigDecimal goWorkDayHoliday2;
    /**
     * 节假日工时
     */
    @TableField("jiaban_holiday2")
    private BigDecimal jiabanHoliday2;

    /**
     * 早退工时
     */
    @TableField("gongshi_base_zaotui")
    private BigDecimal gongshiBaseZaotui;
    /**
     * 迟到工时
     */
    @TableField("gongshi_base_chidao")
    private BigDecimal gongshiBaseChidao;
    /**
     * 
     */
    @TableField("gongshi_base_kuang")
    private BigDecimal gongshiBaseKuang;

    /**
     * 
     */
    @TableField("form_nianjia")
    private BigDecimal formNianjia;
    /**
     * 
     */
    @TableField("form_shijia")
    private BigDecimal formShijia;
    /**
     * 
     */
    @TableField("form_bingjia")
    private BigDecimal formBingjia;
    /**
     * 
     */
    @TableField("form_tiaoxiu")
    private BigDecimal formTiaoxiu;
    /**
     * 
     */
    @TableField("form_chanjianjia")
    private BigDecimal formChanjianjia;
    /**
     * 
     */
    @TableField("form_chanjia")
    private BigDecimal formChanjia;
    /**
     * 
     */
    @TableField("form_peichanjia")
    private BigDecimal formPeichanjia;
    /**
     * 
     */
    @TableField("form_hunjia")
    private BigDecimal formHunjia;
    /**
     * 
     */
    @TableField("form_lijia")
    private BigDecimal formLijia;
    /**
     * 
     */
    @TableField("form_sangjia")
    private BigDecimal formSangjia;
    /**
     * 
     */
    @TableField("form_burujia")
    private BigDecimal formBurujia;
    /**
     * 审批加班相关-平时工作日工时
     */
    @TableField("process_base_jiaban")
    private BigDecimal processBaseJiaban;
    /**
     * 审批加班相关-周末工时
     */
    @TableField("process_weekend_jiaban")
    private BigDecimal processWeekendJiaban;
    /**
     * 审批加班相关-节假日工时
     */
    @TableField("process_holiday_jiaban")
    private BigDecimal processHolidayJiaban;
    /**
     * 当天补卡次数
     */
    @TableField("buka_num")
    private Integer bukaNum;

    /**
     * OA品质奖罚
     */
    @TableField("oa_award_sum")
    private BigDecimal oaAwardSum;

    /**
     * OA品质奖罚
     */
    @TableField("oa_kpi_number")
    private BigDecimal oaKpiNumber;

    /**
     * 是否可以修改 0 可以修改 1 不能修改
     */
    @TableField("is_update")
    private Integer isUpdate;

    /**
     * 分析是否有异常
     */
    @TableField("is_error")
    private Byte isError;

    /**
     * 异常详情
     */
    @TableField("error_detail")
    private String errorDetail;

}