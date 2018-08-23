# 随手拍红包项目
## 项目框架
  SpringBoot          
  Thymeleaf        
  MyBatis       
  RabbitMQ         
  Redis       
  MySql             
## 代码结构
admin--后台管理（controller，service）     
mobile--前台（controller，service）        
database--数据库操作（dao,model,query,数据配置文件也在这里）         
common--共用方法（工具类，公用配置等）          
operate-recorder--操作记录器           
prize-recorder--红包记录器和发送         
resource--所有静态文件（css，js，图片等）            

| 字段名        | 类型  |  长度  | 备注 |
| :--------:   | :-----:  | :--:  |
| fansId     | int |11|粉丝ID(主键)|
| openId |varchar |32|openId|
| nickName| varchar|150|昵称|
| realName| varchar|20|姓名|
| mobile| varchar|15|联系方式|
| createTime| bigint|10|创建时间|

####全局参数表 global_setting

| 字段名        | 类型  |  长度  | 备注 |
| :--------:   | :-----:  | :--:  |
| settingId     | int |11|(主键)|
| dayLimit |int |11|每日奖励发放上限|
| firstAmount| int|11|初始红包金额|
| receiveType| int|11|领取方式（0，1，2分别对应产品文档的3种方式）|
| updateTime| bigint|10|最后修改时间|

####后台用户登录表 login_user

| 字段名        | 类型  |  长度  | 备注 |
| :--------:   | :-----:  | :--:  |
| userId     | int |11|用户ID(主键)|
| userName |varchar |30|用户名|
| realName| varchar|20|真实姓名|
| mobile| varchar|15|联系方式|
| roleType| int|1|角色类型(0 系统管理，1 一级管理员，2 二级管理员)|
| psw| varchar|32|密码(32位md5)|
| createTime| bigint|10|创建时间|
| status| int|1|状态(0 禁用，1 启用)|



