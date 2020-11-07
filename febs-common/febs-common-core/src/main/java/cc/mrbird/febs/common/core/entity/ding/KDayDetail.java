package cc.mrbird.febs.common.core.entity.ding;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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
public class KDayDetail  {

    public KDayDetail(String workDateStr,String className){
        this.workDateStr=workDateStr;
        if (className==null){
            className="排休";
        }
        this.className=className;
    }
    /**
     *
     */
    @ExcelIgnore
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
    private String position;
    /**
     * 部门
     */
    @ExcelProperty("部门")
    private String deptName="";


    /**
     * 工号
     */
    @ExcelProperty("工号")
    private String jobnumber = "";

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    private String name;



    /**
     * 时间
     */
    @ExcelProperty("时间")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date workDate;

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
    private BigDecimal goWorkDayBai;

    /**
     * 夜班出勤天数
     */
    @ExcelProperty({"出勤天数","夜班"})
    private BigDecimal goWorkDayYe;

    /**
     * 夜班出勤天数
     */
    @ExcelProperty({"出勤天数","夜班(补贴)"})
    private BigDecimal goWorkDayYebu;
    /**

    /**
     * 迟到工时
     */
    @ExcelProperty("迟到工时(m)")
    private BigDecimal gongshiBaseChidao=BigDecimal.ZERO;

    /**
     * 早退工时
     */
    @ExcelProperty("早退工时(m)")
    private BigDecimal gongshiBaseZaotui=BigDecimal.ZERO;


    /**
     * 旷工工时
     */
    @ExcelProperty("旷工工时(H)")
    private BigDecimal gongshiBaseKuang=BigDecimal.ZERO;

    /**
     * 调整后平时出勤天数
     */
    @ExcelProperty({"工作日", "出勤天数"})
    private BigDecimal goWorkDay2;

    /**
     * 调整后工时(H)
     */
    @ExcelProperty({"工作日", "工时(H)"})
    private BigDecimal gongshiBase2;

    /**
     * 加班工时
     */
    @ExcelProperty({"工作日", "加班工时"})
    private BigDecimal jiaban2;

    /**
     * 周末出勤天数
     */
    @ExcelProperty({"周末", "周末出勤天数"})
    private BigDecimal goWorkDayWeekend2;

    /**
     * 周末加班工时
     */
    @ExcelProperty({"周末", "周末加班工时"})
    private BigDecimal jiabanWeekend2;

    /**
     * 节假日出勤天数
     */
    @ExcelProperty({"节假日", "节假日出勤天数"})
    private BigDecimal goWorkDayHoliday2;

    /**
     * 周末加班工时
     */
    @ExcelProperty({"节假日", "节假日加班工时"})
    private BigDecimal jiabanHoliday2;
    /**
     * 审批相关
     */
    @ExcelProperty("审批相关")
    private String formComponentValue;

    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","平时工作日工时(H)"})
    private BigDecimal processBaseJiaban;

    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","周末工时(H)"})
    private BigDecimal processWeekendJiaban;

    /**
     * 审批加班相关(H)
     */
    @ExcelIgnore
    private BigDecimal processWeekendTiaoxiu;
    /**
     * 审批加班相关(H)
     */
    @ExcelProperty({"审批加班相关","节假日工时(H)"})
    private BigDecimal processHolidayJiaban;


    /**
     * 上班1
     */
    @ExcelProperty("上班1")
    private String firstStartTimeStr;

    /**
     * 下班1
     */
    @ExcelProperty("下班1")
    private String firstEndTimeStr;

    /**
     * 上班2
     */
    @ExcelProperty("上班2")
    private String twoStartTimeStr;

    /**
     * 下班2
     */
    @ExcelProperty("下班2")
    private String twoEndTimeStr;

    /**
     * 上班3
     */
    @ExcelProperty("上班3")
    private String threeStartTimeStr;

    /**
     * 下班3
     */
    @ExcelProperty("下班3")
    private String threeEndTimeStr;

    /**
     * 上班状态1
     */
    @ExcelProperty("上班状态1")
    private String firstStartState;

    /**
     * 下班状态1
     */
    @ExcelProperty("下班状态1")
    private String firstEndState;

    /**
     * 上班状态2
     */
    @ExcelProperty("上班状态2")
    private String twoStartState;

    /**
     * 下班状态2
     */
    @ExcelProperty("下班状态2")
    private String twoEndState;

    /**
     * 上班状态3
     */
    @ExcelProperty("上班状态3")
    private String threeStartState;

    /**
     * 下班状态3
     */
    @ExcelProperty("下班状态3")
    private String threeEndState;

    @ExcelProperty({"请假相关", "年假"})
    private BigDecimal formNianjia;

    @ExcelProperty({"请假相关", "事假"})
    private BigDecimal formShijia;

    @ExcelProperty({"请假相关", "病假"})
    private BigDecimal formBingjia;

    @ExcelProperty({"请假相关", "调休"})
    private BigDecimal formTiaoxiu;

    @ExcelProperty({"请假相关", "产检假"})
    private BigDecimal formChanjianjia;

    @ExcelProperty({"请假相关", "产假"})
    private BigDecimal formChanjia;

    @ExcelProperty({"请假相关", "陪产假"})
    private BigDecimal formPeichanjia;

    @ExcelProperty({"请假相关", "婚假"})
    private BigDecimal formHunjia;

    @ExcelProperty({"请假相关", "工伤假"})
    private BigDecimal formLijia;

    @ExcelProperty({"请假相关", "丧假"})
    private BigDecimal formSangjia;

    @ExcelProperty({"请假相关", "哺乳假"})
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

    /**
     * 当天补卡次数
     */
    @ExcelProperty("补卡次数")
    private Integer bukaNum=0;

}
