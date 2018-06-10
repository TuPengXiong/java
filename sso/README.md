## 概述

## 使用oauth2保护你的应用，可以分为简易的分为三个步骤

1. 配置资源服务器
1. 配置认证服务器
1. 配置spring security
1. 前两点是oauth2的主体内容，但前面我已经描述过了，spring security oauth2是建立在spring security基础之上的，所以有一些体系是公用的。

## oauth2根据使用场景不同，分成了4种模式

1. 授权码模式（authorization code）
1. 简化模式（implicit）
1. 密码模式（resource owner password credentials）
1. 客户端模式（client credentials）

```
AuthorizationEndpoint  /oauth/authorize
TokenEndpoint /oauth/token
CheckTokenEndpoint  /oauth/check_token
WhitelabelApprovalEndpoint /oauth/confirm_access  /oauth/error
```

## 客户端模式
```
-- grant_type=client_credentials 
http://localhost:10200/sso/oauth/token?grant_type=client_credentials&scope=test&client_id=c1&client_secret=123456

{
  "access_token" : "079662be-1320-4f41-95b8-9fdf8e2c6f0c",
  "token_type" : "bearer",
  "expires_in" : 42855,
  "scope" : "test"
}
```
## 授权码模式
```
1. 获取授权码
--response_type=code

http://localhost:10200/sso/oauth/authorize?client_id=test&state=test&response_type=code&scope=test&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/?code=wbz8ry&state=test

-- grant_type=authorization_code --response_type=token

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=authorization_code&code=wbz8ry&response_type=token&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/#access_token=f8e308a4-c8e0-4521-9343-2bd113adad08&token_type=bearer&expires_in=43199&scope=test%20info
```

## 密码模式
```
-- grant_type=password --response_type=code

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=password&response_type=code&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/?code=TGxPd4

-- grant_type=password --response_type=token

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=password&response_type=token&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/#access_token=f8e308a4-c8e0-4521-9343-2bd113adad08&token_type=bearer&expires_in=42792&scope=test%20info
```

## 简化模式（implicit）
```
-- grant_type=implicit --response_type=code

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=implicit&response_type=code&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/?code=LIaIx2

-- grant_type=implicit --response_type=token

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=implicit&response_type=token&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/#access_token=f8e308a4-c8e0-4521-9343-2bd113adad08&token_type=bearer&expires_in=42567&scope=test%20info
```

## refresh_token
```
-- grant_type=refresh_token --response_type=token

http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=refresh_token&response_type=token&redirect_uri=https%3a%2f%2fbaidu.com
https://www.baidu.com/#access_token=f8e308a4-c8e0-4521-9343-2bd113adad08&token_type=bearer&expires_in=42309&scope=test%20info

```

## 检验token
```
http://localhost:10200/sso/oauth/check_token?token=f8e308a4-c8e0-4521-9343-2bd113adad08
```
# grant_type
1. authorization_code
1. password
1. implicit
1. refresh_token
1. client_credentials
# response_type
1. code
1. token


## filter order
```
ChannelProcessingFilter

ConcurrentSessionFilter

WebAsyncManagerIntegrationFilter

SecurityContextPersistenceFilter

HeaderWriterFilter

CorsFilter

CsrfFilter

LogoutFilter

X509AuthenticationFilter

AbstractPreAuthenticatedProcessingFilter

filterToOrder."org.springframework.security.cas.web.CasAuthenticationFilter"
		order);

UsernamePasswordAuthenticationFilter

ConcurrentSessionFilter

filterToOrder.
		"org.springframework.security.openid.OpenIDAuthenticationFilter"

DefaultLoginPageGeneratingFilter

ConcurrentSessionFilter

DigestAuthenticationFilter

BasicAuthenticationFilter

RequestCacheAwareFilter

SecurityContextHolderAwareRequestFilter

JaasApiIntegrationFilter

RememberMeAuthenticationFilter

AnonymousAuthenticationFilter

SessionManagementFilter

ExceptionTranslationFilter

FilterSecurityInterceptor

SwitchUserFilter
```
	