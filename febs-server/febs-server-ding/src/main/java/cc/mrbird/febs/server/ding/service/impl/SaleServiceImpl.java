package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.Sale;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.vo.SaleDateValueVo;
import cc.mrbird.febs.server.ding.mapper.SaleMapper;
import cc.mrbird.febs.server.ding.service.ISaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SaleServiceImpl implements ISaleService {

    private final SaleMapper saleMapper;

    @Override
    public List<SaleDateValueVo> selectCountDay7() {
        List<SaleDateValueVo> newSales = new ArrayList<>(20);
        Map map = new HashMap(2);
        List<Sale> sales = saleMapper.selectCountDay7(map);
        map.put("isWaifa", "true");
        List<Sale> sales2 = saleMapper.selectCountDay7(map);
        Map<String, String> saleMap = new HashMap<>();
        for (Sale sale : sales) {
            saleMap.put("总" + sale.getTime(), sale.getYuan());
        }
        for (Sale sale : sales2) {
            saleMap.put("外发" + sale.getTime(), sale.getYuan());
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -20);
        while (cal.getTime().getTime() <= new Date().getTime()) {
            String dateFormat = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
            if (saleMap.get("外发" + dateFormat) != null) {
                newSales.add(new SaleDateValueVo(dateFormat, "外发", new BigDecimal(saleMap.get("外发" + dateFormat))));
                newSales.add(new SaleDateValueVo(dateFormat, "非外发", new BigDecimal(saleMap.get("总" + dateFormat)).subtract(new BigDecimal(saleMap.get("外发" + dateFormat)))));
            } else {
                newSales.add(new SaleDateValueVo(dateFormat, "外发", BigDecimal.ZERO));
                if (saleMap.get("总" + dateFormat) != null) {
                    newSales.add(new SaleDateValueVo(dateFormat, "非外发", new BigDecimal(saleMap.get("总" + dateFormat))));
                } else {
                    newSales.add(new SaleDateValueVo(dateFormat, "非外发", BigDecimal.ZERO));
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return newSales;
    }

    @Override
    public List<SaleDateValueVo> selectCountDayByAbbrname() {
        List<SaleDateValueVo> newSales = new ArrayList<>(20);
        Map map = new HashMap(2);
        List<Sale> sales = saleMapper.selectCountDayByAbbrname(map);
        map.put("isWaifa", "true");

        Map<String, String> saleMap = new HashMap<>();
        for (Sale sale : sales) {
            saleMap.put("总" + sale.getTime(), sale.getYuan());
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -20);
        while (cal.getTime().getTime() <= new Date().getTime()) {
            String dateFormat = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
            if (saleMap.get("外发" + dateFormat) != null) {
                newSales.add(new SaleDateValueVo(dateFormat, "外发", new BigDecimal(saleMap.get("外发" + dateFormat))));
                newSales.add(new SaleDateValueVo(dateFormat, "非外发", new BigDecimal(saleMap.get("总" + dateFormat)).subtract(new BigDecimal(saleMap.get("外发" + dateFormat)))));
            } else {
                newSales.add(new SaleDateValueVo(dateFormat, "外发", BigDecimal.ZERO));
                if (saleMap.get("总" + dateFormat) != null) {
                    newSales.add(new SaleDateValueVo(dateFormat, "非外发", new BigDecimal(saleMap.get("总" + dateFormat))));
                } else {
                    newSales.add(new SaleDateValueVo(dateFormat, "非外发", BigDecimal.ZERO));
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return newSales;
    }

    @Override
    public List<SaleDateValueVo> selectCountBySalesname() {

        List<SaleDateValueVo> newSales = new ArrayList<>(20);

        Map map = new HashMap(2);
        List<Sale> salesNameList = saleMapper.selectBySalesname(map);
        List<Sale> saleCountList = saleMapper.selectCountBySalesname(map);

        Map<String, String> saleMap = new HashMap<>();
        for (Sale sale : saleCountList) {
            saleMap.put(sale.getSalesname() + sale.getTime(), sale.getYuan());
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -20);
        while (cal.getTime().getTime() <= new Date().getTime()) {
            String dateFormat = DateUtil.getDateFormat(cal.getTime(), "yyyy-MM-dd");
            for (Sale sale : salesNameList) {
                if (saleMap.get(sale.getSalesname() + dateFormat) != null) {
                    newSales.add(new SaleDateValueVo(dateFormat, sale.getSalesname(), new BigDecimal(saleMap.get(sale.getSalesname() + dateFormat))));
                } else {
                    newSales.add(new SaleDateValueVo(dateFormat, sale.getSalesname(), BigDecimal.ZERO));
                }
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return newSales;
    }
}
