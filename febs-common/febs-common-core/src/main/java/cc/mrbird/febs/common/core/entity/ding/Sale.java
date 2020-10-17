package cc.mrbird.febs.common.core.entity.ding;


import lombok.Data;


/**
 * lot 记录详情表   Entity
 *
 * @author MrBird
 * @date 2019-12-28 14:57:29
 */
@Data
public class Sale {

    /**
     *  接单工厂
     */
    private String abbrName;
    /**
     *  销售员
     */
    private String salesname;
    /**
     *  查询的时间
     */
//    @TableField("time")
    private String time;

    /**
     * 元(万)
     */
//    @TableField("kuan")
    private String kuan="0";

    /**
     * 款
     */
//    @TableField("yuan")
    private String yuan="0";

    /**
     * 平米
     *
     */
//    @TableField("pingmi")
    private String pingmi="0";


}
