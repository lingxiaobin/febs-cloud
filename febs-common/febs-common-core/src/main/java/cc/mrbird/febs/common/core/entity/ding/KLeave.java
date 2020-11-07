package cc.mrbird.febs.common.core.entity.ding;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Entity
 *
 * @author MrBird
 * @date 2020-04-28 10:52:26
 */
@Data
public class KLeave {

    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String userid;

    /**
     *
     */
    private Integer processType;

    /**
     * 
     */
    private String processInstanceId;

    /**
     *  审批实例业务编号  撤销,修改的和原来的是一样的
     */
    private String businessId;

    /**
     * 休假的当天时间
     */
    private Date workDate;

    /**
     *
     */
    private String formComponentValue="";

    /**
     * 请假的开始时间
     */
    private Date fromTime;

    /**
     * 请假的结束时间
     */
    private Date toTime;

    /**
     * 申请开始时间
     */
    private Date createTime;

    /**
     * 申请结束时间
     */
    private Date finishTime;

    /**
     * 请假的长度  天
     */
    private BigDecimal durationInDay=BigDecimal.ZERO;
    /**
     * 请假的长度  小时
     */
    private BigDecimal durationInHour=BigDecimal.ZERO;
    /**
     * 请假的类型
     */
    private String extension;

    /**
     * 请假的时间单位  小时,半天,全天
     */
    private String unit;

    /**
     * 申请的类型 , 表里都是请假申请信息
     */
    private String pushTag;

    /**
     * 请假的总长度  天
     */
    private BigDecimal durationInDaySum=BigDecimal.ZERO;
    /**
     * 请假的总长度  小时
     */
    private BigDecimal durationInHourSum=BigDecimal.ZERO;
    /**
     * 一天的工作时长 分钟
     */
    private BigDecimal workTimeMinutes;

    /**
     * 审批实例url，可在钉钉内跳转到审批页面
     */
    private String url;

    /**
     *
     */
    private Integer state;


    /**
     *  审批实例业务动作，MODIFY表示该审批实例是基于原来的实例修改而来，REVOKE表示该审批实例对原来的实例进行撤销，NONE表示正常发起
     *
     */
    private String bizAction;

}
