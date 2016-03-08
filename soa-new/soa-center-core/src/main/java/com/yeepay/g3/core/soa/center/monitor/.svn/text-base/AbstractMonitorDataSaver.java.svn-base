package com.yeepay.g3.core.soa.center.monitor;

import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.config.ConfigNotifyListener;
import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;

import java.util.Map;

/**
 * @author：wang.bao
 * @since：2015年8月18日 上午9:10:30
 * @version:
 */
public abstract class AbstractMonitorDataSaver implements MonitorDataSaver {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMonitorDataSaver.class);

    private static boolean subscribedConfig = false;
    private boolean inited = false;

    public AbstractMonitorDataSaver() {
        MonitorDataSaverFactory.register(this);
        subscribeConfig();
        preInit();
    }

    @Override
    public void save(MonitorData data) {
        try {
            if (!inited) {
                return;
            }
            doSave(data);
        } catch (Exception e) {
            logger.error("保存监控数据时报错，statistics: " + data.toString(), e);
        }
    }

    private void preInit() {
        ConfigParam param = ConfigurationUtils.getSysConfigParam(SoaCenterConst.CONFIG_KEY_SAVER_SWITCH);
        if (param == null || param.getValue() instanceof Map) {
            Map<String, Boolean> saverSwitch = (Map<String, Boolean>) param.getValue();
            if (null != saverSwitch && !saverSwitch.isEmpty() && saverSwitch.containsKey(getName()) && saverSwitch.get(getName())) {
                init();
            }
        }
    }

    public synchronized void init() {
        if (inited) {
            return;
        }
        try {
            doInit();
            inited = true;
        } catch (Exception e) {
            logger.error("init " + this.getName() + " saver error!", e);
            inited = false;
        }
    }

    public synchronized void destory() {
        if (!inited) {
            return;
        }
        try {
            doDestory();
            inited = false;
        } catch (Exception e) {
            logger.error("destory " + this.getName() + " saver error!", e);
            inited = true;
        }
    }

    protected abstract void doInit() throws Exception;

    protected abstract void doDestory() throws Exception;

    protected abstract void doSave(MonitorData data) throws Exception;

    private void subscribeConfig() {
        if (!subscribedConfig) {
            synchronized (AbstractMonitorDataSaver.class) {
                if (!subscribedConfig) {
                    ConfigurationUtils.addListener(new ConfigNotifyListener() {
                        @Override
                        public void notifyConfig(
                                Map<String, Map<String, Object>> configParams) {
                            try {
                                if (configParams == null || configParams.isEmpty()) {
                                    return;
                                }
                                Map<String, Object> sysConfig = configParams
                                        .get(ConfigurationUtils.CONFIG_TYPE_SYS);
                                if (sysConfig == null || sysConfig.isEmpty()) {
                                    return;
                                }

                                @SuppressWarnings("unchecked")
                                Map<String, Boolean> saverSwitch = (Map<String, Boolean>) sysConfig
                                        .get(SoaCenterConst.CONFIG_KEY_SAVER_SWITCH);
                                if (saverSwitch == null || saverSwitch.isEmpty()) {
                                    return;
                                }

                                for (Map.Entry<String, Boolean> entry : saverSwitch.entrySet()) {
                                    Boolean isOpen = entry.getValue();
                                    if (null != isOpen && isOpen) {
                                        MonitorDataSaverFactory.getSaver(entry.getKey()).init();
                                    } else {
                                        MonitorDataSaverFactory.getSaver(entry.getKey()).destory();
                                    }
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        }
                    });
                    subscribedConfig = true;
                }
            }
        }
    }

}
