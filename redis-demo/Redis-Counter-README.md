# Redis-Counter Read me



## 相关概念定义

- Test：多次执行某个或某些action，相关配置为间隔时间和执行次数，名字以及所要执行的action名字列表和描述文字
- Action：一般定义为用户执行的操作，相关配置为名字，所要执行的counter和描述文字
- Counter：一般定义为redis执行的操作，相关配置为键值，所要进行的操作，超时时间，和所操作counter的值的类型
- ConfigUntils：读取配置文件的工具类
- LockUntils :  读写锁的工具类，用于给监听文件修改从而自适应配置的写入操作和用户进行相关操作的读出操作加上读写锁从而保证用户单次操作的配置是相同版本
- DateSplitUtils：时间切片的类，用于获取freq的hash所存储的field值
- MonitorUtils：文件监听类，使用了apache.commons.io，对配置文件进行监听，监听到文件改变后进行重新load配置的操作
- TestResolver：test级别的解决，读取test的相关配置，设置定时任务，并设置定时取消的线程，从而进行多次执行某个或某些action的操作
- ActionResolver：action级别的解决，读取action的相关配置，并顺序执行索要执行的counter
- ResolverFactory：根据counter的类型返回对应的resolver进行执行，并在此获取jedis资源，jedis是action层的资源，一个action共用一个jedis
- CounterResolver：所有counter执行的父类
- domain package：Test，counter，action等的定义类，其中Limit类是限流操作的相关配置：有zset实现的滑动窗口限流和list实现的令牌桶限流



## 所实现的功能

- 实现num类型的值的增加减少查询和设置超时时间
- 实现String类型的值的覆盖和查询以及设置超时时间
- 实现zset类型的滑动窗口实现的限流操作（执行操作：执行test的第1个）
- 实现list类型的令牌桶算法的限流操作（执行操作：分别执行test的第2，3个test，分别是向令牌桶中放入令牌和从令牌桶中拿出令牌）
- 实现hash类型的freq统计（分钟级）



