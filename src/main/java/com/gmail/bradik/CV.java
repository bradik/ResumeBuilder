package com.gmail.bradik;

import com.gmail.bradik.util.DateUtil;
import com.gmail.bradik.util.FileUtils;
import com.gmail.bradik.util.XsltProcessor;
import org.w3c.dom.*;

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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by Brad on 06.11.2017.
 */
public class CV {

    private Path photo;
    private XMLCV xmlcv;

    public CV(String xml, String photo) throws IOException {

        this.xmlcv = new XMLCV(xml);
        this.photo = Paths.get(photo);
    }

    public URL getResource(String name) {
        URL url = getClass().getResource("/" + name);
        //Resources.getResource(name)
        return url;
    }

    private String transform() throws IOException, TransformerException {

        URL xmlUrl = xmlcv.updateData().getURI();

        URL xslURL = getResource("cv.xsl");
        try (InputStream xmlStream = xmlUrl.openStream(); InputStream xslStream = xslURL.openStream()) {
            XsltProcessor processor = new XsltProcessor(xslStream);
            return processor.transform(xmlStream);
        }
    }

    public void build() throws IOException, TransformerException {

        FileUtils.copyFile(photo, Paths.get("out/static/pic/photo.jpeg"));

        String html = transform();
        try (Writer writer = Files.newBufferedWriter(Paths.get("out/index.html"))) {
            writer.write(html);
        }

    }

    public static void main(String[] args) throws IOException, TransformerException {

        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "\nNo parameters set:\n" +
                            "data/cv.xml data/photo.jpeg");
        }

        CV cv = new CV(args[0], args[1]);

        cv.build();

    }

}
