import com.google.common.io.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.DateUtil;
import util.XsltProcessor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Brad on 06.11.2017.
 */
public class Main {

    public static void main(String[] args) throws IOException, TransformerException, URISyntaxException {

        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "\nNo parameters set:\n" +
                    "data/cv.xml data/photo.jpeg");
        }

        URL xmlUrl = Paths.get(args[0]).toFile().toURI().toURL();

        Path photo = Paths.get(args[1]);
        Path photoOut = Paths.get("out/static/pic/photo.jpeg");

        Path mainjs = Paths.get(Resources.getResource("main.js").toURI());
        Path mainjsOut = Paths.get("out/static/js/main.js");

        Files.createDirectories(photoOut.getParent());
        Files.copy(photo,photoOut, REPLACE_EXISTING);
        Files.copy(mainjs,mainjsOut, REPLACE_EXISTING);

        //Пока больше не требуется т.к. данные расчитываются через js
        //String html = transform(updateData(xmlUrl));
        String html = transform(xmlUrl);
        try (Writer writer = Files.newBufferedWriter(Paths.get("out/index.html"))) {
            writer.write(html);
        }
    }

    private static void calculatePeriod(NodeList elements, String... names) {

        for (int i = 0; i < elements.getLength(); i++) {
            Node item = elements.item(i);

            NamedNodeMap attributes = item.getAttributes();
            Date start = DateUtil.toDate(attributes.getNamedItem("start").getTextContent());
            Date end = DateUtil.toDate(attributes.getNamedItem("end").getTextContent());

            for (int j = 0; j < names.length; j++) {
                if ("seniority".equals(names[j]))
                    attributes.getNamedItem("seniority").setTextContent(DateUtil.seniority(start, end));
                else if ("duration".equals(names[j]))
                    attributes.getNamedItem("duration").setTextContent(DateUtil.duration(start, end));
            }

        }

    }

    private static URL updateData(URL xmlUrl) {

        try {

            File file = Paths.get(xmlUrl.toURI()).toFile();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.normalize();

            calculatePeriod(doc.getElementsByTagName("experiences"), "duration");
            calculatePeriod(doc.getElementsByTagName("experience"), "seniority", "duration");


            // Записываем изменения в XML файл
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            xmlUrl = file.toURI().toURL();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return xmlUrl;
    }

    private static String transform(URL xmlUrl) throws IOException, TransformerException {

        URL xslURL = Resources.getResource("cv.xsl");
        try (InputStream xmlStream = xmlUrl.openStream(); InputStream xslStream = xslURL.openStream()) {
            XsltProcessor processor = new XsltProcessor(xslStream);
            return processor.transform(xmlStream);
        }
    }

}
