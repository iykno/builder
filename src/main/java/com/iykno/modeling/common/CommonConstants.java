package com.iykno.modeling.common;

public final class CommonConstants {
	private CommonConstants() {
	}

	// 模 100
	public final static int MOD = 100;
	// 通用的缓存时间 半小时
	public final static int default_expire = 1800;
	public final static int batch_select_size = 5000;
	public final static int batch_insert_size = 2000;

	public final static String MODELING_CACHE_PREFIX = "modeling_";

	public static final String operator_comma = "\\,";

	public static final String operator_point = "\\.";

}
