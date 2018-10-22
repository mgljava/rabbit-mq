# rabbit-mq
## 发布-订阅
### 交换器(exchanges)代码详见：[Github](https://github.com/mgljava/rabbit-mq)
RabbitMQ 消息模型的核心思想是，生产者不直接发送任何消息给队列。实际上，一般的情况下，生产者甚至不知道消息应该发送到哪些队列。  
相反的，生产者只能将信息发送到交换器。交换器是非常简单的。它一边收到来自生产者的消息，另一边将它们推送到队列。交换器必须准确知道接收到的消息如何处理。是否被添加到一个特定的队列吗？是否应该追加到多个队列？或者是否应该被丢弃？这些规则通过交换器类型进行定义。
交换器的类型
* **direct** :处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
![direct](http://dl.iteye.com/upload/attachment/264104/0ec0f465-49c6-361c-ae2b-dd951a6ed1a9.png)
* **topic**: 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.” 只会匹配到“audit.irs”。
![topic](http://dl.iteye.com/upload/attachment/264108/11171ab4-af07-3ff6-bdf6-d1febda679c3.png)
* **headers**: 不处理路由键，而是根据发送的消息内容中的headers属性进行匹配。在绑定Queue与Exchange时指定一组键值对；当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时指定的键值对进行匹配；如果完全匹配则消息会路由到该队列，否则不会路由到该队列。headers属性是一个键值对，可以是Hashtable，键值对的值可以是任何类型。而fanout，direct，topic 的路由键都需要要字符串形式的。不过headers比较少用到  
* **fanout**:它只是将所有收到的消息广播到所有它所知道的队列。 
![fanout](http://dl.iteye.com/upload/attachment/264106/0bbdcd3d-9fc6-3107-b7e0-db67c174d46a.png)
---
声明交换器`channel.exchangeDeclare("logs", "fanout");`  
使用：`channel.basicPublish("logs", "", null, message.getBytes());`



