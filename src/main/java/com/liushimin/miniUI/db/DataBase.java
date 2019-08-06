package com.liushimin.miniUI.db;

import oracle.sql.CLOB;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static String user = "lshim";
    private static String pwd = "123456";

    private static String colSql = "SELECT column_name FROM user_col_comments WHERE table_name = @tablename";
//    private static String colSql = "SELECT column_name FROM user_col_comments WHERE table_name = 'T_DEPARTMENT'";

    private static Map<String, String> insertSqlCache = new HashMap<String, String>();
    private static Map<String, String> deleteSqlCache = new HashMap<String, String>();
    private static Map<String, String> updateSqlCache = new HashMap<String, String>();
    private static Map<String, String> selectSqlCache = new HashMap<String, String>();

    private static Map<String, List<String>> tableFieldsCache = new HashMap<String, List<String>>();
    private static Map<String, Map<String, Class>> tableFieldsTypeCache = new HashMap<String, Map<String, Class>>();


    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

//    public Connection conn;
//    private PreparedStatement stmt;

    protected static PreparedStatement open(String sql, Map param, Connection conn) throws Exception {
        PreparedStatement stmt = createPreparedStatement(conn, sql, param);
        return stmt;
    }

    public static void close(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    /**
     * 增删改查设置PreparedStatement的sql与参数
     * 比如
     * insert into tableName (aa,bb) values (@aa,@bb)
     * -->
     * insert into tableName (aa,bb) values (?,?)
     * 然后将param Map中的参数与?一一对应上，返回PreparedStatement
     *
     * @param conn
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    private static PreparedStatement createPreparedStatement(Connection conn, String sql, Map param) throws Exception {
        List paramList = new ArrayList();

        if (param != null) {
            Map lowerParam = new HashMap();
            for (Object key : param.keySet()) {
                Object value = param.get(key.toString());
                String newKey = key.toString().toLowerCase();
                lowerParam.put(newKey, value);
            }

            String regex = "@\\w+";
            Pattern mPattern = Pattern.compile(regex);
            Matcher mMatcher = mPattern.matcher(sql);

            while (mMatcher.find()) {
                String key = mMatcher.group(0);
                sql = sql.replaceFirst(key, "?");
                key = key.substring(1).toLowerCase();
                Object value = lowerParam.get(key);

                Map kv = new HashMap();
                kv.put("key", key);
                kv.put("value", value);
                paramList.add(kv);
            }
        }

        System.out.println(sql);
        PreparedStatement stmt = conn.prepareStatement(sql);

        for (int i = 0; i < paramList.size(); i++) {
            Map kv = (HashMap) paramList.get(i);
            System.out.println(kv);
            Object value = kv.get("value");
            try {
                stmt.setObject(i + 1, value);
            } catch (SQLException e) {
                if (value instanceof Timestamp) {
                    stmt.setObject(i + 1, new Date(((Timestamp)value).getTime()));
                }else if (value instanceof Date) {
                    stmt.setObject(i + 1, new Timestamp(((Date)value).getTime()));
                }else {
                    throw e;
                }
            }
        }
        return stmt;
    }

    /**
     * 查询
     *
     * @param rst
     * @return
     * @throws Exception
     */
    private static List resultSetToList(ResultSet rst) throws Exception {
        ResultSetMetaData md = rst.getMetaData();
        int columnCount = md.getColumnCount();
        List list = new ArrayList();
        while (rst.next()) {
            Map rowData = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                Object v = rst.getObject(i);
                if (v != null && (v.getClass() == java.util.Date.class || v.getClass() == java.sql.Date.class)) {
                    Timestamp ts = rst.getTimestamp(i);
                    v = new java.util.Date(ts.getTime());
                } else if (v != null && v.getClass() == Clob.class) {
                    v = clob2String((CLOB) v);
                }
                rowData.put(md.getColumnName(i), v);
            }
            list.add(rowData);
        }
        return list;
    }

    private static String clob2String(CLOB clob) throws Exception {
        return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
    }

    public static List select(String sql, Map param, Connection conn) throws Exception {
        boolean autoClose = false;
        if (conn == null) {
            autoClose = true;
            conn = getConnection();
        }
        PreparedStatement stmt = open(sql, param, conn);

        ResultSet rst = stmt.executeQuery();
        List list = resultSetToList(rst);

        rst.close();
        stmt.close();
        if (autoClose) {
            close(conn);
        }
        return list;
    }

    /**
     * 获取表所有的字段，小写形式
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    private static List<String> getTableFields(String tableName) throws Exception {
        List<String> tableFields = tableFieldsCache.get(tableName);

        if (tableFields == null || tableFields.size() == 0) {
            Map o = new HashMap();
            o.put("tableName", tableName);
            List tableFieldsList = select(colSql, o, null);
            if (tableFieldsList == null || tableFieldsList.size() == 0) {
                throw new Exception("表" + tableName + "不存在可用字段");
            }
            tableFields = new ArrayList<String>();
            Map<String, String> fieldsMap = null;
            for (Map tableFieldMap : (List<Map>) tableFieldsList) {
                tableFields.add(tableFieldMap.get("COLUMN_NAME").toString().toLowerCase());
            }
            tableFieldsCache.put(tableName, tableFields);
        }
        return tableFields;
    }

    /**
     * 从传入的fields中抽取出tableFields中拥有的字段
     * tableFields,fields不会改变
     * 如果field为空，则取tableFields全字段
     *
     * @param tableFields
     * @param fields
     * @return
     * @throws Exception
     */
    private static List<String> getValuableFields(List<String> tableFields, List<String> fields) throws Exception {
        List<String> valuableFields = new ArrayList<String>(tableFields);
        if (CollectionUtils.isNotEmpty(fields)) {
            List<String> flds = new ArrayList<String>(fields);
            Map<String, String> fieldsMap = new HashMap<String, String>();
            for (int i = 0; i < flds.size(); i++) {
                String s = flds.get(i);
                if (StringUtils.isEmpty(s)) {
                    continue;
                }
                s = s.toLowerCase();
                flds.set(i, s);
                fieldsMap.put(s, s);
            }
            Iterator<String> it = valuableFields.iterator();
            for (; it.hasNext(); ) {
                if (fieldsMap.get(it.next()) == null) {
                    it.remove();
                }
            }
        }
        return valuableFields;
    }

    private static String genCacheKey(String tableName, List<String> valuableFields) {
        String cacheKey = tableName;
        if (CollectionUtils.isNotEmpty(valuableFields)) {
            valuableFields.removeAll(Collections.singleton(null));
            valuableFields.removeAll(Collections.singletonList(""));
            Collections.sort(valuableFields);
            for (String field : valuableFields) {
                cacheKey += "-" + field;
            }
        }
        return cacheKey;
    }

    /**
     * 增删改
     *
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    public static int execute(String sql, Map param, Connection conn) throws Exception {
        boolean autoClose = false;
        if (conn == null) {
            autoClose = true;
            conn = getConnection();
        }
        PreparedStatement stmt = open(sql, param, conn);

        int count = stmt.executeUpdate();
        stmt.close();
        if (autoClose) {
            close(conn);
        }
        return count;
    }

    /**
     * 只有一条记录
     *
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    public static Map selectFirst(String sql, Map param, Connection conn) throws Exception {
        List list = select(sql, param, conn);
        return list.size() == 0 ? null : (Map) list.get(0);
    }

    /**
     * 取单个数据
     *
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    public static Object selectScalar(String sql, Map param, Connection conn) throws Exception {
        Map o = selectFirst(sql, param, conn);
        return getFirstValue(o);
    }

    private static Object getFirstValue(Map o) {
        for (Object key : o.keySet()) {
            Object value = o.get(key.toString());
            return value;
        }
        return null;
    }

    /**
     * 查询出所有表字段
     * 如果传入字段列表为空，则生成全字段插入语句
     * 参数不为空，遍历表字段，对比字段列表，剔除列表中不存在的字段
     * insert into tableName ("field1", "field2"...) values (@field1, @field1...)
     * <p>
     * 第一次生成后存入缓存，后续都从缓存中获取sql
     *
     * @param tableName
     * @param fields
     * @return
     */
    public static String createInsertSql(String tableName, List<String> fields) throws Exception {
        List<String> tableFields = getTableFields(tableName);
        List<String> valuableFields = getValuableFields(tableFields, fields);
        if (valuableFields.size() == 0) {
            throw new Exception("插入字段列表为空");
        }
        String cacheKey = genCacheKey(tableName, CollectionUtils.isEmpty(fields) ? null : valuableFields);
        String sql = insertSqlCache.get(cacheKey);
        if (StringUtils.isBlank(sql)) {
            StringBuilder sb1 = new StringBuilder("insert into " + tableName + " (");
            StringBuilder sb2 = new StringBuilder(") values (");
            for (String tableField : valuableFields) {
                sb1.append(" \"" + tableField + "\",");
                sb2.append(" @" + tableField + ",");
            }
            sql = sb1.substring(0, sb1.lastIndexOf(",")) + sb2.substring(0, sb2.lastIndexOf(",")) + ")";
            System.out.println("第一次获取，进行组装");
            insertSqlCache.put(cacheKey, sql);
        }
        System.out.println(sql);
        return sql;
    }

    public static String createDeleteSql(String tableName, String idField) {
        String sql = deleteSqlCache.get(tableName);
        if (StringUtils.isBlank(sql)) {
            sql = "delete from " + tableName + " where \"" + idField + "\" =@" + idField;
            deleteSqlCache.put(tableName, sql);
        }
        System.out.println(sql);
        return sql;
    }

    public static String createUpdateSql(String tableName, String idField, List<String> fields) throws Exception {
        List<String> tableFields = getTableFields(tableName);
        List<String> valuableFields = getValuableFields(tableFields, fields);
        if (valuableFields.size() == 0 || (valuableFields.size() == 1 && idField.equals(valuableFields.get(0)))) {
            throw new Exception("插入字段列表为空");
        }
        String cacheKey = genCacheKey(tableName, CollectionUtils.isEmpty(fields) ? null : valuableFields);
        String sql = updateSqlCache.get(cacheKey);
        if (StringUtils.isBlank(sql)) {
            StringBuilder sb = new StringBuilder("update " + tableName + " set");
            String s = " where \"" + idField + "\" = @" + idField;
            for (String tableField : valuableFields) {
                if (!idField.equals(tableField)) {
                    sb.append(" \"" + tableField + "\" = @" + tableField + ",");
                }
            }
            sql = sb.substring(0, sb.lastIndexOf(",")) + s;
            System.out.println("第一次获取，进行组装");
            updateSqlCache.put(cacheKey, sql);
        }
        System.out.println(sql);
        return sql;
    }

    /*public static String createSelectByIdSql(String tableName, String idField) {
        String cacheKey = genCacheKey(tableName, new List<String>(Collections.singletonList(idField)));
        String sql = selectSqlCache.get(cacheKey);
        if (StringUtils.isBlank(sql)) {
            sql = "select * from " + tableName + " where \"" + idField + "\" = @" + idField;
            selectSqlCache.put(cacheKey, sql);
        }
        System.out.println(sql);
        return sql;
    }*/

    public static String createSelectByIdSql(String tableName, String idField) throws Exception {
        List<String> fields = new ArrayList<String>(Collections.singletonList(idField));
        return createSelectByFields(tableName, fields);
    }

    public static String createSelectByFields(String tableName, List fields) throws Exception {
        List<String> tableFields = getTableFields(tableName);
        List<String> valuableFields = getValuableFields(tableFields, fields);
        if (valuableFields.size() == 0) {
            throw new Exception("插入字段列表为空");
        }
        String cacheKey = genCacheKey(tableName, CollectionUtils.isEmpty(fields) ? null : valuableFields);
        String sql = selectSqlCache.get(cacheKey);
        if (StringUtils.isBlank(sql)) {
            sql = "select * from " + tableName;
            if (CollectionUtils.isNotEmpty(fields)) {
                sql += " where";
                for (String valuableField : valuableFields) {
                    sql += " \"" + valuableField + "\" = @" + valuableField + " and";
                }
                sql = sql.substring(0, sql.lastIndexOf(" and"));
            }
            selectSqlCache.put(cacheKey, sql);
        }
        System.out.println(sql);
        return sql;
    }

}
