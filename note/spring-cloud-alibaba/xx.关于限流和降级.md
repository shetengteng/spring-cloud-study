限流
限流目的是通过对并发访问/请求进行限速或者一个事件窗口内的请求进行限速，来保护系统。
一般高并发系统常见限流有：

限制总并发数（如数据库连接池、线程池）
瞬时并发数（如Nginx的limit_conn模块）
限制时间窗口内平均速率（如Guava的RateLimiter、Nginx的limit_req模块，用来限制每秒的平均速率）
限制远程接口调用速率
限制MQ消费
限流算法
主要有令牌桶、漏桶

令牌桶算法
是一个存放固定容量令牌的桶，按照固定速率往桶里添加令牌

假设限制为2r/s，则按照500ms的固定速率网桶中添加令牌
桶中最多存放b个令牌，当桶满时，新添加令牌被丢弃或拒绝
当一个n个字节大小数据包到达，将从桶中删除n个令牌，接着数据包被发送到网络上
如果桶中令牌不足n个，则不会删除令牌，且该数据包将被限流
漏桶算法
漏桶作为计量工具，可以用于流量整形（Traffic Shaping）和流量控制（Traffic Policing）。

一个固定容量的漏桶，按照常量固定速率流出水滴
如果桶是空的，则不需要流出水滴
可以以任意速率流入水滴到桶里
如果流入水超出了桶的容量，则流入的水滴溢出了（被丢弃），而漏桶容量是不变的
二者比较
令牌桶是展昭固定速率王桶中添加令牌，请求是否被处理需要看桶中令牌是否足够，当令牌数减为0时，则拒绝新的请求。
漏桶则是按照固定速率流出请求，流入请求速率任意，当流入请求数累计到漏桶容量时，则新流入请求被拒绝。

应用级限流
限流总并发/连接/请求数
限制总资源数
限流某个接口的总并发/请求数
限流某个接口的时间窗请求数
平滑限流某个接口的请求数：前面几个限流都不能很好的应对突发请求，即瞬间请求可能都被允许，从而导致一些问题（极限值请求），因此在一些场景中需要对突发请求进行整形，整形为平均速率请求处理（比如5r/s，则每隔200ms处理一个请求，平滑了速率）。
分布式限流
分布式限流最关键是要讲限流服务做成原子化，而解决方案可以使用Redis+Lua或者Nginx+Lua实现，通过这两种可以实现高并发和高性能。
主要思想就是使用Redis作为请求数存储，多台机从同一个Redis中共享某一个计数器，如果应用非常大，Redis抗不住？

是否真的会有这么大？
是否可以通过一致性哈希将分布式限流分片，这就回到了负载均衡和隔离
并发量太大，是否可以降为应用级限流？
接入层限流
接入层通常只请求流量的入口，该层主要目的有：负载均衡、非法 请求过滤、请求聚合、缓存、降级等等。
以Nginx为例，可以使用

ngx_http_limit_conn_module连接数限流模块
ngx_http_limit_req_module漏桶算法
节流
有时候我们想在特定时间窗口内对重复的相同事件最多只处理一次，或者想限制多个连续相同事件最小执行时间间隔，那么可使用节流（Throttle）实现，其防止多个相同事件连续重复执行。主要有以下几种throttleFirst、throttleLast、throttleWithTimeout。

throttleFirst/throttleLast是指在一个时间窗口内，如果有重复的多个相同事件要处理，则只处理第一个或最后一个。其相当于一个事件频率控制器，把一段时间内重复的多个相同事件变为一个，减少事件处理频率，从而减少无用处理，提升性能。例如：
一个场景是网页中的resize、scroll和mousemove，当我们改变浏览器大小等事件，可能会触发多次，但是不能执行多次，所以节流就派上用场，可以使用jquery-throttle-debounce-plugin等实现节流。
throttleWithTimeout也叫做debounce（去抖），仙则两个连续事件的先后执行事件不得小于某个时间窗口。例如：
搜索关键字自动补全，如果没录入一个字就发送一次请求，而先输入的自动补全会被很快被下一个字符覆盖，那么会导致先期的自动补全无用。所以可以使用throttleWithTimeout，通过它来减少频繁网络请求，避免每
降级
当访问量剧增、服务出现问题（如响应长或者不响应）或非核心服务影响核心流程性能，仍然需要保证服务还是可用。系统可以根据一些关键因素降级。

降级预案
对系统进行梳理，看看系统是不是可以丢卒保帅，从而梳理哪些必须必须保护，哪些可以降级
比如，可以按照log级别来设置降级，一般，警告，错误，严重错误
比如，可以根据功能程度来降级，对让一些耗时的请求进行降级，能够维持运行，不影响总体，例如如下：

页面降级
页面片段降级
页面异步请求降级
服务功能降级
读降级（只读缓存）
写降级（只进行cache更新）
爬虫降级
自动开关降级
由预先定义好的规则，进行降级，例如方案有根据超时降级（Hystrix有），根据失败次数（统计后判断），故障降级，限流降级（排队页面，无货，错误页（如活动太火爆了，稍后重试））。

人工开关降级
当监控到线上一项服务存在问题时，需要暂时将这些服务摘掉

读服务降级
可以暂时切换读，暂时屏蔽读，页面降级、页面片段降级、页面异步请求降级都是读服务，目的是丢卒保帅。

写服务降级
写服务在大多数场景是不可降级的，不过，可以通过一些迂回战术来解决问题，比如将同步操作转化为异步操作，或者限制写的比例

多级降级
多级降级可以理解为将前面几种方法都整合起来，例如，降级也可以分层：

页面JS降级开关
接入层降级开关
应用层降级开关
参考资料

亿级流量网站架构核心技术
