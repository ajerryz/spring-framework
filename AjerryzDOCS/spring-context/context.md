# spring-context
# 各个体系
## BeanFactory体系
```text
                BeanFactory
                    |
ListableBeanFactory, HierarchicalBeanFactory
```
BeanFactory:
- 概述：BeanFactory 是 Spring IoC 容器的基础接口，定义了获取 Bean 的基本方法，如 getBean 方法，是 ApplicationContext 的基础。
- 功能：提供了对 Bean 的基本管理功能，包括根据名称或类型获取 Bean 实例。

ListableBeanFactory
- 概述：继承自 BeanFactory，扩展了列出所有 Bean 定义的功能。
- 功能：可以获取容器中所有 Bean 的名称、类型等信息，方便进行批量操作。

HierarchicalBeanFactory
- 概述：同样继承自 BeanFactory，支持 BeanFactory 的层次结构，允许存在父子容器。
- 功能：可以在父容器中查找 Bean，实现容器的分层管理。

## MessageSource体系
```text
        message
            |
 HierarchicalMessageSource
```
MessageSource
- 概述：用于实现国际化消息的解析和获取，是 Spring 国际化支持的核心接口。
- 功能：提供了根据消息代码和参数获取国际化消息的方法。

HierarchicalMessageSource
- 概述：继承自 MessageSource，支持消息源的层次结构，允许存在父子消息源。
- 功能：当在当前消息源中找不到消息时，可以到父消息源中查找。

## ApplicationEventPublisher体系
ApplicationEventPublisher
- 概述：定义了发布应用程序事件的方法，是 Spring 事件机制的基础。
- 功能：提供了 publishEvent 方法，用于发布事件。

ApplicationEventMulticaster
- 概述：负责事件的多播，将事件分发给所有注册的事件监听器。
- 功能：管理事件监听器，并将事件广播给相应的监听器进行处理。

## Resource体系
Resource
- 概述：表示资源的抽象接口，可用于访问不同类型的资源，如文件、类路径资源等。
- 功能：提供了获取资源内容、判断资源是否存在等方法。

ResourceLoader
- 用于加载资源，根据资源路径返回 Resource 对象。
- 提供了 getResource 方法，根据资源路径加载资源。

ResourcePatternResolver
- 继承自 ResourceLoader，支持根据资源模式加载多个资源。
- 功能：可以使用通配符等模式匹配多个资源，返回资源数组。

## ApplicationContext接口
- 概述：ApplicationContext 是 spring - context 模块中最顶层的抽象接口，它继承自 ListableBeanFactory、HierarchicalBeanFactory、MessageSource、ApplicationEventPublisher 和 ResourcePatternResolver 等接口，整合了多种功能。
- 功能:
  - IoC 容器功能：继承自 BeanFactory 系列接口，具备管理 Bean 的能力，如创建、获取和销毁 Bean 等操作。 
  - 国际化支持：通过实现 MessageSource 接口，提供国际化消息的解析和获取功能。 
  - 事件发布：实现 ApplicationEventPublisher 接口，可发布和处理应用程序事件。 
  - 资源加载：实现 ResourcePatternResolver 接口，能够加载不同类型的资源，如文件、类路径资源等。
- 常见实现类：ClassPathXmlApplicationContext、FileSystemXmlApplicationContext、AnnotationConfigApplicationContext 等。


```text
EnvironmentCapable
    Environment getEnvironment();
 
ListableBeanFactory -> BeanFactory
HierarchicalBeanFactory -> BeanFactory
MessageSource
ApplicationEventPublisher
ResourcePatternResolver -> ResourceLoader
| 
implements 
| 
ApplicationContext , Lifecycle(start(),stop(),isRunning()),  Closeable(close())
|
|
ConfigurableApplicationContext
```