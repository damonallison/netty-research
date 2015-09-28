# Netty Spike #

Example netty code. 

## Transports

* OIO (Old I/O) : blocking - use with legacy code
* NIO : use by default
* Local : intra-VM connections
* Embedded : unit testing

## Channels

ChannelHandlerAdapter : abstract base that 
	ChannelInboundHandlerAdapter
	ChannelOutboundHandlerAdapter