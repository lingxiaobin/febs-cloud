package cc.mrbird.febs.common.core.entity.ding;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.*;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-11-06 08:35:28
 */
@Data
@TableName(value = "s_salary_setting", autoResultMap = true)
public class SSalarySetting {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField(value = "lock_kaoqin", typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String,Integer> lockKaoqin;

    @TableField(value = "flush_kaoqin", typeHandler = FastjsonTypeHandler.class)
    private LinkedHashMap<String,Integer> flushKaoqin;

    @TableField("work_date")
    private Date workDate;

    @TableField(exist = false)
    private String workDateStr;

}
