package com.yeepay.g3.core.soa.center.rabbitmq;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;
import com.yeepay.g3.facade.soa.center.facade.ServiceQueryFacade;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import org.junit.Assert;

/**
 * Created by Administrator on 2015/1/28.
 */
public class ImitateSendMonitorData implements Runnable{
    private void testQueryService(){
        try {
            HessianProxyFactory factory = new HessianProxyFactory();
            String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
                    + ServiceQueryFacade.class.getName();
            ServiceQueryFacade serviceQueryFacade = (ServiceQueryFacade) factory
                    .create(ServiceQueryFacade.class, serviceUrl);
            ServiceQueryParam queryParam = new ServiceQueryParam();
            queryParam.setQueryInput("settle");
            queryParam.setQueryType(QueryTypeEnum.SERVICE);
            for (int i = 0; i < 10; i++) {
                serviceQueryFacade.queryService(queryParam);
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
    @Override
    public void run() {
        for(int i=0;i<6;i++){
            System.out.println("第" + i + "次循环");
            testQueryService();
        }
    }
}
