package com.hg.ecommerce.dao.support;

public interface IOperators {
	
	/**
	 * Database common operation,you can override in subclass,you also can add new operation in subclass
	 */
	public static final String EQ = " = ";
	public static final String NE = " <> ";
	public static final String MOD = " % ";
	public static final String BIT_LEFT = " << ";
	public static final String BIT_RIGHT = " >> ";
	public static final String BIT_AND = " & ";
	public static final String BIT_OR = " | ";
	public static final String BIT_XOR = " ^ ";
	public static final String LOG_AND = " && ";
	public static final String LOG_OR = " || ";
	public static final String MULTI = " * ";
	public static final String PLUS = " + ";
	public static final String SUB = " - ";
	public static final String DIV = " / ";
	public static final String LT = " < ";
	public static final String LE = " <= ";
	public static final String GT = " > ";
	public static final String GE = " >= ";
	public static final String BETWEEN = " BETWEEN ";
	public static final String IS = " IS ";
	public static final String NOT = " NOT ";
	public static final String IS_NULL = " IS NULL ";
	public static final String IS_NOT = " IS_NOT ";
	public static final String IS_NOT_NULL = "IS NOT NULL";
	public static final String LIKE = " LIKE ";
	public static final String NOT_LIKE = " NOT LIKE ";
	public static final String IN = " IN ";
	public static final String NOT_IN = " NOT IN ";
	public static final String NULL = " NULL ";
	//key words
	public static final String INSERT = " INSERT INTO ";//select
	public static final String VALUES = " VALUES ";
	public static final String DELETE = " DELETE FROM ";//delete
	public static final String UPDATE = " UPDATE ";//update
	public static final String SET = " SET ";
	public static final String SELECT = " SELECT ";//select
	public static final String DISTINCT = " DISTINCT ";
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";//where
	public static final String AND = " AND ";
	public static final String OR = " OR ";
	public static final String ORDER = " ORDER BY ";
	public static final String DESC = " DESC ";
	public static final String ASC = " ASC ";
	public static final String LIMIT = " LIMIT ";
	//syntax operators
	public static final String LP = " ( ";//left parentheses
	public static final String RP = " ) ";//right parenthesess
	public static final String TLP = "(";//trim left parenthesess
	public static final String TRP = ")";//trim right parenthesess
	public static final String COMMA = ",";
	public static final String QUOTE = "'";//quotation
	public static final String BLANK = " ";
	//functions operations
	public static final String AVG = " AVG";
	public static final String COUNT = " COUNT";
	public static final String FIRST = " FIRST";
	public static final String LAST = " LAST";
	public static final String MAX = " MAX";
	public static final String MIN = " MIN";
	public static final String SUM = " SUM";
	public static final String GROUPBY = " GROUP BY ";
	public static final String HAVING = " HAVING ";
	public static final String UCASE = " UCASE";
	public static final String LCASE = " LCASE";
	public static final String MID = " MID";
	public static final String LEN = " LEN";
	public static final String ROUND = " ROUND";
	public static final String NOW = " NOW() ";
	public static final String FORMAT = " FORMAT";
	public static final String AS = " AS ";
	//sort
	public enum SORT{
		ASC(" ASC "),DESC(" DESC ");
		String sort;
		private SORT(String sort){this.sort = sort;}
		public String getSort() {return this.sort;}
	}
	//and||or
	public enum AOR{
		AND(" AND "),OR(" OR ");
		String aor;
		private AOR(String aor) {this.aor=aor;}
		public String getAor(){return this.aor;}
	}

}
