# Oauth2.0 Demo

通过实现github授权登录来理解Oauth2.0协议

## 准备

- java1.8
- Maven

## 注意事项

client_secret和client_id不写在项目里，为了安全考虑，运行前从github拷贝过来即可

运行前需要补充`OauthConstants`中的`client_secret`和`client_id`, 以及`login.html`中的`client_id`

## 运行示例

1. 启动`MyOauthDemoApplication`
2. 访问`http://localhost:8080/login.html`

## 参考资料

- [阮一峰博客](http://www.ruanyifeng.com/blog/2019/04/oauth_design.html)
- [github oauth](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/#web-application-flow)
- [github api](https://docs.github.com/en/free-pro-team@latest/rest/reference/users#get-the-authenticated-user)
  
  
