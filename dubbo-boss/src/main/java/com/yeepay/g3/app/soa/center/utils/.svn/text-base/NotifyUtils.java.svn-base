package com.yeepay.g3.app.soa.center.utils;

import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.encrypt.Digest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author by menghao.chen
 */
@Component
public class NotifyUtils {

	public String generateSign(String username, String ruleName, String secretKey, List<String> recipients) {
		return Digest.md5Digest(username + ruleName + StringUtils.join(recipients, ",") + secretKey);
	}
}
