package study.lscj.eight;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 实现一个nio的server，接收多个客户端，发来的四则混合运算表达式，将结果返回给客户端
 * @author shuwei
 *
 */
public class MyNIOServer {
    private ExecutorService es = Executors.newCachedThreadPool();
    
    private void doAccept(SelectionKey sk) {
        print(sk.channel());
        print(sk.selector());
        
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);

            clientChannel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        
        try {
            len = channel.read(bb);
            if(len < 0) {
                disconnect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        bb.flip();
        es.execute(new CalThread(getString(bb), sk));
    }
    
    public static String getString(ByteBuffer buffer)  
    {  
        Charset charset = null;  
        CharsetDecoder decoder = null;  
        CharBuffer charBuffer = null;  
        try  
        {  
            charset = Charset.forName("UTF-8");  
            decoder = charset.newDecoder();  
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空  
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());  
            return charBuffer.toString();  
        }  
        catch (Exception ex)  
        {  
            ex.printStackTrace();  
            return "";  
        }  
    }  
    
    private void disconnect(SelectionKey sk) {
        try {
            sk.channel().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void doWrite(SelectionKey sk) {
        print("WWWWWWWWWWWWWWWWW");
    }
    
    Selector selector;
    
    public void startServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selector = SelectorProvider.provider().openSelector();
        // serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT | SelectionKey.OP_READ);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            selector.select();
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> i = readKeys.iterator();
            while (i.hasNext()) {
                SelectionKey selectionKey = i.next();
                i.remove();
                if (selectionKey.isAcceptable()) {
                    doAccept(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                    doRead(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                    doWrite(selectionKey);
                }
            }
        }
    }
    
    private void print(Object object) {
        System.out.println(Thread.currentThread() + ":" + object);
    }
    public static void main(String[] args) {
        MyNIOServer server = new MyNIOServer();
        try {
            server.startServer(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}