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

## 数据库

### 数据库设计

#### 粉丝表 fans  

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝ID(主键)</td></tr>
<tr><td>openId</td><td>varchar</td><td>32</td><td>openId</td></tr>
<tr><td>nickName</td><td>varchar</td><td>150</td><td>昵称</td></tr>
<tr><td>realName</td><td>varchar</td><td>20</td><td>姓名</td></tr>
<tr><td>mobile</td><td>varchar</td><td>15</td><td>联系方式</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
</table>


#### 全局参数表 global_setting

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>settingId</td><td>int</td><td>11</td><td>主键</td></tr>
<tr><td>dayLimit</td><td>int</td><td>11</td><td>每日奖励发放上限</td></tr>
<tr><td>firstAmount</td><td>int</td><td>11</td><td>初始红包金额</td></tr>
<tr><td>receiveType</td><td>int</td><td>11</td><td>领取方式（0，1，2分别对应产品文档的3种方式）</td></tr>
<tr><td>updateTime</td><td>bigint</td><td>10</td><td>最后修改时间</td></tr>
</table>


#### 后台用户登录表 login_user

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>userId</td><td>int</td><td>11</td><td>用户ID(主键)</td></tr>
<tr><td>userName</td><td>varchar</td><td>30</td><td>用户名</td></tr>
<tr><td>realName</td><td>varchar</td><td>20</td><td>真实姓名</td></tr>
<tr><td>mobile</td><td>varchar</td><td>15</td><td>联系方式</td></tr>
<tr><td>roleType</td><td>int</td><td>1</td><td>角色类型(0 系统管理，1 一级管理员，2 二级管理员)</td></tr>
  <tr><td>psw</td><td>varchar</td><td>32</td><td>密码(32位md5)</td></tr>
  <tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
  <tr><td>status</td><td>int</td><td>1</td><td>状态(0 禁用，1 启用)</td></tr>
</table>

#### 操作记录表 operate_record

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>recordId</td><td>bigint</td><td>20</td><td>主键</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID（外键）</td></tr>
<tr><td>userId</td><td>int</td><td>11</td><td>登录用户ID（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>desc</td><td>varchar</td><td>100</td><td>备注</td></tr>
</table>

#### 红包记录表 prize_record

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>recordId</td><td>bigint</td><td>20</td><td>主键</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝ID（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>status</td><td>int</td><td>1</td><td>状态（-1 发送失败，0 待发送，1 发送成功，2 发送中）</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID（外键）</td></tr>
  <tr><td>type</td><td>int</td><td>1</td><td>类型（0 初次发放，1 追加发放）</td></tr>
  <tr><td>amount</td><td>int</td><td>11</td><td>金额（单位分，×100=元）</td></tr>
  <tr><td>errorInfo</td><td>varchar</td><td>100</td><td>失败原因</td></tr>
  <tr><td>billno</td><td>varchar</td><td>28</td><td>红包订单号（腾讯方）</td></tr>
</table>

#### 举报信息表 report

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>reportId</td><td>bigint</td><td>20</td><td>举报信息ID(主键)</td></tr>
<tr><td>fansId</td><td>int</td><td>11</td><td>粉丝id（外键）</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
<tr><td>happenTime</td><td>bigint</td><td>10</td><td>发生时间</td></tr>
<tr><td>happenPlace</td><td>varchar</td><td>200</td><td>发生地点</td></tr>
  <tr><td>eventDesc</td><td>varchar</td><td>1200</td><td>事件描述</td></tr>
  <tr><td>descVoice</td><td>varchar</td><td>200</td><td>描述语音</td></tr>
  <tr><td>descImages</td><td>varchar</td><td>500</td><td>佐证材料图片</td></tr>
  <tr><td>descVideo</td><td>varchar</td><td>150</td><td>佐证材料视频</td></tr>
  <tr><td>status</td><td>int</td><td>1</td><td>状态(参考产品文档)</td></tr>
  <tr><td>reportType1</td><td>int</td><td>11</td><td>初次分类</td></tr>
  <tr><td>reportType2</td><td>int</td><td>11</td><td>追加分类</td></tr>
  <tr><td>prizeStatus1</td><td>int</td><td>1</td><td>初次发放状态（0 未发，1 已发）</td></tr>
  <tr><td>prizeStatus2</td><td>int</td><td>1</td><td>追加发放状态（0 未发，1 已发）</td></tr>
  <tr><td>remark</td><td>varchar</td><td>1200</td><td>备注</td></tr>
  <tr><td>reply</td><td>varchar</td><td>1200</td><td>小编回复</td></tr>
</table>

#### 分类表 report_type

<table>
<tr><td>字段名</td><td>类型</td><td>长度</td><td>备注</td></tr>
<tr><td>typeId</td><td>int</td><td>11</td><td>主键</td></tr>
<tr><td>typeName</td><td>varchar</td><td>200</td><td>分类名称</td></tr>
<tr><td>typeDesc</td><td>varchar</td><td>2000</td><td>分类描述</td></tr>
<tr><td>amount</td><td>int</td><td>11</td><td>奖励金额</td></tr>
<tr><td>createTime</td><td>bigint</td><td>10</td><td>创建时间</td></tr>
</table>
