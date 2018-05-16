package study.ios.push;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.transmission.NotificationThread;
import javapns.notification.transmission.NotificationThreads;

/**
 * @author shuwei
 * @version 创建时间：2017年4月13日 上午11:33:18 类说明
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String deviceToken = "4729e4ea3c7eabc737e0d50b5bb050cc3a101cedd72f3fe52459ec8baedcbd76";
        String alert = "sw011给你发信息了";// push的内容
        int badge = 3;// 图标小红圈的数值
        String sound = "default";// 铃音

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        String certificatePath = "D:/maven/bbb.p12";
        String certificatePassword = "111111";// 此处注意导出的证书密码不能为空因为空密码会报错
        boolean sendCount = true;

        try {
            PushNotificationPayload payLoad = new PushNotificationPayload();
            payLoad.addAlert(alert); // 消息内容
            payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值

            if (!StringUtils.isBlank(sound)) {
                payLoad.addSound(sound);// 铃音
            }
            PushNotificationManager pushManager = new PushNotificationManager();
            // true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword,
                    false));
            List<PushedNotification> notifications = new ArrayList<PushedNotification>();
            // 发送push消息
            if (sendCount) {
                Device device = new BasicDevice();
                device.setToken(tokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {
                List<Device> device = new ArrayList<Device>();
                for (String token : tokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);
            }
            pushManager.stopConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test_ThreadPoolFeature(String keystore,String password,String token,boolean production) throws Exception {
        try {
          System.out.println("");
          System.out.println("TESTING THREAD POOL FEATURE");
          AppleNotificationServer server=new AppleNotificationServerBasicImpl(keystore,password,production);
          NotificationThreads pool=new NotificationThreads(server,3).start();
          Device device=new BasicDevice(token);
          System.out.println("Thread pool started and waiting...");
          System.out.println("Sleeping 5 seconds before queuing payloads...");
          Thread.sleep(5 * 1000);
          for (int i=1; i <= 4; i++) {
            Payload payload=PushNotificationPayload.alert("Test " + i);
            NotificationThread threadForPayload=(NotificationThread)pool.add(new PayloadPerDevice(payload,device));
            System.out.println("Queued payload " + i + " to "+ threadForPayload.getThreadNumber());
            System.out.println("Sleeping 10 seconds before queuing another payload...");
            // Thread.sleep(10 * 1000);
          }
          System.out.println("Sleeping 10 more seconds let threads enough times to push the latest payload...");
          Thread.sleep(10 * 1000);
        }
       catch (  Exception e) {
          e.printStackTrace();
        }
      }
    
    public static void main1(String[] args) throws Exception {
        test_ThreadPoolFeature("D:/maven/bbb.p12", "111111", "4729e4ea3c7eabc737e0d50b5bb050cc3a101cedd72f3fe52459ec8baedcbd76", false);
    }
}