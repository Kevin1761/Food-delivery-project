# 订单流程完整集成指南

## 🎯 系统概述

本文档说明用户从浏览菜品到订单完成的整个流程集成。涉及以下文件的协调：

| 文件 | 用途 | 功能 |
|------|------|------|
| products.html | 菜品展示 | 浏览产品、加入购物车、钱包充值 |
| checkout.html | 订单确认 | 填写收货信息、选择支付方式、提交订单 |
| order-success.html | 订单完成 | ✨ **新增** - 展示订单成功信息 |
| payment.html | 支付（可选） | 处理支付流程（目前尚未与成功页面整合） |

---

## 📊 完整订单流程图

```
┌─────────────────────────────────────────────────────────────┐
│                     FastFood Delivery                         │
│                      订单完整流程                              │
└─────────────────────────────────────────────────────────────┘

┌──────────────┐
│products.html │  - 浏览菜品
└──────┬───────┘  - 搜索/筛选
       │         - 加入购物车
       │         - 充值钱包
       │
       ↓
┌──────────────┐
│checkout.html │  - 查看购物车内容
└──────┬───────┘  - 填写收货地址
       │         - 填写联系电话
       │         - 选择支付方式
       │         - 添加备注
       │
       ↓
   [提交订单]
       │
       ├─→ POST /user/order/submit
       │   发送: OrdersSubmitDTO
       │   {
       │     address: "...",
       │     phone: "...",
       │     payMethod: 1/2/3,
       │     amount: 45.98,
       │     orderDetails: [...]
       │   }
       │
       ↓
   [后端处理]
       │
       ├─→ 创建订单 order 记录
       ├─→ 保存订单详情 order_details 记录
       ├─→ 返回 OrderSubmitVO
       │   {
       │     id: 123,
       │     orderNumber: "2026040310302123",
       │     money: 45.98,
       │     orderTime: "2026-04-03T10:30:21"
       │   }
       │
       ↓
┌───────────────────┐
│order-success.html │ ✨ 新增页面
└───────┬───────────┘  - 显示订单号
        │             - 显示金额
        │             - 显示下单时间
        │             - 显示菜品内容
        │             - 预计送达时间
        │
        ├──→ 返回首页 (products.html)
        ├──→ 继续购物 (products.html)
        └──→ 查看订单 (待实现)
```

---

## 🔄 数据流转过程

### 1. 购物车数据存储 (products.html)

```javascript
localStorage.setItem('checkoutCart', JSON.stringify({
  items: [
    {
      id: 1,
      name: "宫保鸡丁",
      price: 19.99,
      unit: "per bowl",
      quantity: 2
    },
    {
      id: 3,
      name: "可乐",
      price: 3.50,
      unit: "per cup",
      quantity: 1
    }
  ],
  totalPrice: 43.48,
  totalItems: 3
}));
```

### 2. 结账信息收集 (checkout.html)

```javascript
const checkoutCart = JSON.parse(localStorage.getItem('checkoutCart'));
const address = "123 Main Street, Apt 4B";
const phone = "+1234567890";
const payMethod = "card"; // "card" | "cash" | "wallet"
const note = "No spicy, please!";
```

### 3. 构建订单 DTO (checkout.html)

```javascript
const orderDto = {
  address: "123 Main Street, Apt 4B",
  phone: "+1234567890",
  payMethod: 1,  // 1=Card, 2=Cash, 3=Wallet
  remark: "No spicy, please!",
  orderTime: "2026-04-03 10:30:21",
  estimatedDeliveryTime: "2026-04-03 10:50:00",
  amount: 43.48,
  orderDetails: [
    {
      name: "宫保鸡丁",
      quantity: 2,
      price: 19.99
    },
    {
      name: "可乐",
      quantity: 1,
      price: 3.50
    }
  ]
};

// 发送到后端
const resp = await fetch('/user/order/submit', {
  method: 'POST',
  headers: {'Content-Type': 'application/json'},
  body: JSON.stringify(orderDto)
});
```

### 4. 后端处理 (Java)

```java
// OrderController.submit()
@PostMapping("/submit")
public Result<OrderSubmitVO> submitOrder(@RequestBody OrdersSubmitDTO dto) {
    // OrderServiceImpl 处理
    OrderSubmitVO vo = orderService.submit(dto);
    // 返回: { code: 1, data: {id, orderNumber, money, orderTime} }
    return Result.success(vo);
}
```

### 5. 前端接收响应 (checkout.html)

```javascript
const data = await resp.json();
// data.data 包含:
// {
//   "id": 123,
//   "orderNumber": "2026040310302123",
//   "money": 43.48,
//   "orderTime": "2026-04-03T10:30:21"
// }
```

### 6. 重定向到成功页面 (checkout.html)

```javascript
// 构建 URL 参数
const urlParams = new URLSearchParams();
urlParams.append('id', orderInfo.id);
urlParams.append('orderNumber', orderInfo.orderNumber);
urlParams.append('money', orderInfo.money);
urlParams.append('orderTime', orderInfo.orderTime);
urlParams.append('items', encodeURIComponent(JSON.stringify(items)));

// 跳转
window.location.href = 'order-success.html?' + urlParams.toString();
```

