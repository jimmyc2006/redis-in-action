package study.lscj.eight;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class CalThread implements Runnable {
    private String content;
    private SelectionKey sk;
    
    public CalThread(String content, SelectionKey selectionKey) {
        this.content = content;
        sk = selectionKey;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + ":" + content);
        SocketChannel channel = (SocketChannel) sk.channel();
        Selector selector = sk.selector();
        // 计算值
        double value = Arithmetic.arithmetic(content);
        String result = value + System.lineSeparator();
        // 回写
        try {
            channel.write(ByteBuffer.wrap(result.getBytes()));
        } catch (Exception e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            disconnect(sk);
        }
    }
    
    private void disconnect(SelectionKey sk) {
        try {
            sk.channel().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}