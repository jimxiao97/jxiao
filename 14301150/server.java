import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {  
    final int RECEIVE_PORT = 9090; // 该服务器的端口号  
    Socket request = null; // 用户请求的套接字  
    Thread receiveThread = null; 
    // receiveServer的构造器  
    public void recieveServer() {  
    	
        ServerSocket rServer = null; // ServerSocket的实例  
 
        try {  
            rServer = new ServerSocket(RECEIVE_PORT); // 初始化ServerSocket  
            System.out.println("Welcome to the server!");   
            System.out.println("The server is ready!");  
            System.out.println("Port: " + RECEIVE_PORT);  
            while (true) { // 等待用户请求  
                request = rServer.accept(); // 接收客户机连接请求  
                receiveThread = new serverThread(request); // 生成serverThread的实例  
                receiveThread.start();// 启动serverThread线程  
            }  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
  
    public static void main(String args[]) {  
        new Server();  
    } // end of main  
  
} // end of class  
  
//类serverThread  
class serverThread extends Thread {  
  
    Socket clientRequest; // 用户连接的通信套接字  
    BufferedReader input; // 输入流  
    PrintWriter output; // 输出流  
  
    public serverThread(Socket s) { // serverThread的构造器  
        this.clientRequest = s; // 接收receiveServer传来的套接字  
        InputStreamReader reader;  
        OutputStreamWriter writer;  
        try { // 初始化输入、输出流  
            reader = new InputStreamReader(clientRequest.getInputStream());  
            writer = new OutputStreamWriter(clientRequest.getOutputStream());  
            input = new BufferedReader(reader);  
            output = new PrintWriter(writer, true);  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        output.println("Welcome to the server!");  
        
    }  
  
    public void run() { // 线程的执行方法  
        String command = null; // 用户指令  
        String str = null;  
        boolean done = false;  
  
        while (!done) {  
            try {  
                str = input.readLine(); // 接收客户机指令  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
            command = str.trim().toUpperCase();  
            if (str == null || command.equals("QUIT")) // 命令quit结束本次连接  
                done = true;  
            else if (command.equals("HELP")) { // 命令help查询本服务器可接受的命令  
                output.println("query");  
                output.println("quit");  
                output.println("help");  
            } else if (command.startsWith("QUERY")) { // 命令query  
                output.println("OK to query something!");  
            }  
        }// end of while  
        try {  
            clientRequest.close(); // 关闭套接字  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        command = null;  
    }
    private String reverse(String str) {
		int length = str.length();
		char[] result = new char[length];
		for (int i = 0; i < length; i++) {
			result[i] = str.charAt(length - i - 1);
		}
		String re = new String(result);
		return re;
	}// end of run  
}  
