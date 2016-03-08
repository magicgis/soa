/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.log.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.core.soa.log.dao.CommonBizLogDao;
import com.yeepay.g3.core.soa.log.entity.CommonBizLog;
import com.yeepay.g3.core.soa.log.service.BizLogService;
import com.yeepay.g3.utils.common.DateUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.soa.log.BizLogDTO;

/**
 * @author：wang.bao
 * @since：2014年11月5日 下午4:22:01
 * @version:
 */
@Component
public class BizLogServiceImpl implements BizLogService {
	private static final Logger logger = LoggerFactory
			.getLogger(BizLogServiceImpl.class);

	@Autowired
	private JdbcTemplate logJdbcTemplate;

	@Autowired
	private CommonBizLogDao commonBizLogDao;

	private static final String COL_ID = "ID";
	private static final String COL_APPLICATION = "APPLICATION";
	private static final String COL_GUID = "GUID";

	private static final int MAX_LEN = 3000;

	@Override
	public void save(BizLogDTO bizLog) {
		try {
			CommonBizLog log = DataConvertUtils.convert(bizLog,
					CommonBizLog.class);
			if (bizLog.isLogTable()) {
				if (bizLog.getColumns().containsKey(COL_ID)
						&& bizLog.getColumns().get(COL_ID) == null) {
					bizLog.getColumns().remove(COL_ID);
				}
				log.setLogContent(log.getLogContent() + ":"
						+ StringUtils.join(bizLog.getColumns().values(), ","));
			}
			String logContent = log.getLogContent();
			if (logContent.length() > MAX_LEN) {
				logContent = StringUtils
						.substringByByte(logContent, 0, MAX_LEN);
			}
			log.setLogContent(logContent);
			commonBizLogDao.add(log);

			if (bizLog.isLogTable()) {
				String sql = this.buildSql(bizLog);
				logJdbcTemplate.execute(sql);
			}
		} catch (Exception e) {
			logger.error("saveBizLog error. bizLog : " + bizLog + " errMsg : "
					+ e.getMessage());
		}
	}

	@Override
	public void save(List<BizLogDTO> bizLogs) {
		try {
			List<CommonBizLog> logs = new ArrayList<CommonBizLog>();
			Map<String, String> sqls = new HashMap<String, String>();
			Map<String, List<Object[]>> sqlArgs = new HashMap<String, List<Object[]>>();
			for (BizLogDTO bizLog : bizLogs) {
				CommonBizLog log = DataConvertUtils.convert(bizLog,
						CommonBizLog.class);
				if (bizLog.isLogTable()) {
					if (bizLog.getColumns().containsKey(COL_ID)
							&& bizLog.getColumns().get(COL_ID) == null) {
						bizLog.getColumns().remove(COL_ID);
					}
					log.setLogContent(log.getLogContent()
							+ ":"
							+ StringUtils.join(bizLog.getColumns().values(),
									","));
				}
				String logContent = log.getLogContent();
				if (logContent.length() > MAX_LEN) {
					logContent = logContent.substring(0, MAX_LEN);
				}
				log.setLogContent(logContent);
				logs.add(log);
				if (bizLog.isLogTable()) {
					// 按表名分组
					this.prepareBatch(bizLog, sqls, sqlArgs);
				}
			}
			commonBizLogDao.batchInsert("batchInsert", logs);
			for (String talbeName : sqls.keySet()) {
				String sql = sqls.get(talbeName);
				List<Object[]> args = sqlArgs.get(talbeName);
				logJdbcTemplate.batchUpdate(sql, args);
			}
		} catch (Exception e) {
			logger.error("batch saveBizLog error. errMsg : " + e.getMessage());
		}
	}

	private String buildSql(BizLogDTO bizLog) {
		String tableName = bizLog.getLogContent();
		Map<String, Object> columns = bizLog.getColumns();

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(tableName);
		sql.append(" (");
		sql.append(StringUtils.join(columns.keySet(), ","));
		sql.append(" ) VALUES (");
		boolean first = true;
		for (String col : columns.keySet()) {
			if (first) {
				first = false;
			} else {
				sql.append(",");
			}
			Object v = columns.get(col);
			if (v == null) {
				if (StringUtils.equalsIgnoreCase(col, COL_APPLICATION)) {
					sql.append("'");
					sql.append(bizLog.getApplication());
					sql.append("'");
				} else if (StringUtils.equalsIgnoreCase(col, COL_GUID)) {
					sql.append("'");
					sql.append(bizLog.getGuid());
					sql.append("'");
				} else {
					sql.append("null");
				}
			} else if (v instanceof Integer || v instanceof Long
					|| v instanceof Double || v instanceof Float) {
				sql.append(v);
			} else if (v instanceof Boolean) {
				Boolean value = (Boolean) v;
				sql.append(value ? 1 : 0);
			} else if (v instanceof Date) {
				Date value = (Date) v;
				sql.append("'");
				sql.append(DateUtils.getTimeStampStr(value));
				sql.append("'");
			} else {
				sql.append("'");
				sql.append(v.toString().replace("\n", " ").replace("\t", "  ")
						.replace("'", ""));
				sql.append("'");
			}
		}
		sql.append(" )");
		return sql.toString();
	}

	private void prepareBatch(BizLogDTO bizLog, Map<String, String> sqls,
			Map<String, List<Object[]>> sqlArgs) {
		String tableName = bizLog.getLogContent();
		Map<String, Object> columns = bizLog.getColumns();
		String s = sqls.get(tableName);
		if (StringUtils.isBlank(s)) {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ");
			sql.append(tableName);
			sql.append(" (");
			sql.append(StringUtils.join(columns.keySet(), ","));
			sql.append(" ) VALUES (");
			sql.append(StringUtils.repeat("?", ",", columns.size()));
			sql.append(" )");
			sqls.put(tableName, sql.toString());
		}
		List<Object[]> args = sqlArgs.get(tableName);
		if (args == null) {
			args = new ArrayList<Object[]>();
			sqlArgs.put(tableName, args);
		}
		Object[] colValues = new Object[columns.size()];
		int i = 0;
		for (String colName : columns.keySet()) {
			Object v = columns.get(colName);
			if (v == null) {
				if (StringUtils.equalsIgnoreCase(colName, COL_APPLICATION)) {
					v = bizLog.getApplication();
				} else if (StringUtils.equalsIgnoreCase(colName, COL_GUID)) {
					v = bizLog.getGuid();
				}
			}
			colValues[i++] = v;
		}
		args.add(colValues);
	}
}
