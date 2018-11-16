package org.fisco.bcos.web3j.protocol.websocket;

import org.fisco.bcos.web3j.protocol.core.Request;
import org.fisco.bcos.web3j.protocol.core.methods.response.EthBlockNumber;
import org.fisco.bcos.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.junit.Ignore;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Ignore
public class WebSocketRpcTest {

    private WebSocketClient webSocketClient  = new WebSocketClient(new URI("ws://10.107.105.59:8888/"));


    private WebSocketService service = new WebSocketService(webSocketClient, true);

    private Request<?, EthBlockNumber> request = new Request<>(
            "blockNumber",
            Arrays.asList("0"),
            service,
            EthBlockNumber.class);

    public WebSocketRpcTest() throws URISyntaxException {
    }

    @Ignore
    @Test
    public void testConnectViaWebSocketClient() throws Exception {

        service.connect();
        service.sendAsync(request, Web3ClientVersion.class);

        //  verify(webSocketClient).connectBlocking();
    }
@Ignore
    @Test
    public void testSSL() throws NoSuchAlgorithmException, KeyManagementException {

            X509TrustManager x509m = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            };
            // 获取一个SSLContext实例
            SSLContext s = SSLContext.getInstance("SSL");
            // 初始化SSLContext实例
            s.init(null, new TrustManager[] { x509m },
                    new java.security.SecureRandom());
            // 打印这个SSLContext实例使用的协议
            System.out.println("缺省安全套接字使用的协议: " + s.getProtocol());
            // 获取SSLContext实例相关的SSLEngine
            SSLEngine e = s.createSSLEngine();
            System.out
                    .println("支持的协议: " + Arrays.asList(e.getSupportedProtocols()));
            System.out.println("启用的协议: " + Arrays.asList(e.getEnabledProtocols()));
            System.out.println("支持的加密套件: "
                    + Arrays.asList(e.getSupportedCipherSuites()));
            System.out.println("启用的加密套件: "
                    + Arrays.asList(e.getEnabledCipherSuites()));
        }

        @Test
    public  void notOk() throws IOException {
        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket server = (SSLServerSocket) factory
                .createServerSocket(20000);
        System.out.println("ok");
        server.accept();
    }

//    @Test
//    public  void sslSocketServer() throws Exception {
//
//        // key store相关信息
//        String keyName = "cmkey";
//        char[] keyStorePwd = "123456".toCharArray();
//        char[] keyPwd = "123456".toCharArray();
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//
//        // 装载当前目录下的key store. 可用jdk中的keytool工具生成keystore
//        InputStream in = null;
//        keyStore.load(in = Test2.class.getClassLoader().getResourceAsStream(
//                keyName), keyPwd);
//        in.close();
//
//        // 初始化key manager factory
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory
//                .getDefaultAlgorithm());
//        kmf.init(keyStore, keyPwd);
//
//        // 初始化ssl context
//        SSLContext context = SSLContext.getInstance("SSL");
//        context.init(kmf.getKeyManagers(),
//                new TrustManager[] { new MyX509TrustManager() },
//                new SecureRandom());
//
//        // 监听和接收客户端连接
//        SSLServerSocketFactory factory = context.getServerSocketFactory();
//        SSLServerSocket server = (SSLServerSocket) factory
//                .createServerSocket(10002);
//        System.out.println("ok");
//        Socket client = server.accept();
//        System.out.println(client.getRemoteSocketAddress());
//
//        // 向客户端发送接收到的字节序列
//        OutputStream output = client.getOutputStream();
//
//        // 当一个普通 socket 连接上来, 这里会抛出异常
//        // Exception in thread "main" javax.net.ssl.SSLException: Unrecognized
//        // SSL message, plaintext connection?
//        InputStream input = client.getInputStream();
//        byte[] buf = new byte[1024];
//        int len = input.read(buf);
//        System.out.println("received: " + new String(buf, 0, len));
//        output.write(buf, 0, len);
//        output.flush();
//        output.close();
//        input.close();
//
//        // 关闭socket连接
//        client.close();
//        server.close();
//    }
}

