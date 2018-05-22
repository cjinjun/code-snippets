package com.code.codesnippets.alipay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class AlipayConfig {

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	@Value("${alipay.partner}")
	public static String PARTNER;

	// 支付宝分配给开发者的应用ID
	@Value("${alipay.app.id}")
	public static String APPID;

	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String SELLER_ID = PARTNER;

	// 调用接口名称，无需修改
	public static String SERVICE = "create_direct_pay_by_user";

	// 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	@Value("${app.private.key}")
	public static String PRIVATE_KEY;

	// 请求数据格式
	public static String FORMAT = "json";

	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	@Value("${alipay.public.key}")
	public static String ALIPAY_PUBLIC_KEY;

	// 支付宝消息验证地址
	public static String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	// 支付宝提供给商户的服务接入网关URL(新)
	public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String NOTIFY_URL;

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String RETURN_URL;

	// 签名算法
	@Value("${alipay.sign.type}")
	public static String SIGN_TYPE;

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String INPUT_CHARSET = "utf-8";

	// 支付类型 ，无需修改
	public static String PAYMENT_TYPE = "1";

	// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
	public static String ANTI_PHISHING_KEY = "";

	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String EXTER_INVOKE_IP = "";

	//支付宝最新服务器URL
	public static String OPENAPI_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

	// HTTP(S) 连接超时时间，单位毫秒
	public static final int HTTPCONNECTTIMEOUTMS = 3 * 1000;
	// HTTP(S) 读数据超时时间，单位毫秒
	public static final int HTTPREADTIMEOUTMS = 5 * 1000;

}
