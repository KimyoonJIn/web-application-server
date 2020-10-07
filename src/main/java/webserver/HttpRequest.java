package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private String method;
	private String path;
	private Map<String, String> params  = new HashMap<String, String>();
	private Map<String, String> headers = new HashMap<String, String>();


	public HttpRequest(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		String line  = br.readLine();
		if (line==null){
			return;
		}
		log.debug("line: {}", line);

		String[] processedLine = line.split("\\s+");

		method = processedLine[0];

		while(!"".equals(line)){
			line = br.readLine();
			String[] headerTokens = line.split(": ");
			if (headerTokens.length == 2) {
				headers.put(headerTokens[0], headerTokens[1]);
			}
		}

		log.debug("headers: {}", headers);

		if  (HttpMethod.POST.toString().equals(method)){
			path = processedLine[1];
			log.debug("path: {}", path);

			String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
			params = HttpRequestUtils.parseQueryString(body);
			log.debug("POST params: {}", params);
		} else {
			// GET
			log.debug("GET start");
			int index = processedLine[1].indexOf("?");
			if (index == -1){
				path = processedLine[1];
      } else {
        path = processedLine[1].substring(0, index);
        log.debug("path: {}", path);
        params = HttpRequestUtils.parseQueryString(processedLine[1].substring(index + 1));
        log.debug("GET params: {}", params);
			}
		}
	}

	public String getMethod(){
		return method;
	}

	public String getPath(){
		return path;
	}

	public String getHeader(String fieldName){
		return headers.get(fieldName);
	}

	public String getParameter(String key){
		log.debug("params: {}", params);
		return params.get(key);
	}
}
