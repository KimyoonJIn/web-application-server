import org.junit.Test;
import webserver.HttpRequest;

import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class HttpRequestTest {
	private String testDir = "./src/test/resources/";

	@Test
	public void request_GET() throws IOException {
		InputStream in = new FileInputStream(new File(testDir + "Http_GET.txt"));
		HttpRequest httpRequest = new HttpRequest(in);

		assertEquals("GET", httpRequest.getMethod());
		assertEquals("/user/create", httpRequest.getPath());
		assertEquals("keep-alive", httpRequest.getHeader("Connection"));
		assertEquals("javajigi", httpRequest.getParameter("userId"));
	}

	@Test
	public void request_POST() throws IOException {
		InputStream in = new FileInputStream(new File(testDir + "Http_POST.txt"));
		HttpRequest httpRequest = new HttpRequest(in);

		assertEquals("POST", httpRequest.getMethod());
		assertEquals("/user/create", httpRequest.getPath());
		assertEquals("keep-alive", httpRequest.getHeader("Connection"));
		assertEquals("javajigi", httpRequest.getParameter("userId"));
	}
}
