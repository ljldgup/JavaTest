package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;

/**
 * @author : xxjia
 * @since : 2019/9/9
 */
public abstract class Pooled {
    private static final PooledByteBufAllocator ALLOC = PooledByteBufAllocator.DEFAULT;

    private Pooled() {
    }

    public static ByteBuf buffer() {
        return ALLOC.buffer();
    }

    public static ByteBuf buffer(int initialCapacity) {
        return ALLOC.buffer(initialCapacity);
    }

    public static boolean isEmpty(ByteBuf buf) {
        return buf == null || buf.readableBytes() == 0;
    }

    public static ByteBuf wrappedBuffer(ByteBuf... buffers) {
        return new CompositeByteBuf(ALLOC, PlatformDependent.directBufferPreferred(), buffers.length, buffers);
    }
}
