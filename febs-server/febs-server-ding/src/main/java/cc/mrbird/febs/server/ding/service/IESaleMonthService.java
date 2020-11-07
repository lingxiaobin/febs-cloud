package cc.mrbird.febs.server.ding.service;



import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.ESaleMonth;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-10-26 10:43:46
 */
public interface IESaleMonthService extends IService<ESaleMonth> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param eSaleMonth eSaleMonth
     * @return IPage<ESaleMonth>
     */
    IPage<ESaleMonth> findESaleMonths(QueryRequest request, ESaleMonth eSaleMonth);

    /**
     * 查询（所有）
     *
     * @param eSaleMonth eSaleMonth
     * @return List<ESaleMonth>
     */
    List<ESaleMonth> findESaleMonths(ESaleMonth eSaleMonth);

    /**
     * 新增
     *
     * @param eSaleMonth eSaleMonth
     */
    void createESaleMonth(ESaleMonth eSaleMonth);

    /**
     * 修改
     *
     * @param eSaleMonth eSaleMonth
     */
    void updateESaleMonth(ESaleMonth eSaleMonth);

    /**
     * 删除
     *
     * @param eSaleMonth eSaleMonth
     */
    void deleteESaleMonth(ESaleMonth eSaleMonth);
}
