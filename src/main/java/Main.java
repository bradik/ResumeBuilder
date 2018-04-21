import com.google.common.io.Resources;
import util.XsltProcessor;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Brad on 06.11.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException, TransformerException {
        URL xmlUrl = Resources.getResource("cv.xml");
        String html = transform(xmlUrl);
        try (Writer writer = Files.newBufferedWriter(Paths.get("out/index.html"))) {
            writer.write(html);
        }
    }

    private static String transform(URL xmlUrl) throws IOException, TransformerException {

        URL xslURL = Resources.getResource("cv.xsl");
        try (InputStream xmlStream = xmlUrl.openStream(); InputStream xslStream = xslURL.openStream()) {
            XsltProcessor processor = new XsltProcessor(xslStream);
            return processor.transform(xmlStream);
        }
    }

}
