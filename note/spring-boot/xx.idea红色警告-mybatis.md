使用自动注入mybatis的mapper对象的时候，idea会报异常

- idea认为该对象是null，因此给个警告

解决

- 方式1，使用required=false，不强制要求

```java
@Autowired(required=false)
```

- 方式2，使用@Resource替换@Autowired

- 方式3，在mapper接口上添加@Repository注解

- 方式4，使用lombok，在调用该mapper的service实现类上添加@RequiredAgsConstructor注解

```java
@RequiredAgsConstructor(onConstructor = @_(@Autowried))

// 对调用的mapper类使用final修饰
private final UserMapper userMapper;
```

- spring官方并不建议直接在类的field上使用@Autowired注解