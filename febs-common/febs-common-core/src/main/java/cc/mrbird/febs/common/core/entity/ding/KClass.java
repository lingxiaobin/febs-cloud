package cc.mrbird.febs.common.core.entity.ding;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-04-25 14:24:29
 */
@Data
public class KClass {

    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String name;

    private Boolean isRest = false;


    /**
     * 休息时间 的开始时间
     */
    private Date onDutyRestTime;

    private Date offDutyRestTime;

    /**
     * 休息时间是否是次日  0 是当天 1 是次日
     */
    private Integer onDutyAcross;

    private Integer offDutyAcross;


    private String checkTimeList;

    private String checkAcrossList;
}
