# FastFood Delivery API Documentation

## 🔗 Base URL
```
http://localhost:8080
```

---

## 📋 API 概览

| 模块 | 接口名称 | 方法 | 端点 | 描述 |
|-----|---------|------|------|------|
| 用户认证 | 用户登录 | POST | `/user/login` | 用户登录 |
| 用户认证 | 用户注册 | POST | `/user/register` | 用户注册 |
| 订单管理 | 提交订单 | POST | `/user/order/submit` | 提交订单 |

---

## 🔐 用户认证模块（User Auth）

### 1. 用户登录
**POST** `/user/login`

#### 请求说明
- **方法**: POST
- **Content-Type**: application/json

#### 请求体 (Request Body)
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

| 字段 | 类型 | 必需 | 说明 |
|-----|------|------|------|
| email | String | ✓ | 用户邮箱 |
| password | String | ✓ | 用户密码 |

#### 成功响应 (200 OK)
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "role": 0,
    "id": 1,
    "email": "user@example.com"
  }
}
```

#### 响应字段说明
| 字段 | 类型 | 说明 |
|-----|------|------|
| code | Integer | 响应码：1 = 成功，0 = 失败 |
| msg | String | 错误信息 |
| data | Object | 返回数据 |
| data.role | Integer | 用户角色：0 = 客户，1 = 商家 |
| data.id | Long | 用户ID |
| data.email | String | 用户邮箱 |

#### 错误响应 (400)
```json
{
  "code": 0,
  "msg": "Invalid email or password",
  "data": null
}
```

#### cURL 示例
```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

---

### 2. 用户注册
**POST** `/user/register`

#### 请求说明
- **方法**: POST
- **Content-Type**: application/json

#### 请求体 (Request Body)
```json
{
  "email": "newuser@example.com",
  "password": "password123",
  "code": 0
}
```

| 字段 | 类型 | 必需 | 说明 |
|-----|------|------|------|
| email | String | ✓ | 用户邮箱 |
| password | String | ✓ | 用户密码 |
| code | Integer | ✓ | 用户类型：0 = 客户，1 = 商家 |

#### 成功响应 (200 OK)
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "role": 0,
    "id": 2,
    "email": "newuser@example.com"
  }
}
```

#### 响应字段说明
| 字段 | 类型 | 说明 |
|-----|------|------|
| code | Integer | 响应码：1 = 成功，0 = 失败 |
| msg | String | 错误信息 |
| data | Object | 返回数据 |
| data.role | Integer | 用户角色：0 = 客户，1 = 商家 |
| data.id | Long | 用户ID |
| data.email | String | 用户邮箱 |

#### 错误响应 (400)
```json
{
  "code": 0,
  "msg": "Email already exists",
  "data": null
}
```

#### cURL 示例
```bash
curl -X POST http://localhost:8080/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newuser@example.com",
    "password": "password123",
    "code": 0
  }'
```

---

## 📦 订单管理模块（Orders）

### 3. 提交订单
**POST** `/user/order/submit`

#### 请求说明
- **方法**: POST
- **Content-Type**: application/json
- **认证**: 需要用户登录

#### 请求体 (Request Body)
```json
{
  "addressBookId": null,
  "address": "123 Main Street, New York, NY 10001",
  "phone": "+1234567890",
  "payMethod": 1,
  "remark": "No spicy, please",
  "orderTime": "2026-04-03 10:30:00",
  "estimatedDeliveryTime": "2026-04-03 11:00:00",
  "deliveryStatus": 1,
  "tablewareNumber": 0,
  "tablewareStatus": 1,
  "packAmount": 0,
  "amount": 35.50,
  "orderDetails": [
    {
      "name": "Chicken Rice Bowl",
      "quantity": 2,
      "price": 9.99
    },
    {
      "name": "Fresh Lemon Tea",
      "quantity": 1,
      "price": 3.52
    }
  ]
}
```

| 字段 | 类型 | 必需 | 说明 |
|-----|------|------|------|
| addressBookId | Long | ✗ | 地址簿ID（可选） |
| address | String | ✓ | 配送地址 |
| phone | String | ✓ | 联系电话 |
| payMethod | Integer | ✓ | 支付方式：1=信用卡，2=现金，3=钱包 |
| remark | String | ✗ | 订单备注 |
| orderTime | LocalDateTime | ✓ | 订单时间（格式：yyyy-MM-dd HH:mm:ss） |
| estimatedDeliveryTime | LocalDateTime | ✓ | 预计配送时间（格式：yyyy-MM-dd HH:mm:ss） |
| deliveryStatus | Integer | ✓ | 配送状态：1=立即配送，0=指定时间 |
| tablewareNumber | Integer | ✓ | 餐具数量 |
| tablewareStatus | Integer | ✓ | 餐具状态：1=按餐数提供，0=指定数量 |
| packAmount | Integer | ✓ | 包装费 |
| amount | BigDecimal | ✓ | 订单总金额 |
| orderDetails | List<OrderDetail> | ✓ | 订单菜品列表 |
| orderDetails[].name | String | ✓ | 菜品名称 |
| orderDetails[].quantity | Integer | ✓ | 菜品数量 |
| orderDetails[].price | BigDecimal | ✓ | 菜品单价 |

#### 成功响应 (200 OK)
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 1,
    "orderNumber": "1712139000000",
    "money": 35.50,
    "orderTime": "2026-04-03 10:30:00"
  }
}
```

