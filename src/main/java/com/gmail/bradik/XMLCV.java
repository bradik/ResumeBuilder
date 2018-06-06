package com.gmail.bradik;

import com.gmail.bradik.util.DateUtil;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Brad on 06.06.2018.
 */
public class XMLCV {
    private Path sourse;
    private Path tmp;

    private DocumentBuilderFactory docBuilderFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private Map<Pattern, String> patterns;
    private Pattern compileLines;
    private Pattern compileAllText;

    public XMLCV(String path) throws IOException {
        this.sourse = Paths.get(path);
        this.tmp = Files.createTempFile("cv",".xml");
        //this.tmp = Paths.get("sample/cv-1.xml");
    }

    public URL getURI() throws MalformedURLException {

        return tmp.toFile().exists() ? tmp.toUri().toURL() : sourse.toUri().toURL();
    }

    private void calculatePeriod(String tagName, String... names) {

        NodeList elements = doc.getElementsByTagName(tagName);

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

    public void appendXmlFragment(Node parent, String fragment) throws IOException, SAXException {
        Document doc = parent.getOwnerDocument();
        Node fragmentNode = docBuilder.parse(new InputSource(new StringReader(fragment))).getDocumentElement();
        fragmentNode = doc.importNode(fragmentNode, true);
        parent.setTextContent("");
        parent.appendChild(fragmentNode);
    }

    private void formatHTML(Element element) {

        String textContent = element.getTextContent();

        int flags = Pattern.MULTILINE;

        if (patterns == null) {
            patterns = new LinkedHashMap<>();
            patterns.put(Pattern.compile("(\\*([^\\s]*)\\*)", flags), "<strong>$2</strong>");
            patterns.put(Pattern.compile("(^\\s*\\*(.*))", flags), "<li>$2</li>");
        }

        for (Map.Entry<Pattern, String> entry : patterns.entrySet()) {
            Pattern pattern = entry.getKey();
            textContent = pattern.matcher(textContent).replaceAll(entry.getValue());
        }

        if (compileLines == null)
            compileLines = Pattern.compile("<li>|<div>");

        StringBuilder builder = new StringBuilder();
        String[] strings = textContent.split("\\n");
        for (String line : strings) {
            if (line.isEmpty())
                continue;

            if (compileLines.matcher(line).find()) {
                builder.append(line);
            } else
                builder.append("<div>" + line + "</div>");

            builder.append("\n");
        }

        textContent = builder.toString();

        if (compileAllText == null)
            compileAllText = Pattern.compile("<li>", flags);

        if (compileAllText.matcher(textContent).find())
            textContent = "<lu>" + textContent + "</lu>";
        else
            textContent = "<div>" + textContent + "</div>";

        try {
            appendXmlFragment(element, textContent);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void convertText(String tagName) {

        NodeList elements = doc.getElementsByTagName(tagName);

        for (int i = 0; i < elements.getLength(); i++) {
            Node node = elements.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                formatHTML((Element) node);
            }
        }
    }

    public XMLCV updateData() {

        try {

            File file = sourse.toFile();
            File newfile = tmp.toFile();

            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(file);

            doc.normalize();

            //Пока больше не требуется т.к. данные расчитываются через js
            //calculatePeriod("experiences", "duration");
            //calculatePeriod("experience", "seniority", "duration");

            convertText("description");
            convertText("qualitys");

            // Записываем изменения в XML файл
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(newfile);
            transformer.transform(source, result);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            docBuilderFactory = null;
            docBuilder = null;
            doc = null;
        }

        return this;
    }

}
