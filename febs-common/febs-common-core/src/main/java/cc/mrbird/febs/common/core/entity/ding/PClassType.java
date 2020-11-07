package cc.mrbird.febs.common.core.entity.ding;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
*
* @author MrBird
* @date 2020-11-06 08:20:43
*/
@Data
@TableName("p_class_type")
public class PClassType {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("name")
    private String name;

    /**
     * 
     */
    @TableField("type")
    private Integer type;

}