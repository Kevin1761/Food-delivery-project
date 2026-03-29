# Sptingboot_demo 项目说明

## 一、环境要求

- 操作系统：Windows 10 / 11
- JDK：17
- 构建工具：Maven 3.8+（命令行执行 `mvn -v` 能正常显示版本）
- IDE：推荐 IntelliJ IDEA / VS Code（需安装 Java 插件）
- 数据库：MySQL 8.x

## 二、数据库环境配置

1. 安装 MySQL 8.x，并保证服务已启动。
2. 图形化工具中选择导入项目中数据库文件：[db/takeout.sql](db/takeout.sql) 
3. 修改数据库连接信息，请编辑：`src/main/resources/application.properties` 中以下配置：

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/takeout?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
   spring.datasource.username=root// 换成个人数据库账号
   spring.datasource.password=123456// 换成个人数据库密码
   ```


## 三、IDE 中运行项目

### 1. 使用 IntelliJ IDEA

1. 打开 IDEA，选择 "Open"，导入本项目根目录。
2. 等待 IDEA 自动识别 Maven 项目并下载依赖。
3. 确认 Project SDK 选择为 JDK 17。
4. 在 `src/main/java/org/example/springboot_demo/SpringbootDemoApplication.java` 中找到主启动类，右键选择 "Run" 即可启动。
5. 启动成功后，默认服务端口为 8080（如需修改，可在 `src/main/resources/application.properties` 中添加/修改 `server.port=xxxx`）。

### 2. 使用 VS Code

1. 安装扩展：
   - Extension Pack for Java
   - Spring Boot Extension Pack（可选）
2. 打开本项目根目录。
3. VS Code 会自动识别为 Maven 项目并加载依赖。
4. 在 Java 资源视图中找到 `SpringbootDemoApplication` 主类，点击运行图标启动。

## 六、常见问题

- 若启动时报找不到驱动类 `com.mysql.cj.jdbc.Driver`，请确认 MySQL 依赖已正常下载且网络无代理限制。
- 若连接数据库失败，请检查：
  - MySQL 服务是否已启动；
  - 数据库名、用户名和密码是否与 `application.properties` 中配置一致；
  - 端口是否为 3306，或根据实际端口修改 `spring.datasource.url`。
- 若 Lombok 注解（如 `@Data` 等）报红，请在 IDE 中安装 Lombok 插件，并开启注解处理。
