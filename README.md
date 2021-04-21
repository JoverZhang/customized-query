# Customized Query (定制查询)

## 运行 Examples

##### 1. 进入目录 `./customized-query-example-server/deploy` 然后运行以下命令

```shell
docker-compose up
```

##### 2. 依次启动 customized-query-example-server 与 customized-query-example-client

**可使用下方链接可直接跳转到对应的启动类**

[Jump to ServerApplication](./customized-query-examples/customized-query-example-server/service-provider/src/main/java/org/customizedquery/example/server/ServerApplication.java)

[Jump to ClientApplication](./customized-query-examples/customized-query-example-client/src/main/java/org/customizedquery/example/client/ClientApplication.java)

##### 3. 运行下方测试用例

```shell
curl 'localhost:9000/example-query/example-one'

{"id":3,"name":"little_b","balance":10.00,"birthday":"1970-01-02T16:00:00.000+00:00"}
```

```shell
curl 'localhost:9000/example-query/example-list'

[{"id":1,"name":"little_a","balance":10.00,"birthday":"1969-12-31T16:00:00.000+00:00"}]
```

```shell
curl 'localhost:9000/example-query/example-page'

{"records":[{"id":3,"name":"little_b","balance":10.00,"birthday":"1970-01-02T16:00:00.000+00:00"},{"id":2,"name":"little_c","balance":20.00,"birthday":"1970-01-01T16:00:00.000+00:00"}],"total":2,"pages":1}
```

**http 请求的测试用例**

## Usage

[Jump to DemoUserController.http](./customized-query-examples/customized-query-example-client/src/test/java/org/customizedquery/example/client/controller/DemoUserController.http)

### 1. 自定义与数据库关联的实体 (DO)

[Jump to DemoUser](./customized-query-examples/customized-query-example-server/service-provider/src/main/java/org/customizedquery/example/server/domain/DemoUser.java)

```java

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemoUser extends AbstractEntity {

    /**
     * 假设为唯一索引字段, 允许等值查询
     */
    private String name;

    /**
     * 假设非索引字段, 不允许作为查询条件
     */
    private BigDecimal balance;

   /**
    * 假设为索引字段, 允许等值与区间查询
    */
   private Date birthday;

}
```

### 2. 定义与 DO 相对应的查询实体 (Query)

**三个步骤**

1. 继承抽象类 AbstractRecoverableQuery
1. 实现静态方法 queryBuilder()
1. 定制查询条件
   - 定义仅允许等值查询: `变量类型 变量名称;` 如 `String name;`
   - 定义即允许等值查询, 又允许区间查询 `Range<变量类型> 变量名称;` 如 `Range<Long> id;`
   - 定义可排序字段: `Boolean orderBy变量名` 变量名需首字母大写, 如 `Boolean orderById;`
     (当参数为 true 时正序, false 倒序)

[Jump to DemoUserQuery](./customized-query-examples/customized-query-example-server/service-api/src/main/java/org/customizedquery/example/server/domain/query/DemoUserQuery.java)

```java

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DemoUserQuery extends AbstractRecoverableQuery {

    /**
     * 定义即允许等值查询, 又允许区间查询的字段
     */
    private Range<Long> id;

   /**
    * 定义仅允许等值查询的字段
    */
    private String name;

    /**
     * 定义即允许等值查询, 又允许区间查询的字段
     */
    private Range<Date> birthday;

   /**
    * 定义仅允许等值查询的字段
    */
    private Boolean orderById;

    /**
     * 定义允许排序的字段
     */
    private Boolean orderByName;

    /**
     * 定义允许排序的字段
     */
    private Boolean orderByBirthday;

    /**
     * 代理 lombok 的注解 @Builder 自动生成的静态方法 builder()
     */
    public static DemoUserQueryBuilder queryBuilder() {
        return builderProxy(builder());
    }

}
```

### 3. 服务调用方使用查询实体 (Query) 进行远程调用

[Jump to DemoUserController](./customized-query-examples/customized-query-example-client/src/main/java/org/customizedquery/example/client/controller/DemoUserController.java)

```java
public class DemoUserController {

    /**
     * 等值匹配查询
     */
    @GetMapping("/example-one")
    public DemoUserDTO one() {
        DemoUserQuery query = DemoUserQuery.queryBuilder()
                .name("little_b")
                .id(ne(1L))
                .build();

        DemoUserDTO result = demoUserApi.one(query);

        Asserts.notNull(result);
        return result;
    }

    /**
     * 分页查询
     *
     * <p>当参数较多时, 需要注意调用顺序 (如 orderByXxx)
     */
    @GetMapping("/example-page")
    public Page<DemoUserDTO> page() {
        DemoUserQuery query = DemoUserQuery.queryBuilder()
                .id(between(1L, 3L))
                .birthday(ge(new Date(86400000)))
                .orderByBirthday(false)
                .orderByName(true)
                .build();
        PaginationQuery<DemoUserQuery> paginationQuery = PaginationQuery.query(1, 4, query);

        Page<DemoUserDTO> result = demoUserApi.page(paginationQuery);

        Asserts.isTrue(result.getTotal() == 2);
        Asserts.isTrue(result.getRecords().size() == 2);
        return result;
    }

}
```

#### **前端须知, 上方示例中的查询实体通过 Json 序列化后的格式:

[Jump to DemoUserController.http](./customized-query-examples/customized-query-example-client/src/test/java/org/customizedquery/example/client/controller/DemoUserController.http)

##### example-one ( DemoUserQuery )

```json
{
  "condition": {
    "name": "little_b",
    "id_ne": 1
  }
}
```

##### example-page ( PaginationQuery\<DemoUserQuery\> )

```json
{
  "current": 1,
  "size": 4,
  "query": {
    "condition": {
      "id_between": [
        1,
        3
      ],
      "birthday_ge": "1970-01-02"
    },
    "orderBy": {
      "birthday": false,
      "name": true
    }
  }
}
```

## 扩展工具

### 将查询实体 (Query) 直接转换为 Mybatis-Plus 的 QueryWrapper

[Jump to DemoUserRepositoryImpl](./customized-query-examples/customized-query-example-server/service-provider/src/main/java/org/customizedquery/example/server/repository/impl/DemoUserRepositoryImpl.java)

```java
public class DemoUserRepositoryImpl implements DemoUserRepository {

    public DemoUser one(DemoUserQuery query) {
        QueryWrapper<DemoUser> wrapper = MybatisPlusRecover.recover(query, DemoUser.class);
        return demoUserDao.selectOne(wrapper);
    }

}
```
