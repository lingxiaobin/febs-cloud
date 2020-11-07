package cc.mrbird.febs.server.ding.service;



import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.PClassType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-11-06 08:20:43
 */
public interface IPClassTypeService extends IService<PClassType> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param pClassType pClassType
     * @return IPage<PClassType>
     */
    IPage<PClassType> findPClassTypes(QueryRequest request, PClassType pClassType);

    /**
     * 查询（所有）
     *
     * @param pClassType pClassType
     * @return List<PClassType>
     */
    List<PClassType> findPClassTypes(PClassType pClassType);

    /**
     * 新增
     *
     * @param pClassType pClassType
     */
    void createPClassType(PClassType pClassType);

    /**
     * 修改
     *
     * @param pClassType pClassType
     */
    void updatePClassType(PClassType pClassType);

    /**
     * 删除
     *
     * @param pClassType pClassType
     */
    void deletePClassType(PClassType pClassType);
}
