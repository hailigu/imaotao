package com.codeReading.core.search.constant;

public final class ReservedWords {
	//基础
	public static final String PROPERTIES = "properties";
	public static final String BOOST = "boost";
	public static final String NULL_VALUE = "null_value"; //When there is a (JSON) null value for the field, use the null_value as the field value. Defaults to not adding the field at all.
	public static final String INDEX_NAME = "index_name";
	public static final String COPY_TO = "copy_to";
	public static final String DYNAMIC = "dynamic";
	public static final String STRICT = "strict";
	public static final String REQUIRED = "required";
	public static final String PATH = "path";
	public static final String ID = "id";
	
	public final class BOOLEAN {
		public static final String TRUE = "true";
		public static final String FALSE = "false";
	}
	public final class OPENABLE {
		public static final String ENABLED = "enabled";
		public static final String DISABLED = "disabled";
	}
	public final class SCORE_MODE {
		public static final String AVG = "avg";
		public static final String SUM = "sum";
		public static final String MAX = "max";
		public static final String NONE = "none";
		public static final String SCORE = "score";
	}
	//field
	public static final String _FIELD = "FIELD";
	public final class _FIELD {
		public static final String ALL = "_all";
		public static final String PARENT = "_parent";
		public static final String ROUTING = "_routing";
		public static final String TIMESTAMP = "_timestamp";
	}
	//数据类型
	public static final String TYPE = "type";
	public final class TYPE {
		public static final String STRING = "string";
		public static final String INTEGER = "integer";
		public static final String LONG = "long";
		public static final String FLOAT = "float";
		public static final String DOUBLE = "double";
		public static final String BOOLEAN = "boolean";
		public static final String BINARY = "binary";
		public static final String NULL = "null";
		public static final String OBJECT = "object"; //one-to-one
		public static final String NESTED = "nested"; //one-to-many
	}
	
	//索引
	public static final String STORE = "store"; //默认值为NO，暂时不需要设置
	public static final String INDEX = "index";
	public static final String FIELDS = "fields";
	public final class INDEX {
		public static final String NO = "no"; //不对该字段进行索引（无法搜索）
		public static final String ANALYZER = "analyzer";
		public static final String INDEX_ANALYZER = "indexAnalyzer";
		public static final String SEARCH_ANALYZER = "searchAnalyzer";
		public static final String NOT_ANALYZED = "not_analyzed"; //以单个关键词进行索引
		public static final String IGNORE_ABOVE = "ignore_above";
		public static final String TERM_VECTOR = "term_vector";
		public static final String WITH_POSITIONS_OFFSETS = "with_positions_offsets"; 
	}
	public final class LANGUAGE{
		public static final String CN = "cn";
		public static final String EN = "en";
	}
	public final class ANALYZER{
		public static final String STANDARD = "standard";
		public static final String ENGLISH = "english";
		public static final String IK = "ik";
		public static final String IK_SMART = "ik_smart";
		public static final String IK_MAX_WORD = "ik_max_word";
	}
	//包含
	public static final String INCLUDE_IN_ALL = "include_in_all";
	public static final String INCLUDE_IN_PARENT = "include_in_parent";
	public static final String INCLUDE_IN_ROOT = "include_in_root";
}
