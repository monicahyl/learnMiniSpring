# learnMiniSpring
code about mini Spring


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


