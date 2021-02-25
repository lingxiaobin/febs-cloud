package cc.mrbird.febs.server.ding.controller.excel.salary;

import cc.mrbird.febs.common.core.entity.ding.SEat;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.excel.common.UploadExcelRes;
import cc.mrbird.febs.server.ding.controller.excel.redis.ExcelRedis;
import cc.mrbird.febs.server.ding.service.ISEatService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class SEatToListener extends AnalysisEventListener<SEatExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<SEatExcel> list = new ArrayList<>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private ISEatService isEatService;
    private ExcelRedis<SEatExcel> excelRedis;


    private UploadExcelRes res = new UploadExcelRes();
    private String workDate;

    public SEatToListener(ISEatService isEatService, ExcelRedis<SEatExcel> excelRedis, String workDate) {
        this.isEatService = isEatService;
        this.excelRedis = excelRedis;
        this.workDate = workDate;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @SneakyThrows
    @Override
    public void invoke(SEatExcel data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
//        saveData();
/*        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }*/
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
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() throws ParseException {
        log.info("{}条数据，开始存储数据库！", list.size());
        Date date = DateUtil.getDateParse(workDate, "yyyy-MM");
        //查询
        List<SEat> sEats = isEatService.findSEatIdsBy(workDate);  //获取已经使用的金额
        List<SEat> successList = new ArrayList<>();    // 修改成功
        List<SEatExcel> errorList = new ArrayList<>();   // 修改失败
        Map<String, SEat> jobMap = new HashMap<>();   //总数据
        Set<String> haveJobSet=new HashSet<>();    //工号重复
        for (SEat sEat : sEats) {
            if (sEat.getJobnumber()!=null) {
                jobMap.put(sEat.getJobnumber(), sEat);
            }
        }
        for (SEatExcel data : list) {
            SEat sEat = jobMap.get(data.getJobnumber());
            if (sEat != null) {
                if (!haveJobSet.contains(data.getJobnumber())){
                    try {
                        sEat.setUseNum(new BigDecimal(data.getUseNum()));
                        successList.add(sEat);
                    } catch (Exception e) {
                        data.setErrorDetail("金额格式有误!");
                        errorList.add(data);
                        continue;
                    }
                    haveJobSet.add(data.getJobnumber());
                }else {
                    data.setErrorDetail("工号重复!");
                    errorList.add(data);
                    continue;
                }
            } else {
                data.setErrorDetail("工号匹配失败");
                errorList.add(data);
            }
        }
        if (successList.size()>0) {
            isEatService.updateBatchById(successList);
        }
        if (errorList.size() > 0) {
            res.errorId = excelRedis.setErrorDate(errorList);
        }
        res.errorCount = errorList.size();
        res.successCount = successList.size();
        log.info("存储数据库成功！");
    }

    public UploadExcelRes getUploadExcelRes() {
        return res;
    }
}
