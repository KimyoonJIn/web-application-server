package webserver;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

// 쓰레드를 상속하고 있으며, 사용자의 요청에 대한 처리와 응답에 대한 처리를 담당하는 가장 중심이 되는 클래스
public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        // InputStream 은 서버에서 클라이언트에 응답을 보낼 때 전달되는 데이터를 담당하는 스트림
        // OutputStream은 서버에서 클라이언트에 응답을 보낼 때 전달되는 데이터를 담당하는 스트림
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new webserver.HttpResponse(out);
            String path = getDefaultPath(httpRequest.getPath());
            log.debug("path: {}", path);

            if (path.equals("/user/create")){
                log.info("Request user create service.");
                User user = new User(httpRequest.getParameter("userId"),
                        httpRequest.getParameter("password"),
                        httpRequest.getParameter("name"),
                        httpRequest.getParameter("email"));
                log.debug("USER: {}", user);
                DataBase.addUser(user);

                httpResponse.sendRedirect("/index.html");
         } else if (path.equals("/user/login")){
                log.debug("user login service start");
                User user = DataBase.findUserById(httpRequest.getParameter("userId"));
                log.debug("find user: {}", user);

                if (user != null){
                    if (user.getPassword().equals(httpRequest.getParameter("password"))){
                    // Valid Password
                    log.debug("login validation success");
                    httpResponse.addHeader("Set-Cookie", "logined=true");
                    httpResponse.sendRedirect("/index.html");
                } else{
                    //Invalid Password
                    log.debug("login validation fail");
                    httpResponse.sendRedirect("/user/login_failed.html");
                    }
          } }
            else if (path.equals("/user/list")) {
            if (!isLogin(httpRequest.getHeader("Cookie"))) {
                httpResponse.sendRedirect("user/login.html");
                return;
            }
                Collection<User> users = DataBase.findAll();

                StringBuilder sb = new StringBuilder();
                sb.append("<table border='1'>");
                for (User user : users){
                    log.debug("all users: {}", user);
                    sb.append("<tr>");
                    sb.append("<td> " + user.getUserId() +"</td>");
                    sb.append("<td> " + user.getName() +"</td>");
                    sb.append("<td> " + user.getEmail() +"</td>");
                    sb.append("</tr>");
                }
                sb.append("<table>");
                httpResponse.forwardBody(sb.toString());
                }else{
                httpResponse.forward(path);
            }
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")){
            return "./index.html";
        }
        return path;
    }

    private boolean isLogin(String line) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(line);
        log.debug("cookies: {}", cookies);
        String value = cookies.get("logined");
        log.debug("value: {}", value);

        if (value == null){
            return false;
        }
        return Boolean.parseBoolean(value);
    }
}
