

### xml
1️⃣ XML 是什么

XML（eXtensible Markup Language，可扩展标记语言）
•	一种用于存储和传输数据的文本格式。
•	可读性好（对人友好）、可扩展（可以自定义标签）。
•	类似 HTML，但 HTML 是为了展示，而 XML 是为了描述数据。

示例：
```xml
<person>
    <name>Monica</name>
    <age>25</age>
    <email>monica@example.com</email>
</person>
```
	•	<person> 是根元素
	•	<name>, <age>, <email> 是子元素
	•	可以自由定义标签名

⸻

2️⃣ XML 的特点
1.	自描述性
      •	数据和结构分开，标签描述了数据的含义。
2.	层次化结构
      •	支持树形嵌套，父子关系清晰。
3.	可扩展
      •	可以根据需要自定义标签，没有固定语义限制。
4.	平台独立
      •	文本格式，任何语言和平台都能解析。
5.	严格规范
      •	每个标签必须闭合、区分大小写、唯一根元素等。



### 实现一个原始版本的IOC容器
对于现在要着手实现的原始版本Bean，我们先只管理两个属性：id与class。
其中，class表示要注入的类，而id则是给这个要注入的类一个别名，它可以简化记忆的成本。

**我们要做的是把Bean通过XML的方式注入到框架中，你可以看一下XML的配置。**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans>
    <bean id = "xxxid" class = "com.minis.xxxclass"></bean>
</beans>
```

解析xml工具类：dom4j



纯 Java 项目里 src/main/java 和 src/test/java 的结构，顺便讲清楚资源、编译后目录和 classpath 的关系。

⸻

一、Maven / Gradle 典型项目结构

project-root/
├── src/
│   ├── main/
│   │   ├── java/            # 主代码
│   │   │   └── com/example/YourClass.java
│   │   └── resources/       # 主资源文件（XML, properties）
│   │       └── config.xml
│   └── test/
│       ├── java/            # 测试代码
│       │   └── com/example/YourClassTest.java
│       └── resources/       # 测试资源（测试专用 XML, properties）
│           └── test-config.xml
├── pom.xml / build.gradle
└── target/                  # 编译输出目录


⸻

二、编译后目录

1️⃣ src/main/java → target/classes

src/main/java/com/example/YourClass.java  --->  target/classes/com/example/YourClass.class
src/main/resources/config.xml             --->  target/classes/config.xml

2️⃣ src/test/java → target/test-classes

src/test/java/com/example/YourClassTest.java  --->  target/test-classes/com/example/YourClassTest.class
src/test/resources/test-config.xml           --->  target/test-classes/test-config.xml


⸻

三、Classpath 规则
•	运行主程序 (main 方法)：classpath = target/classes + 依赖的 jar
•	运行测试 (JUnit test)：classpath = target/test-classes + target/classes + 依赖 jar

所以测试代码可以访问 main 的类和资源，但 main 不能访问 test 的类和资源。

⸻

四、资源访问规则

1️⃣ ClassLoader
•	getClassLoader().getResource("file") → 从 classpath 根查找
•	例子：

// main 资源
URL url = YourClass.class.getClassLoader().getResource("config.xml");

// test 资源
URL url2 = YourClassTest.class.getClassLoader().getResource("test-config.xml");

	•	不要加 classpath: 前缀
	•	ClassLoader 只能从 classpath 根开始查找，路径相对根目录

2️⃣ Class.getResource
•	getClass().getResource("/file") → 斜杠表示从 classpath 根开始
•	getClass().getResource("file") → 相对当前类所在包

⸻

五、访问规则总结

场景	路径写法	ClassLoader	Class
主资源	config.xml	✅	getClass().getResource("/config.xml") ✅
测试资源	test-config.xml	✅	getClass().getResource("/test-config.xml") ✅
相对包路径	com/example/data.txt	✅	getClass().getResource("data.txt")


⸻

六、关键点
1.	test 包可以访问 main 包的类和资源
2.	main 包不能访问 test 包的类和资源
3.	资源必须在 resources 下，才会被拷贝到 classpath
4.	编译后的路径分开
      •	main → target/classes
      •	test → target/test-classes
5.	JUnit 测试运行时，classpath 会把 test-classes 放在前面，保证测试覆盖






### 循环依赖问题
在某个Bean需要注入另一个Bean的时候，如果那个Bean还不存在，该怎么办？
场景，Spring扫描到了ABean，在解析它并设置内部属性时，
发现某个属性是另一个BBean，
而此时Spring内部还不存在BBean的实例。
这就要求Spring在创建ABean的过程中，能够再去创建一个BBean，
继续推衍下去，BBean可能又会依赖第三个CBean。事情还可能进一步复杂化，如果CBean又反过来依赖ABean，就会形成循环依赖。

