#

## 界面展示

游记详情

![游记详情](/img/essay_detail.png)

游记列表

![游记列表](/img/essay_list.png)

个人信息

![个人信息](/img/perinfo_page.png)

搜索列表

![搜素列表](/img/search_info.png)

## 系统设计

### 邮箱验证
邮箱验证使用Spring-mail提供的API处理,邮件发送服务器[application.yml](src/main/resources/application.yml)进行配置。

### 整合redis
使用Spring-redis-template进行redis操作,redis用来存储用户收藏文章信息和文章浏览次数。

key-value设计如下:

1. view:type:essay:article-id:2:num 1
2. collection:type:essay:user-id:1:article-id:1 1

### 整合MQ
邮件发送使用消息队列异步化，增加用户体验。

### 图片存储
图片使用腾讯云提供的对象存储方式进行存储。

### 注意事项
**使用请替换邮箱服务以及图片云存储**







