## 验证码服务使用指南

### 1 部署验证码服务

#### 1.1 基础环境

​		Java 1.8+

#### 1.2 安装Redis

验证码服务使用了Redis存储生成的信息，所以需要先安装Redis。**确认6379端口未被占用**

1. 下载安装包：https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.zip

2. 解压安装，将下载的Redis-x64-3.2.100.zip 解压到某个地址


3. 启动Redis：打开一个cmd窗口，使用cd命令切换到上边的解压目录

   ```shell
   cd C:\work\Redis-x64-3.2.100
   redis-server.exe redis.windows.conf
   ```

4. 测试：再打开一个cmd窗口，启动Redis客户端

   ```
   cd C:\work\Redis-x64-3.2.100
   redis-cli.exe -h 127.0.0.1 -p 6379
   ```

   设置键值对：

   ```
   set key abc
   ```

   取出键值对：

   ```
   get key
   ```

#### 1.3 部署验证码服务

##### 1.3.1 下载源码

```
git clone https://github.com/fightingape/sailing.git
```

##### 1.3.2 使用idea打开项目

##### 1.3.3 注册腾讯云短信服务

本服务中的短信发送使用了腾讯云短信服务，需要注册腾讯云开通短信服务：

https://cloud.tencent.com/product/isms/getting-started

1. 开通短信服务成功后，获取 SDKAppID 和 AppKey**
   云短信应用 **SDKAppID** 和 **AppKey** 可在 [短信控制台](https://console.cloud.tencent.com/sms) 的应用信息里获取。如您尚未添加应用，请登录 [短信控制台](https://console.cloud.tencent.com/sms) 添加应用。
2. 申请签名并确认审核通过
   一个完整的短信由短信**签名**和**短信正文内容**两部分组成，短信**签名**需申请和审核，**签名**可在 [短信控制台](https://console.cloud.tencent.com/sms)的相应服务模块【内容配置】中进行申请，详细申请操作请参见 [创建签名](https://cloud.tencent.com/document/product/382/18061#.E5.88.9B.E5.BB.BA.E7.AD.BE.E5.90.8D)。发送国际短信时，允许不携带签名。

3. 申请模板并确认审核通过
   短信或语音正文内容**模板**需申请和审核，**模板**可在 [短信控制台](https://console.cloud.tencent.com/sms) 的相应服务模块【内容配置】中进行申请，详细申请操作请参见 [创建正文模板](https://cloud.tencent.com/document/product/382/18061#.E5.88.9B.E5.BB.BA.E6.AD.A3.E6.96.87.E6.A8.A1.E6.9D.BF)。

##### 1.3.4 配置短信参数

1. 打开项目中sailing/src/main/resources/application.yml

2. 将腾讯云短信服务获取到参数做如下配置：

   ```yaml
   sms:
     qcloud:
       appId: 14000000
       appKey: 36ff7sd14d0d2342344cdf335cc25052
       templateId: 234323
       sign: 签名
   ```

##### 1.3.5 控制台获取验证码

如果暂时没有申请到腾讯云短信服务，可以临时将验证码输出到控制台。

修改sailing/src/main/java/cn/xsh/sailing/handler/SmsNumberVerificationHandler.java

```java
	@Override
	String confusion(Map<String, Object> payload, String key, String code) {
		String mobile = String.valueOf(payload.get("mobile"));

		// 使用腾讯云发送短信
		//smsService.send(mobile, code, getEffectiveTime());

		// 测试使用，在控制台输出验证码
		smsService.sendOnConsole(mobile, code, getEffectiveTime());
		return null;
	}
```

##### 1.3.6 启动服务

```log
Tomcat started on port(s): 56085 (http) with context path '/sailing'
Started SailingBootstrap in 3.224 seconds (JVM running for 4.01)
Completed initialization in 7 ms
```

在控制台查看验证码：

```
INFO [http-nio-56085-exec-1][QCloudSmsService.java:37] - 给手机号17717815555发送验证码：575184
```