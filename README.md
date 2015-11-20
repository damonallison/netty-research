# Netty Spike #

A simple client / server application using Netty.

#### Todo

* Interacting with the channel from a handler : always through the context?
* Attribute / AttributeKey : Per channel attributes that are associated to a channel through the <code>ChannelContext</code>
* What's a "User Event"?

## ChannelHandler Class Hierarchy ##

The ChannelHandler class hierarchy with Netty 4.1:

<pre>
interface ChannelHandler
    abstract class ChannelHandlerAdapter
    interface ChannelOutboundHandler
        ChannelOutboundHandler
    interface ChannelInboundHandler
        class ChannelInboundHandlerAdapter

</pre>


