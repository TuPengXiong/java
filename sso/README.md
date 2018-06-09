概述

使用oauth2保护你的应用，可以分为简易的分为三个步骤

配置资源服务器
配置认证服务器
配置spring security
前两点是oauth2的主体内容，但前面我已经描述过了，spring security oauth2是建立在spring security基础之上的，所以有一些体系是公用的。

oauth2根据使用场景不同，分成了4种模式

授权码模式（authorization code）
简化模式（implicit）
密码模式（resource owner password credentials）
客户端模式（client credentials）


http://localhost:10200/sso/oauth/authorize?client_id=test&state=test&response_type=code&scope=test&redirect_uri=https%3a%2f%2fbaidu.com
http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=password&response_type=code&redirect_uri=https%3a%2f%2fbaidu.com
http://localhost:10200/sso/oauth/authorize?client_id=test&grant_type=authorization_code&code=4bQZ6J&response_type=token&redirect_uri=https%3a%2f%2fbaidu.com
http://localhost:10200/sso/oauth/authorize?client_id=test&state=test&response_type=code&scope=info&redirect_uri=https%3a%2f%2fbaidu.com
# grant_type
1. authorization_code
1. password
1. implicit
1. refresh_token
1. client_credentials
# response_type
1. code
1. token