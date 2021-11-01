# FlashSaleSystem

* [FlashSaleSystem](#flashsalesystem)
  * [Project highlights](#project-highlights)
    * [Distributed system scheme](#distributed-system-scheme)
    * [System optimization](#system-optimization)
    * [In\-depth microservice skills](#in-depth-microservice-skills)
    * [The security policy](#the-security-policy)
  * [Server design ideas](#server-design-ideas)
    * [The overall architecture](#the-overall-architecture)
  * [The detailed guide to build such a system](#the-detailed-guide-to-build-such-a-system)
  * [Mainly questions](#mainly-questions)

## Project highlights

### Distributed system scheme

From a single machine to a cluster, it is easy to scale horizontally simply by adding servers to cope with greater traffic and concurrency

### System optimization

Browser cache/Nginx cache/page cache/object cache/RabbitMQ queue asynchronous ordering, reduce network traffic, reduce database pressure, improve the system's concurrent processing capability

### In-depth microservice skills

SpringBoot/RabbitMQ/Redis/MySQL, based on the most popular Java microservices framework

### The security policy

Graphic verification code, flow limiting and brush prevention, interface address hiding, various security mechanisms to reject the robot ticket brushing

## Server design ideas

The bottleneck is the database's ability to handle requests. After a large number of requests are sent to the database, the database may time out or break down due to its limited processing capability. So the idea is to try to intercept requests upstream of the system.

- For applications that read a lot (read inventory) and write a little (create order), use caching more (for inventory query operations through caching, reduce database operations)

- Cache, application, database cluster, load balancing; Asynchronous message processing

- The foreground can do some restrictions to the normal user's operation through JS, and cache some static resources with CDN and user browser

### The overall architecture

![architecture](https://github.com/codesssss/FlashSale/blob/master/architecture.svg)

## The detailed guide to build such a system

[Chap01: Integrate Mybatis and Redis](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap01.pdf)

[Chap02: MD5 encryption and globle exception handler](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap02.pdf)

[Chap03: Implement distributed session via redis](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap03.pdf)

[Chap04: Implement the flash sale function](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap04.pdf)

[Chap05: Using JMeter to pressure test](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap05.pdf)

[Chap06: Page cache and object cache](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap06.pdf)

[Chap07: Integrate rabbitMQ and optimize the interface](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap07.pdf)

[Chap08: Optimizing the flash sale system after integrated rabbitMQ](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap08.pdf)

[Chap09: Dynamic flash sale url, mathematical formula verification code and the interface current limiting](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap09.pdf)

[Chap10: Conclusion the project](https://github.com/codesssss/FlashSale/blob/master/guideDoc/Chap10.pdf)

## Main questions

#### How to ensure thread safety and prevent oversold when inventory is deducted in Redis?

Redis has a decr() method that implements decrement and atomicity

#### How to limit traffic and prevent robot access?

This is done by interceptor, and we have a custom annotation that will mark a method, specify the number of times it is accessed per unit of time, and if it exceeds the requirement, it will be intercepted.

Interceptor is inherited HandlerInterceptorAdapter, rewriting is preHandle method, in this method, will visit frequency synchronization to Redis, there is the period of validity of the key/value pair. Finally, you need to configure the interceptor into the project, inheriting the WebMvcConfigurerAdapter and overriding the addInterceptors() method.



