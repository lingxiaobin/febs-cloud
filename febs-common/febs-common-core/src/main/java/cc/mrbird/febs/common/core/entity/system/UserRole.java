package cc.mrbird.febs.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MrBird
 */
@Data
@TableName("t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -3166012934498268403L;

    public static final Long DEFAULT_ROLE_ID = 2L;

    @TableField(value = "USER_ID")  //TableId
    private Long userId;

    @TableField(value = "ROLE_ID")  //TableId
    private Long roleId;

}