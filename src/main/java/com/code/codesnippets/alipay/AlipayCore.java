package com.code.codesnippets.alipay;

import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.httpclient.methods.multipart.FilePartSource;
//import org.apache.commons.httpclient.methods.multipart.PartSource;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class AlipayCore {
	
	 /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /** 
     * 生成文件摘要
     * @param strFilePath 文件路径
     * @param file_digest_type 摘要算法
     * @return 文件摘要结果
     */
    public static String getAbstract(String strFilePath, String file_digest_type) throws IOException {
//        PartSource file = new FilePartSource(new File(strFilePath));
//    	if(file_digest_type.equals("MD5")){
//    		return DigestUtils.md5Hex(file.createInputStream());
//    	}
//    	else if(file_digest_type.equals("SHA")) {
//    		return DigestUtils.sha256Hex(file.createInputStream());
//    	}
//    	else {
//    		return "";
//    	}
        return "";
    }
    
    /**
     * 将请求参数转为Map集合
     * @Title:			paraTransfer 
     * @description: 	TODO
     * @param: 			@param requestParamMap
     * @param: 			@return
     * @return: 		Map<String,String>
     * @throws:			
     * @author:		 	shemi
     * @Date: 		 	2017年7月14日 上午10:49:48
     */
    public static Map<String, String> paraTransfer(Map<String,String[]> requestParamMap){
    	Map<String, String> params = new HashMap<String, String>();
    	//遍历请求参数
		for (Iterator<String> iter = requestParamMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParamMap.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
    }
	

}
