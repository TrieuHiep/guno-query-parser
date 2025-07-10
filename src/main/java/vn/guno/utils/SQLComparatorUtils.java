package vn.guno.utils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class SQLComparatorUtils {

    public static boolean sqlEquals(String sql1, String sql2) throws JSQLParserException {
        Statement stmt1 = CCJSqlParserUtil.parse(sql1);
        Statement stmt2 = CCJSqlParserUtil.parse(sql2);
        return stmt1.toString().equals(stmt2.toString());
    }

}
