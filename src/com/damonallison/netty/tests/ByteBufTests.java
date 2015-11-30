package com.damonallison.netty.tests;


import io.netty.buffer.*;
import io.netty.util.IllegalReferenceCountException;
import org.junit.Assert;
import org.junit.Test;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


/**
 * Netty's {@link ByteBuf} has the following advantages over Java's {@link ByteBuffer}:
 *
 * <ul>
 * <li>Allocates and uses memory pools for memory efficiency (zero-copy).</li>
 * <li>Capacity is expanded on demand (a-la StringBuilder).</li>
 * <li>No need to call flip() between reader / writer modes.</li>
 * <li>Fluid interface supports method chaining.</li>
 * </ul>
 *
 * LISTEN UP: You can use Netty's ByteBuf by itself without the rest of Netty.
 *            Use the {@link Unpooled} class to create ByteBuf instances.
 */
public class ByteBufTests {

    @Test
    public void byteBufCreation() {

        // If you are creating multiple ByteBuf instances or want to use pooling,
        // create and use a pooled allocator.
        //
        // If you want to want to create one-off buffers or use unpooled memory,
        // use the helper Netty class "Unpooled".
        PooledByteBufAllocator allocator = new PooledByteBufAllocator(true);
        ByteBuf buf = allocator.directBuffer();
        buf.writeBytes("damon".getBytes(Charset.defaultCharset()));
        Assert.assertTrue(buf.writerIndex() == 5);
        Assert.assertTrue(buf.readerIndex() == 0);

        buf = Unpooled.copiedBuffer("damon".getBytes(Charset.defaultCharset()));

        // Copy the bytes into a data structure.
        byte[] copy = new byte[buf.readableBytes()];
        buf.readBytes(copy);
        Assert.assertEquals("damon", new String(copy, Charset.defaultCharset()));
        Assert.assertTrue(buf.readerIndex() == 5);
    }

    @Test
    public void referenceCounting() {
        ByteBuf buf = Unpooled.copiedBuffer("damon".getBytes());

        Assert.assertTrue(buf.refCnt() == 1);

        // .release() will return true iif .refCnt() == 0 after the release.
        Assert.assertTrue(buf.release());
        Assert.assertTrue(buf.refCnt() == 0);

        try {
            buf.writeByte(100);
            Assert.fail("After refCnt == 0, we should not be able to use the byteBuf");
        }
        catch(IllegalReferenceCountException exc) {

        }
    }

}
