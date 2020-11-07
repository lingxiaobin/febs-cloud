package cc.mrbird.febs.common.core.entity.ding;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-04-24 15:43:55
 */
@Data
@TableName("e_wipline_user")
public class EWiplineUser {


    /**
     * userid
     */
    @TableField("user_name")
    private String userName;

    /**
     * userid
     */
    @TableField("wip_user_name")
    private String wipUserName;


}