### 7. 成功页面呈现 (order-success.html)

```javascript
// 解析 URL 参数
const params = new URLSearchParams(window.location.search);
const orderNumber = params.get('orderNumber');  // "2026040310302123"
const money = params.get('money');              // "43.48"
const orderTime = params.get('orderTime');      // "2026-04-03T10:30:21"
const items = JSON.parse(params.get('items'));  // [...]

// 显示在页面上
document.getElementById('orderNumber').textContent = orderNumber;
document.getElementById('orderAmount').textContent = "¥" + money;
// ... 等等
```

---

## 📦 关键数据模型

### OrdersSubmitDTO (前端发送)

```java
class OrdersSubmitDTO {
  String address;           // 收货地址
  String phone;            // 联系电话
  int payMethod;           // 支付方式: 1=卡, 2=现金, 3=钱包
  String remark;           // 备注
  String orderTime;        // 下单时间
  String estimatedDeliveryTime;  // 预计送达时间
  int deliveryStatus;      // 配送状态: 1=待派送
  int tablewareNumber;     // 餐具数量: 0
  int tablewareStatus;     // 餐具状态: 1
  int packAmount;          // 包装费: 0
  BigDecimal amount;       // 订单金额
  List<OrderDetail> orderDetails;  // 订单菜品明细
}

class OrderDetail {
  String name;        // 菜品名称
  int quantity;       // 数量
  BigDecimal price;   // 单价
}
```

### OrderSubmitVO (后端响应)

```java
class OrderSubmitVO {
  long id;                    // 订单ID
  String orderNumber;         // 订单号（13位数字）
  BigDecimal money;           // 订单总金额
  LocalDateTime orderTime;    // 下单时间
}
```

---

## 🛠️ 实现检查清单

### 后端实现 ✅
- [x] OrderController.submit() 接收 OrdersSubmitDTO
- [x] OrderServiceImpl.submit() 保存订单到数据库
- [x] 返回 OrderSubmitVO 包含订单信息
- [ ] OrderServiceImpl 保存 order_details 表（待优化）
- [ ] 订单号生成规则（当前使用时间戳 + 随机数）

### 前端实现 ✅
- [x] products.html 购物车功能
- [x] checkout.html 订单提交逻辑
- [x] order-success.html 成功页面 ✨ 新增
- [x] URL 参数传递订单信息
- [x] 订单号复制功能
- [x] 时间格式化显示
- [x] 响应式布局

### 数据库实现 ✅
- [x] orders 表（已存在）
- [x] order_details 表（待创建 - 在 takeout.sql 中）
- [x] category 表（已添加）
- [x] dish 表（已添加）

---

## 🚀 快速启动步骤

### 1. 数据库初始化

```bash
# 运行 SQL 脚本
mysql -u root -p < db/takeout.sql

# 或在 MySQL 客户端中：
source /path/to/takeout.sql;
```

### 2. 启动后端应用

```bash
mvn clean install
mvn spring-boot:run
```

### 3. 访问应用

```
http://localhost:8080/resources/static/products.html
```

### 4. 完整订单流程测试

1. **访问 products.html**
   - 浏览菜品列表
   - 搜索或筛选菜品
   - 加入购物车

2. **进入 checkout.html**
   - 点击"Checkout"按钮
   - 或直接访问 `checkout.html`

3. **填写订单信息**
   - 收货地址：`123 Main Street`
   - 联系电话：`+1234567890`
   - 支付方式：选择任意一种
   - 备注：`(可选)`

4. **提交订单**
   - 点击"Place order"按钮
   - 看到成功提示

5. **查看 order-success.html**
   - 显示订单号、金额、时间
   - 显示购买的菜品列表
   - 预计配送时间

---

## 🐛 故障排除

### 问题 1: 订单提交失败，显示 "Network error"

**原因**: 后端 API 未启动或地址错误
**解决**:
```javascript
// 检查 checkout.html 中的 API 地址
fetch('http://localhost:8080/user/order/submit', {...})
// 确保后端运行在 8080 端口
```

### 问题 2: order-success.html 无法显示订单信息

**原因**: URL 参数缺失或格式错误
**解决**:
```javascript
// 在浏览器控制台检查
console.log(new URLSearchParams(window.location.search));
// 确保包含: id, orderNumber, money, orderTime
```

### 问题 3: 订单号显示为 "-"

**原因**: OrderSubmitVO.orderNumber 为 null
**解决**: 检查后端 OrderServiceImpl.submit() 的订单号生成逻辑

### 问题 4: 金额显示错误

**原因**: 数据类型转换问题或精度丢失
**解决**:
```javascript
// 确保金额保留 2 位小数
const amount = parseFloat(params.money).toFixed(2);
document.getElementById('orderAmount').textContent = `¥${amount}`;
```

---

## 📱 前端页面交互流程

