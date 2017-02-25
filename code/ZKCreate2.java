import java.io.IOException;
import java.io.*;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import java.lang.Math;

public class ZKCreate2 {
 // create static instance for zookeeper class.
 private static ZooKeeper zk;

 // create static instance for ZooKeeperConnection class.
 private static ZooKeeperConnection conn;

 // Method to create znode in zookeeper ensemble
 public static void create(String path, byte[] data) throws
 KeeperException, InterruptedException {
  zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
   CreateMode.PERSISTENT);
 }

 public static void main(String[] args) throws IOException {

  File file = new File("META_ip.txt");
  File file1 = new File("META.txt");


  // creates a FileWriter Object
  FileWriter writer = new FileWriter(file);
  FileWriter writer1 = new FileWriter(file1, true);
  // znode path
  int ip = Math.abs("second".hashCode());
  ip = ip % 255;
  String path = "/zk-demo/my-node/second"; // Assign path to znode
  String ip_addr = "127.0.0." + String.valueOf(ip);
  // data in byte array
  byte[] data = (ip_addr).getBytes(); // Declare data
  //System.out.println("1 " + ip_addr);

  try {
   conn = new ZooKeeperConnection();
   zk = conn.connect("localhost");
   create(path, data); // Create the data to the specified path
   conn.close();
  } catch (Exception e) {
   System.out.println(e.getMessage()); //Catch error message
  }

  writer.write(ip_addr + "\n");
  ip = Math.abs("secondR".hashCode());
  ip = ip % 255;
  ip = Math.abs(ip);
  path = "/zk-demo/my-node/secondR"; // Assign path to znode
  String ip_addr2 = "127.0.0." + String.valueOf(ip);
  // data in byte array
  data = (ip_addr2).getBytes(); // Declare data
  // System.out.println("1R " + ip_addr);

  try {
   conn = new ZooKeeperConnection();
   zk = conn.connect("localhost");
   create(path, data); // Create the data to the specified path
   conn.close();
  } catch (Exception e) {
   System.out.println(e.getMessage()); //Catch error message
  }
  writer.write(ip_addr2 + "\n");
  writer1.write("2 " + ip_addr + " " + ip_addr2 + "\n");
  writer.flush();
  writer.close();
  writer1.flush();
  writer1.close();
 }
}