package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.db.DataBase;
import com.liushimin.miniUI.enumeration.IdType;
import com.liushimin.miniUI.pojo.Employee;
import com.liushimin.miniUI.util.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.*;

/**
 * 方法中带有conn的方法是用来开启事务用的
 * 需要在手动关闭数据库连接
 * Connection conn = DataBase.getConnection();
 * conn.setAutoCommit(false);
 * new FileDao().insert(param, conn);
 * new EmployeeDao().update(param, conn);
 * conn.submit();
 * DataBase.close(conn);
 *
 */
public abstract class BaseDao<T> {

    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract String getTableName();

    /**
     * 主键类型为UUID或者是自增键，需要重写
     * 自增主键与UUID如果有值，则取此值
     * @return
     */
    protected String getIdType() {
        return IdType.COMMON.getCode();
    }

    /**
     * 主键不为id，则需要重写
     * @return
     */
    protected String getIdField() {
        return "id";
    }

    /**
     * 如果主键是AUTO，则需要重写
     * id是自增长类型的才有sequence
     * @return
     */
    protected String getSeqName() {
        return "";
    }

    protected String autoId(Map o, String idField) {
        String id = o.get(idField) == null ? null : o.get(idField).toString();
        if ("".equals(id)) {
            id = null;
        }
        if (id == null && IdType.UUID.getCode().equals(getIdType())) {
            id = UUID.randomUUID().toString();
        }
        o.put(idField, id);
        return id;
    }

    /**
     * 执行增删改
     * 无事务
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    public int execute(String sql, Map<String, Object> param) throws Exception {
        return DataBase.execute(sql, param, null);
    }

    /**
     * 执行增删改
     * @param sql
     * @param param
     * @param conn
     * @return
     * @throws Exception
     */
    public int execute(String sql, Map<String, Object> param, Connection conn) throws Exception {
        return DataBase.execute(sql, param, conn);
    }

    /**
     * 执行查询语句
     * 无事务
     * @param sql
     * @param param
     * @return
     * @throws Exception
     */
    public List<T> select(String sql, Map<String, Object> param) throws Exception {
        return select(sql, param, null);
    }

    /**
     * 执行查询语句
     * @param sql
     * @param param
     * @param conn
     * @return
     * @throws Exception
     */
    public List<T> select(String sql, Map<String, Object> param, Connection conn) throws Exception {
        return ConvertUtils.convertToBeanList(getTClass(), DataBase.select(sql, param, conn));
    }

    /**
     * 全字段插入
     * 自增主键和UUID的表可以有也可以没有id，
     * 普通主键的表必须要有id
     * ##无事务
     * 返回插入主键
     * @param param
     * @return
     * @throws Exception
     */
    public String insert(Map param) throws Exception {
        return insert(param, null);
    }

    /**
     * 全字段插入记录
     * 自增主键和UUID的表可以有也可以没有id，
     * 普通主键的表必须要有id
     * ##需要开启事务时需要传入conn,并且需要手动关闭连接
     * 返回插入主键
     * @param param
     * @param conn
     * @return
     * @throws Exception
     */
    public String insert(Map param, Connection conn) throws Exception {
        boolean toClose = false;
        if (conn == null) {
            toClose = true;
            conn = DataBase.getConnection();
        }
        System.out.println("param:" + param);
        String id = autoId(param, getIdField());
        param.put(getIdField(), id);
        int execute = DataBase.execute(getInsertSql(param), param, conn);
        System.out.println("成功插入" + execute + "条记录");
        if (id == null) {
            id = DataBase.selectScalar("select " + getSeqName() + ".currval from dual", null, conn).toString();
        }
        System.out.println("插入记录id为：" + id);
        if (toClose) {
            DataBase.close(conn);
        }
        return id;
    }

    public void insertList(List<T> list) throws Exception {
        insertList(list, null);
    }

    public void insertList(List<T> list, Connection conn) throws Exception {
        boolean toClose = false;
        if (conn == null) {
            toClose = true;
            conn = DataBase.getConnection();
        }
        for (T o : list) {
            insert(ConvertUtils.convertToMapNoNullValue(o, true), conn);
        }
        if (toClose) {
            DataBase.close(conn);
        }
    }

    /**
     * 获取全量插入sql
     * @return
     * @throws Exception
     */
    protected String getInsertSql() throws Exception {
        return DataBase.createInsertSql(getTableName(), null);
    }

    /**
     * 获取全量插入sql
     * @return
     * @throws Exception
     */
    protected String getInsertSql(Map param) throws Exception {
        List<String> fields = new ArrayList<String>();
        for (Object o : param.keySet()) {
            fields.add(o.toString());
        }
        return DataBase.createInsertSql(getTableName(), fields);
    }

    /**
     * 根据id删除
     * ##无事务
     * @param id
     * @return
     */
    public int delete(String id) throws Exception {
        return delete(id, null);
    }