```
products.html
    ↓ [使用场景]
    ├─ 用户登陆且第一次访问
    ├─ 用户从其他页面返回
    └─ 用户继续购物
    
    ↓ [可交互元素]
    ├─ 菜品卡片
    │  ├─ 菜品名称、描述、价格
    │  ├─ 点击"Add to cart"按钮
    │  └─ localStorage 保存到 checkoutCart
    │
    ├─ 购物车侧栏
    │  ├─ 显示购物车内容
    │  ├─ 修改数量（+/-）
    │  ├─ "Clear" 按钮清空购物车
    │  └─ "Checkout" 按钮进入 checkout.html
    │
    ├─ 钱包功能
    │  ├─ 显示当前余额
    │  ├─ "Recharge" 按钮打开充值弹窗
    │  ├─ 输入卡号、金额、CVV
    │  └─ localStorage 保存余额
    │
    └─ 用户信息
       ├─ 显示登陆邮箱
       └─ "Log out" 按钮返回登陆页

    ↓

checkout.html
    ↓ [使用场景]
    ├─ 从 products.html 的 "Checkout" 跳转进入
    └─ localStorage 读取 checkoutCart
    
    ↓ [可交互元素]
    ├─ 订单摘要卡片
    │  ├─ 显示购物车内容
    │  ├─ 显示小计、配送费、总价
    │  └─ 从 checkoutCart 计算合计
    │
    ├─ 配送信息表单
    │  ├─ 收货地址 (textarea)
    │  ├─ 联系电话 (input)
    │  ├─ 支付方式 (select)
    │  │  ├─ Credit/Debit Card (1)
    │  │  ├─ Cash on delivery (2)
    │  │  └─ Wallet balance (3)
    │  └─ 订单备注 (textarea, 可选)
    │
    ├─ "Place order" 按钮
    │  └─ 验证表单 → 构建 orderDto → 提交
    │
    ├─ "Back to products" 按钮
    │  └─ 返回 products.html
    │
    └─ 状态显示区域
       ├─ "Submitting order..." (处理中)
       ├─ "Order placed successfully!" (成功)
       └─ "Failed to place order" (失败)

    ↓ [成功后]
    └─ 清除 localStorage.checkoutCart
    └─ 重定向到 order-success.html?id=...&orderNumber=...

    ↓

order-success.html ✨ 新增
    ↓ [使用场景]
    └─ 从 checkout.html 的成功回调跳转进入
    
    ↓ [显示内容]
    ├─ 成功图标 + 动画
    ├─ 标题："订单已成功下单"
    ├─ 副标题："商家正在为您准备"
    │
    ├─ 订单信息卡片
    │  ├─ 订单号（可复制到剪贴板）
    │  ├─ 订单金额（大字体）
    │  └─ 下单时间（本地化格式）
    │
    ├─ 订单内容列表（如有 items 参数）
    │  └─ 菜品名 × 数量 = 小计
    │
    ├─ 配送信息
    │  ├─ ✓ 订单已确认
    │  ├─ 预计 [25-45] 分钟内送达
    │  └─ 订单状态变化时通知
    │
    ├─ 操作按钮
    │  ├─ 返回首页 → products.html
    │  ├─ 查看订单 → (待实现)
    │  └─ 继续购物 → products.html
    │
    └─ 温馨提示
       ├─ 保持手机畅通
       ├─ 订单号可追踪
       └─ 遇问题可联系
```

---

## 🔧 配置要求

### application.properties

```properties
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/takeout
spring.datasource.username=root
spring.datasource.password=123456

# MyBatis 配置
mybatis.mapper-locations=classpath*:org/example/springboot_demo/server/mapper/*.xml
```

### 前端地址编程

```javascript
// checkout.html 中的 API 地址
const API_BASE = 'http://localhost:8080';
const SUBMIT_ORDER_API = `${API_BASE}/user/order/submit`;
```

---

## 📈 性能优化建议

### 短期
- [ ] 减少 URL 参数，使用 sessionStorage 传递的购物车数据
- [ ] 添加加载动画减小用户等待感
- [ ] 订单号支持一键复制（已实现✅）

### 中期
- [ ] 实现真实的 WebSocket 订单推送
- [ ] 订单历史查询 API
- [ ] 支付状态轮询

### 长期
- [ ] 订单统计分析
- [ ] 推荐引擎集成
- [ ] PWA 离线支持

---

## 📞 相关文件

- **后端**: [OrderController](src/main/java/org/example/springboot_demo/Controller/orderController.java)
- **服务**: [OrderServiceImpl](src/main/java/org/example/springboot_demo/server/service/OrderServiceImpl.java)
- **VO**: [OrderSubmitVO](src/main/java/org/example/springboot_demo/POJO/OrderSubmitVO.java)  
- **DTO**: [OrdersSubmitDTO](src/main/java/org/example/springboot_demo/POJO/OrdersSubmitDTO.java)
- **前端**: [order-success.html](src/main/resources/static/order-success.html) ✨ 新增
- **数据库**: [takeout.sql](db/takeout.sql)
- **文档**: [ORDER_SUCCESS_GUIDE.md](ORDER_SUCCESS_GUIDE.md) ✨ 新增

