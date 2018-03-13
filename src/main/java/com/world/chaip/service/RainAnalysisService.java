package com.world.chaip.service;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.exchangeRain.ArbitrarilyDay;
import com.world.chaip.entity.exchangeRain.XunQi;
import com.world.chaip.entity.exchangeRain.YearAndMonthRain;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 专业分析
 * @author BZH
 */

public interface RainAnalysisService {

    /**
     * 汛期降雨量分析对比
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<XunQi> getRainXQCompared(Date time, List<String> adcd, List<String> systemTypes,
                                  List<String> stcdOrStnm);

    /**
     * 年逐月降雨量分析对比
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<YearAndMonthRain> getRainNZYCompared(Date time, List<String> adcd, List<String> systemTypes,
                                              List<String> stcdOrStnm);

    /**
     * 任意日降雨量分析对比
     * @param beginTime
     * @param endTime
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<ArbitrarilyDay> getRainRYCompared(Date beginTime, Date endTime, List<String> adcd, List<String> systemTypes,
                                           List<String> stcdOrStnm);

}
