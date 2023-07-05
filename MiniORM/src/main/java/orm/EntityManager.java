package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityManager <E> implements DBContext<E>{
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getIdField((Class<E>) entity.getClass());
        primaryKey.setAccessible(true);
        Object value = primaryKey.get(entity);

        if (value == null || (int) value <= 0) {
            return doInsert(entity);
        }
//        return doUpdate(entity, primaryKey);
return doInsert(entity);
    }

    private boolean doInsert(E entity) throws SQLException {
        String INSET_QUERY = "INSERT INTO %s(%s) VALUES (%s)";
        String tableName = getTableName(entity.getClass());
        String fieldNamesWithoutId = getFieldNamesWithoutId(entity.getClass());
        String fieldValuesWithoutId = getFieldValuesWithoutId(entity);
        String query = String.format(INSET_QUERY, tableName, fieldNamesWithoutId, fieldValuesWithoutId);

        PreparedStatement statement = this.connection.prepareStatement(query);
        return statement.executeUpdate() == 1;
    }

    private String getFieldValuesWithoutId(E entity) {
        Class<?> entityClass = entity.getClass();
        Field idField = getIdField((Class<E>) entityClass);
      return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.getName().equals(idField.getName()))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        Object value = f.get(entity);
                        return String.format("'%s'", value.toString());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
               .collect(Collectors.joining(", "));
    }

    private String getFieldNamesWithoutId(Class<?> entityClass) {
        Field idField = getIdField((Class<E>) entityClass);
       return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.getName().equals(idField.getName()))
               .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotation(Column.class).name())
                .collect(Collectors.joining(", "));
    }

    private String getTableName(Class<?> entityClass) {
        Entity annotation = entityClass.getAnnotation(Entity.class);
        if (annotation == null) {
            throw new UnsupportedOperationException("Entity must have Entity annotation");
        }
        return annotation.name();
    }


    private Field getIdField(Class<E> entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException ("Entity does not have primary key"));
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String SELECT_QUERY_SINGLE = "SELECT * FROM  %s %s LIMIT 1";
        String tableName = getTableName(table);
        String actualWhere = where == null ? "" : where;

        String query = String.format(SELECT_QUERY_SINGLE, tableName, actualWhere);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return createEntity(table, resultSet);
        }
        return null;
    }

    private E createEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        E entity = table.getConstructor().newInstance();

        Arrays.stream(table.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Column.class))
                .forEach(f -> {
                    try {
                        fillData(entity, f, resultSet);
                    } catch (SQLException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        return entity;
    }

    private void fillData(E entity, Field field, ResultSet resultSet) throws SQLException, IllegalAccessException {
        field.setAccessible(true);

        String fieldName = field.getAnnotation(Column.class).name();
        Object value;

        Class <?> fieldType = field.getType();
        if (fieldType == int.class || fieldType == long.class) {
            value = resultSet.getInt(fieldName);
        } else if (fieldType == LocalDate.class) {
            String stringData = resultSet.getString(fieldName);
            value = LocalDate.parse(stringData);
        } else {
            value = resultSet.getString(fieldName);
        }
        field.set(entity, value);
    }

}
