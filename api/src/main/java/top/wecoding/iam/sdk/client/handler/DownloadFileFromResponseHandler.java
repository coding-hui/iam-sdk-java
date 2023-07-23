package top.wecoding.iam.sdk.client.handler;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wecoding Liu
 * @since 0.8
 */
public class DownloadFileFromResponseHandler extends AbstractResponseHandler<File> {

	@Override
	public File convertResult(ClassicHttpResponse response, Type responseType) throws IOException {
		Header contentDispositionHeader = response.getFirstHeader(HttpHeaders.CONTENT_DISPOSITION);
		String contentDisposition = contentDispositionHeader == null ? null : contentDispositionHeader.getValue();
		File file = prepareDownloadFile(contentDisposition);
		Files.copy(response.getEntity().getContent(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return file;
	}

	private File prepareDownloadFile(String contentDisposition) throws IOException {
		String filename = null;
		if (contentDisposition != null && !"".equals(contentDisposition)) {
			Pattern pattern = Pattern.compile("filename=['\"]?([^'\"\\s]+)['\"]?");
			Matcher matcher = pattern.matcher(contentDisposition);
			if (matcher.find()) {
				filename = matcher.group(1);
			}
		}

		String prefix;
		String suffix = null;
		if (filename == null) {
			prefix = "download-";
			suffix = "";
		}
		else {
			int pos = filename.lastIndexOf('.');
			if (pos == -1) {
				prefix = filename + "-";
			}
			else {
				prefix = filename.substring(0, pos) + "-";
				suffix = filename.substring(pos);
			}
			// Files.createTempFile requires the prefix to be at least three characters
			// long
			if (prefix.length() < 3) {
				prefix = "download-";
			}
		}

		return Files.createTempFile(prefix, suffix).toFile();
	}

}
