package com.test.fileupload.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类提供返回前端提示信息的通用方法
 * @author sunc
 *
 */
public class OperateTipsUtils {

	/**
	 * 返回给前端的结果状态码：成功
	 */
	public static int STATUS_OK = 0;

	/**
	 * 返回给前端的结果状态码：业务错误（删除时有关联提示信息等）
	 */
	public static int STATUS_ERROR = 1;

	/**
	 * 返回给前端的结果状态码：没有上传过该文件的任何分片数据
	 */
	public static int STATUS_NO_CHUNK = 21;

	/**
	 * 返回给前端的结果状态吗：上传过该文件的分片数据
	 */
	public static int STATUS_CHUNKED = 22;

	/**
	 * 返回给前端的结果状态码：后台代码异常，此时弹出提示信息“服务器数据错误”
	 */
	public static int STATUS_EXCEPTION = 3;


	private static final Map<String,Object> OPERATE_TIPS = new HashMap<String,Object>(5);

	/**
	 * 异步请求-提示信息-添加是否合并的标记
	 * @param state 成功或者失败的状态
	 * @param msg 成功或者失败的提示信息
	 * @param detail 成功或者失败的详细信息
	 * @return 包含有状态，提示信息，详细信息的map集合。
	 */

	public static Map<String,Object> operateTips(int state, String msg, boolean needMerge, String detail){
		OPERATE_TIPS.clear();
		OPERATE_TIPS.put("state", state);
		OPERATE_TIPS.put("needMerge", needMerge);
		OPERATE_TIPS.put("msg", msg);
		OPERATE_TIPS.put("detail", detail);
		return OPERATE_TIPS;
	}



	/**
	 * 根据变长paraKeyValues生成Map<String, Object>，若paraKeyValues长度为奇数，最后一个key的值设置为null
	 * @param paraKeyValues 变长参数，存放要生成参数的key和Value,偶数索引为key值，奇数索引为Value值
	 * @return
	 */
	public static Map<String, Object> generateParasMap(String...paraKeyValues){
		Map<String, Object> paraValues = new HashMap<String, Object>();
		int length = paraKeyValues.length;
		for(int index = 0; index < length;) {
			if(index + 1 <= length -1) {
				paraValues.put(paraKeyValues[index++], paraKeyValues[index++]);
			}else {
				paraValues.put(paraKeyValues[index++], null);
			}
		}
		return paraValues;
	}


}
