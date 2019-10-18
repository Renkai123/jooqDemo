### JOOQ类生成（Code generation from entities）

#### 背景

    由于querydsl近两年没有更新，使用JOOQ去做动态sql查询。JOOQ 是基于Java访问关系型数据库的工具包，轻量，简单，安全并且足够灵活，可以轻松的使用Java面向对象语法来实现各种复杂的sql。和querydsl一样JOOQ也需要生成一堆文件。
    生成JOOQ类有通过连接数据库，flyway，entity等方式。由于企业级应用无法写死数据库地址账号密码在pom文件中，flyway过于死板不利于更新（flyway文件变更需要更改数据中checksum，并且需要和数据库严格一致），所以通过entity去生成JOOQ类。

但是通过[官方文档](https://www.jooq.org/doc/latest/manual/code-generation/codegen-jpa/) 生成有以下缺点：
    
    1. 需要在entity上指定表名，通过 @Table(name = "author")，如不指定表名为大写
    2. 需要在字段上指定字段名，通过@Column(name = "last_name")，如不指定生成的字段不仅是大写，而且不是下滑线（underline）形式
    3. 不能使用@Column(columnDefinition = "")
    4. 库名需要指定

总之与我jpa生成的table不符合，所以需要改写插件和代码生成。

#### JPADatabase
JOOQ通过H2 Database内嵌数据库生成JOOQ类。H2 Database生成的数据库字段名默认全部变为大写，所以造就上述缺点，生成出来的sql也都默认大写。需要在连接h2的时候 database_to_upper设置为false
```
connection = new org.h2.Driver().connect("jdbc:h2:mem:jooq-meta-extensions-" + UUID.randomUUID() + ";MODE=MySQL;database_to_upper=false", info);
```

新增使用SpringPhysicalNamingStrategy的策略
```
applySetting(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy")
```

#### pom包导入
```
<!-- https://mvnrepository.com/artifact/org.jooq/jooq-codegen -->
<dependency>
    <groupId>org.jooq</groupId>
    <artifactId>jooq-meta-extensions</artifactId>
    <version>3.11.11</version>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jooq</artifactId>
</dependency>
```

#### maven插件
```
            <!--预编译entity文件及conde-generate代码-->
           <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <executions>
                    <execution>
                        <id>pre-compile</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <includes>
                                <include>com/example/creams/demo/entity/*</include>
                                <include>org/jooq/meta/extensions/**</include>
                            </includes>
                        </configuration>
                    </execution>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.jooq</groupId>
                <artifactId>jooq-codegen-maven</artifactId>
                <version>3.11.11</version>
                <executions>
                    <execution>
                        <id>jooq-codegen</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <generator>
                        <database>
                             <!--指定改写的生成类文件-->
                            <name>org.jooq.meta.extensions.jpa.JPADatabaseNew</name>
                            <inputSchema>PUBLIC</inputSchema>
                            <!--指定数据库的schema名-->
                            <outputSchema>library</outputSchema>
                            <properties>
                                <!-- A comma separated list of Java packages, that contain your entities -->
                            <!--指定entity包名-->
                                <property>
                                    <key>packages</key>
                                    <value>com.example.creams.demo.entity</value>
                                </property>

                            </properties>
                        </database>
                        <target>
                            <!--生成地址-->
                            <packageName>com.example.creams.demo.jooq</packageName>
                        </target>
                    </generator>
                </configuration>
            </plugin>

```

设置完上述配置之后，maven compile 就可以在target的generated-sources文件夹中看到JOOQ文件

#### 总结
JOOQ通过H2 Database内嵌数据库生成JOOQ类，所以H2生成的数据库必须和JPA自动生成的数据库相符合。在生成数据库的时候使用jpa.hibernate.SpringPhysicalNamingStrategy的策略保持一致。





