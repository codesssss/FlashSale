# FlashSaleSystem



## Project highlights

### Distributed system scheme

From a single machine to a cluster, it is easy to scale horizontally simply by adding servers to cope with greater traffic and concurrency

### System optimization

Browser cache/Nginx cache/page cache/object cache/RabbitMQ queue asynchronous ordering, reduce network traffic, reduce database pressure, improve the system's concurrent processing capability

### In-depth microservice skills

SpringBoot/RabbitMQ/Redis/MySQL, based on the most popular Java microservices framework

#### The security policy

Graphic verification code, flow limiting and brush prevention, interface address hiding, various security mechanisms to reject the robot ticket brushing

## Server design ideas

The bottleneck is the database's ability to handle requests. After a large number of requests are sent to the database, the database may time out or break down due to its limited processing capability. So the idea is to try to intercept requests upstream of the system.

- For applications that read a lot (read inventory) and write a little (create order), use caching more (for inventory query operations through caching, reduce database operations)

- Cache, application, database cluster, load balancing; Asynchronous message processing

- The foreground can do some restrictions to the normal user's operation through JS, and cache some static resources with CDN and user browser

### The overall architecture

![architecture](https://github.com/jinshuai86/SecKillSystem/raw/master/architecture.svg)
