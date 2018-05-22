package com.code.codesnippets.wxpay;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class WXPayConfig {

	// 微信开放平台审核通过的应用APPID
	@Value("${wxpay.app.id}")
	public static String APP_ID;

	// 微信支付分配的商户号
	@Value("${wxpay.key}")
	public static String MCH_ID;

	// 商户平台设置的密钥key
	@Value("${wxpay.key}")
	public static String KEY;

	// 加签算法
	@Value("wxpay.sign.type}")
	public static String SIGN_TYPE = "MD5";

	// 返回状态码 - 失败
	public static final String FAIL = "FAIL";

	// 返回状态码 - 成功
	public static final String SUCCESS = "SUCCESS";
	// 加签算法 HMAC-SHA256
	public static final String HMACSHA256 = "HMAC-SHA256";
	// 加签算法 MD5
	public static final String MD5 = "MD5";

	// 签名
	public static final String FIELD_SIGN = "sign";
	// 加签方式
	public static final String FIELD_SIGN_TYPE = "sign_type";
	// HTTP(S) 连接超时时间，单位毫秒
	public static final int HTTPCONNECTTIMEOUTMS = 3 * 1000;
	// HTTP(S) 读数据超时时间，单位毫秒
	public static final int HTTPREADTIMEOUTMS = 5 * 1000;

	// 查询订单URL
	public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	public enum SignType {
		MD5, HMACSHA256
	}

}
