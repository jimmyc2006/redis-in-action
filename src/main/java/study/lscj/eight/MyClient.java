package study.lscj.eight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyClient {
    private static ExecutorService tp = Executors.newCachedThreadPool();

    public static class EchoClient implements Runnable {
        private String calStr;

        public EchoClient(String calStr) {
            this.calStr = calStr;
        }

        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.print(calStr);
                writer.println();
                writer.flush();

                print(calStr);
                Thread.sleep(500);
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                print("result from server:" + reader.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try{
                    writer.close();
                }  catch (Exception e) {
                }
                try {
                    reader.close();
                } catch (IOException e) {
                }
                try {
                    client.close();
                } catch (IOException e) {
                }
            }
        }

        private void print(Object object) {
            System.out.println(Thread.currentThread() + ":" + object);
        }
    }


    public static void main(String[] args) {
        EchoClient ec1 = new EchoClient("1+1");
        EchoClient ec2 = new EchoClient("10+10*10");
        EchoClient ec3 = new EchoClient("3+3*100");
        tp.execute(ec1);
        tp.execute(ec2);
        tp.execute(ec3);
        tp.shutdown();
    }
}
