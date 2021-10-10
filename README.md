# FlashSaleSystem

## Server design ideas

The bottleneck is the database's ability to handle requests. After a large number of requests are sent to the database, the database may time out or break down due to its limited processing capability. So the idea is to try to intercept requests upstream of the system.

- For applications that read a lot (read inventory) and write a little (create order), use caching more (for inventory query operations through caching, reduce database operations)

- Cache, application, database cluster, load balancing; Asynchronous message processing

- The foreground can do some restrictions to the normal user's operation through JS, and cache some static resources with CDN and user browser

### The overall architecture

![architecture](https://github.com/jinshuai86/SecKillSystem/raw/master/architecture.svg)
