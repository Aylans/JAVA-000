package Week_02;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by Aylan
 * on 2020/10/28 4:13 下午
 */
public class HttpClient {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true){
            try {
                final Socket socket = serverSocket.accept();
            executorService.execute(()  -> run(socket));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void run(Socket socket) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write(EntityUtils.toString(httpEntity,"utf-8"));
            printWriter.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