    /**
     * 根据id删除
     * ##需要开启事务时需要传入conn,并且需要手动关闭连接
     * 返回删除条数
     * @param id
     * @param conn
     * @return
     */
    public int delete(String id, Connection conn) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new Exception("删除id为空");
        }
        System.out.println("删除数据id为：" + id);
        Map param = new HashMap();
        param.put(getIdField(), id);
        int cnt = DataBase.execute(getDeleteSql(), param, conn);
        System.out.println("删除条数为：" + cnt);
        return cnt;
    }

    protected String getDeleteSql() {
        return DataBase.createDeleteSql(getTableName(), getIdField());
    }

    /**
     * 根据id修改,param中的id字段不能为空
     * 将param中传入字段进行修改
     * ##无事务
     * @param param
     * @return
     */
    public int update(Map param) throws Exception {
        return update(param, null);
    }

    /**
     * 根据id修改,param中的id字段不能为空
     * 将param中传入字段进行修改
     * ##需要开启事务时需要传入conn,并且需要手动关闭连接
     * 返回修改条数
     * @param param
     * @param conn
     * @return
     */
    public int update(Map param, Connection conn) throws Exception {
        String  id = param.get(getIdField()).toString();
        System.out.println("更新数据id为：" + id);
        int cnt = DataBase.execute(getUpdateSql(param), param, conn);
        System.out.println("更新条数为：" + cnt);
        return cnt;
    }

    public void updateList(List<T> updateList, Connection conn) throws Exception {
        boolean toClose = false;
        if (conn == null) {
            toClose = true;
            conn = DataBase.getConnection();
        }
        for (T o : updateList) {
            update(ConvertUtils.convertToMapNoNullValue(o, false), conn);
        }
        if (toClose) {
            DataBase.close(conn);
        }
    }

    public void updateList(List<T> updateList) throws Exception {
        updateList(updateList, null);
    }

    protected String getUpdateSql() throws Exception {
        return DataBase.createUpdateSql(getTableName(), getIdField(),null);
    }

    protected String getUpdateSql(Map param) throws Exception {
        List<String> fields = new ArrayList<String>();
        for (Object o : param.keySet()) {
            fields.add(o.toString());
        }
        return DataBase.createUpdateSql(getTableName(), getIdField(),fields);
    }

    public T findByid(String id) throws Exception {
        return findByid(id, null);
    }

    public T findByid(String id, Connection conn) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new Exception("id为空");
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put(getIdField(), id);
        T result = ConvertUtils.convertToBean(getTClass(), DataBase.selectFirst(getSelectByIdSql(), param, conn));
        return result;
    }

    protected String getSelectByIdSql() throws Exception {
        return DataBase.createSelectByIdSql(getTableName(), getIdField());
    }

    /**
     * 最大每页100条记录
     * 默认为0页10条
     * 无事务
     * @param param
     * @param orderFields
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    public List<T> findSortPage(Map<String, Object> param, Map<String, Boolean> orderFields, Integer page, Integer size) throws Exception {
        return findSortPage(param, orderFields, page, size, null);
    }

    /**
     * 最大每页100条记录
     * 默认为0页10条
     * @param param
     * @param orderFields
     * @param page
     * @param size
     * @param conn
     * @return
     * @throws Exception
     */
    public List<T> findSortPage(Map<String, Object> param, Map<String, Boolean> orderFields, Integer page, Integer size, Connection conn) throws Exception {
        if (page == null ||page < 0) {
            page = 0;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        if (size == null || size > 100) {
            size = 100;
        }
        List fields = null;
        fields = paramToFields(param);
        String sql = getSelectByFields(fields);
        sql = sortedSql(sql, orderFields);
        sql = pageSql(sql, page, size);
        return select(sql, param, conn);
    }

    protected List paramToFields(Map<String, Object> param) {
        List<String> fields = null;
        if (param != null && param.size() != 0) {
            fields = new ArrayList<String>();
            for (Map.Entry<String, Object> entry: param.entrySet()) {
                fields.add(entry.getKey());
            }
        }
        return fields;
    }

    /**
     * false为降序，true为升序
     * @param sql
     * @param orderFields
     * @return
     */
    protected String sortedSql(String sql, Map<String, Boolean> orderFields) {
        if (orderFields != null && orderFields.size() != 0) {
            sql += " order by";
            for (Map.Entry<String, Boolean> entry: orderFields.entrySet()) {
                sql += " \"" + entry.getKey() + "\" " + (entry.getValue() ? "asc" : "desc") + ",";
            }
            sql = sql.substring(0, sql.lastIndexOf(","));
        }
        return sql;
    }

    /**
     * page从1开始
     * @param sql
     * @param page
     * @param size
     * @return
     */
    protected String pageSql(String sql, Integer page, Integer size) {
        sql = "SELECT * from (SELECT rownum rn, tt.* from (" + sql + ") tt WHERE rownum <= " + (page+1)*size + ") where rn > " + page*size;
        return sql;
    }

    protected String getSelectByFields(List fields) throws Exception {
        return DataBase.createSelectByFields(getTableName(), fields);
    }

    /**
     * 根据传入参数进行count
     * 有事务
     * @param param
     * @param conn
     * @return
     * @throws Exception
     */
    public int count(Map<String, Object> param, Connection conn) throws Exception {
        List fields = null;
        fields = paramToFields(param);
        String sql = getSelectByFields(fields);
        sql = "select count(1) from (" + sql + ")";
        return Integer.valueOf(DataBase.selectScalar(sql, param, conn).toString());
    }

    /**
     * 根据传入参数进行count
     * 无事务
     * @param param
     * @return
     * @throws Exception
     */
    public int count(Map<String, Object> param) throws Exception {
        List fields = null;
        fields = paramToFields(param);
        String sql = getSelectByFields(fields);
        sql = "select count(1) from (" + sql + ")";
        return Integer.valueOf(DataBase.selectScalar(sql, param, null).toString());
    }

    /**
     * count所有
     * 无事务
     * @return
     * @throws Exception
     */
    public int count() throws Exception {
        String sql = "select count(1) from " + getTableName();
        return Integer.valueOf(DataBase.selectScalar(sql, null, null).toString());
    }

    /**
     * count所有
     * 有事务
     * @param conn
     * @return
     * @throws Exception
     */
    public int count(Connection conn) throws Exception {
        String sql = "select count(1) from " + getTableName();
        return Integer.valueOf(DataBase.selectScalar(sql, null, conn).toString());
    }

}
