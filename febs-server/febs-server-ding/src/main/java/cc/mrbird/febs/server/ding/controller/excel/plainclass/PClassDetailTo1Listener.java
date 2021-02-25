package cc.mrbird.febs.server.ding.controller.excel.plainclass;

import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.excel.plainclass.PClassDetailTo1;
import cc.mrbird.febs.server.ding.service.IPClassDetailService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class PClassDetailTo1Listener extends AnalysisEventListener<PClassDetailTo1> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 30;
    List<PClassDetailTo1> list = new ArrayList<>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private IPClassDetailService pClassDetailService;

    private Integer classId;

    public int sumALl=0;

    public PClassDetailTo1Listener(IPClassDetailService pClassDetailService,Integer classId) {
        this.pClassDetailService = pClassDetailService;
        this.classId = classId;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(PClassDetailTo1 data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        sumALl++;
        if (StringUtils.isNotEmpty(data.getSignInTimeStr()) && data.getSignInTimeStr().length()>16){
            try {
                data.setSignInTime(DateUtil.getDateParse(data.getSignInTimeStr()));
            } catch (ParseException e) {
                log.info("解析数据,时间转化异常:{}", JSON.toJSONString(data));
            }
        }
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());

       for (PClassDetailTo1 data: list){
           LambdaUpdateWrapper<PClassDetail> updateWrapper=new LambdaUpdateWrapper<>();
           updateWrapper.eq(PClassDetail::getClassId,classId);
           updateWrapper.eq(PClassDetail::getJobnumber,data.getJobnumber());
           PClassDetail pClassDetail=new PClassDetail();
           pClassDetail.setClassId(classId);
           pClassDetail.setJobnumber(data.getJobnumber());
           pClassDetail.setSignInState(data.getSignInState());
           pClassDetail.setSignInTime(data.getSignInTime());
           pClassDetail.setIsSignIn(true);
           pClassDetail.setUpdateTime(new Date());
           pClassDetailService.saveOrUpdate(pClassDetail,updateWrapper);
//           pClassDetailService.save(pClassDetail);
       }
        log.info("存储数据库成功！");

    }

}