#### 响应字段说明
| 字段 | 类型 | 说明 |
|-----|------|------|
| code | Integer | 响应码：1 = 成功，0 = 失败 |
| msg | String | 错误信息 |
| data | Object | 返回数据 |
| data.id | Long | 订单ID |
| data.orderNumber | String | 订单号 |
| data.money | BigDecimal | 订单金额 |
| data.orderTime | LocalDateTime | 订单时间 |

#### 错误响应 (400)
```json
{
  "code": 0,
  "msg": "Order submission failed - invalid address",
  "data": null
}
```

#### cURL 示例
```bash
curl -X POST http://localhost:8080/user/order/submit \
  -H "Content-Type: application/json" \
  -d '{
    "address": "123 Main Street, New York",
    "phone": "+1234567890",
    "payMethod": 1,
    "remark": "No spicy",
    "orderTime": "2026-04-03 10:30:00",
    "estimatedDeliveryTime": "2026-04-03 11:00:00",
    "deliveryStatus": 1,
    "tablewareNumber": 0,
    "tablewareStatus": 1,
    "packAmount": 0,
    "amount": 35.50,
    "orderDetails": [
      {
        "name": "Chicken Rice Bowl",
        "quantity": 2,
        "price": 9.99
      }
    ]
  }'
```

---

## 📊 支付方式代码对照表

| 代码 | 名称 | 说明 |
|-----|------|------|
| 1 | Credit Card | 信用卡 |
| 2 | Cash | 现金 |
| 3 | Wallet | 钱包 |

---

## 👤 用户角色代码对照表

| 代码 | 角色 | 说明 |
|-----|------|------|
| 0 | Customer | 普通客户 |
| 1 | Merchant | 商家 |

---

## ⏱️ 时间格式

所有时间字段使用以下格式：
```
yyyy-MM-dd HH:mm:ss
例如：2026-04-03 10:30:00
```

---

## 🔄 通用响应格式

所有 API 响应都遵循以下格式：

```json
{
  "code": 1,
  "msg": "Optional message",
  "data": {}
}
```

| 字段 | 类型 | 说明 |
|-----|------|------|
| code | Integer | 响应码：1 = 成功，0 = 失败 |
| msg | String | 消息（可选） |
| data | Object/Array | 返回数据（可选） |

---

## 🧪 测试用例

### 测试用户账号

#### 用户账号
```
邮箱: user@test.com
密码: 123456
```

#### 商家账号
```
邮箱: merchant@test.com
密码: 123456
```

### 示例订单数据
```json
{
  "address": "123 Main Street, New York, NY 10001",
  "phone": "+1-555-0123",
  "payMethod": 1,
  "remark": "Please ring the doorbell twice",
  "orderTime": "2026-04-03 10:30:00",
  "estimatedDeliveryTime": "2026-04-03 11:00:00",
  "deliveryStatus": 1,
  "tablewareNumber": 3,
  "tablewareStatus": 1,
  "packAmount": 0,
  "amount": 35.50,
  "orderDetails": [
    {
      "name": "Chicken Rice Bowl",
      "quantity": 2,
      "price": 9.99
    },
    {
      "name": "Fresh Lemon Tea",
      "quantity": 3,
      "price": 3.52
    }
  ]
}
```

---

## 📝 HTTP 状态码

| 状态码 | 说明 |
|--------|------|
| 200 | 请求成功 |
| 400 | 请求错误（验证失败） |
| 401 | 未授权（需要登录） |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

---

## 🔒 安全建议

1. 所有密码应该通过 HTTPS 传输
2. 密码建议进行哈希加密存储
3. 实现速率限制防止暴力攻击
4. 对敏感操作添加额外验证（如两步验证）
5. 定期审计日志

---

## 📞 联系方式

如有任何问题或建议，请联系：
- 项目管理员：admin@example.com
- 技术支持：support@example.com

---

**最后更新**: 2026年4月3日
**API 版本**: v1.0
