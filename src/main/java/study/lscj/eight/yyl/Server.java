package study.lscj.eight.yyl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Server {

	private final ScriptEngine engine;
	private final ByteBuffer buffer;
	private final Selector selector;

	public Server() throws IOException {
		selector = Selector.open();
		buffer = ByteBuffer.allocate(1);
		engine = new ScriptEngineManager().getEngineByName("JavaScript");
	}

	public void start() throws IOException {
		
		System.out.println(":start");
		
		ServerSocketChannel ss = ServerSocketChannel.open();
		ss.socket().bind(new InetSocketAddress(8000));
		ss.configureBlocking(false);

		Map<SelectionKey, ByteArrayOutputStream> group = new HashMap<>();

		ss.register(selector, SelectionKey.OP_ACCEPT);
		while (selector.select() > 0) {
			Set<SelectionKey> set = selector.selectedKeys();
			for (SelectionKey key : set) {
				SocketChannel sc = null;

				if (key.isAcceptable()) {
					sc = ss.accept();
					System.out.println("ACCEPTABLE:" + sc.socket());
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					try {
						sc = (SocketChannel) key.channel();
						sc.read(buffer);
						buffer.flip();

						ByteArrayOutputStream array = group.get(key);
						if (array == null) {
							array = new ByteArrayOutputStream();
						}
 
						byte[] bytes = buffer.array();

						//111111111111111 \n 22222222222222222222

						int remaining = buffer.remaining();
						for (int i = 0; i < remaining; i++) {
							byte b = bytes[i];
							if (b == '\n') {
								String expression = new String(array.toByteArray());
								String result = operation(expression);
								sc.write(ByteBuffer.wrap((result).getBytes()));
								array = new ByteArrayOutputStream();
							} else {
								array.write(b);
							}
						}
						
						group.put(key, array);
						
					} catch (Exception e) {
						key.cancel();
						group.remove(key);
						if (sc != null) {
							sc.close();
						}
					}
				}

			}
			set.clear();
		}
	}

	private String operation(String expression) {
		try {
			System.err.println(expression);
			return "" + engine.eval("(" + expression + ")");
		} catch (Exception e) {
			return "err";
		}
	}

	public static void main(String[] args) throws Exception {
		new Server().start();
	}

}
