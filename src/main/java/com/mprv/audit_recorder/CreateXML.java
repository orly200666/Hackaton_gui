package com.mprv.audit_recorder;

import com.mprv.automation.operations.postJelly.eAuditPhaseTwo_parameters;
import com.mprv.automation.services.SecureSphereServices.audit.AuditFilterDO;
import com.mprv.automation.services.SecureSphereServices.audit.AuditRawDO;
import com.mprv.systemtests.securesphere.utils.AuditUtils;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateXML {

    void create_xml_from_json(String jsonFile) throws ParserConfigurationException {

        java.util.List<eAuditPhaseTwo_parameters> eParams = StabilityAuditRawDo.getAllAuditParams();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("queries");
        doc.appendChild(rootElement);



        //...create XML elements, and others...



        HashMap<String, String> map = new HashMap<>();
        map.put("1","a");
        map.put("2","b");

        for (Map.Entry<String, String> entry : map.entrySet()) {

            //query queryType="connect" query="connect to the database: default" database="default"
            //rootElement.appendChild(doc.createElement("query"));

            Element query = doc.createElement("query");
            query.setAttribute("queryType", "2");
            query.setAttribute("query", "3");

            rootElement.appendChild(query);

            Element auditEventVerifications = doc.createElement("auditEventVerifications");
            auditEventVerifications.setAttribute("normalizedQuery", "5");
            query.appendChild(auditEventVerifications);
            //<auditEventVerifications normalizedQuery="">

        }

        String rawQuery;
//        for (AuditRawDO audit : rawData ) {
//
//            //query queryType="connect" query="connect to the database: default" database="default"
//            //rootElement.appendChild(doc.createElement("query"));
//            rawQuery = audit.getFieldValue(eAuditPhaseTwo_parameters.rawQuery);
//
//            Element query = doc.createElement("query");
//            query.setAttribute("queryType", getQueryType(rawQuery));
//            query.setAttribute("query", audit.getFieldValue(eAuditPhaseTwo_parameters.rawQuery));
//
//            String q="connect to the database: default";
//            System.out.println(q.substring(q.indexOf("database:") + 10));
//
//            switch(getQueryType(rawQuery)) {
//                case "connect":
//                    query.setAttribute("database", rawQuery.substring(q.indexOf("database:") + 10));
//                    break;
//                case "disconnect":
//                    query.setAttribute("database", rawQuery.substring(q.indexOf("database:") + 10));
//                    break;
//                default:
//                    query.setAttribute("ignore", "\"false\"");
//            }
//            rootElement.appendChild(query);
//
//            Element auditEventVerifications = doc.createElement("auditEventVerifications");
//            auditEventVerifications.setAttribute("normalizedQuery", audit.getFieldValue(eAuditPhaseTwo_parameters.normalizedQuery));
//            query.appendChild(auditEventVerifications);
//
//            for (eAuditPhaseTwo_parameters e_par : eParams) {
//                Element auditEventVerification = doc.createElement("auditEventVerification");
//                auditEventVerification.setAttribute("searchColumn", "v_" + e_par.name());
//                auditEventVerification.setAttribute("searchValue", audit.getFieldValue(e_par));
//                auditEventVerifications.appendChild(auditEventVerification);
//
//            }
//
//        }


        // write dom document to a file
        try (FileOutputStream output = new FileOutputStream("C:\\Users\\orly.rimer\\Downloads\\staff-dom.xml")) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }


   }

   private static String getQueryType(String rawQuery)
   {
       String firstWordOfQuery = (rawQuery.split(" ")[0]).toLowerCase();
       switch(firstWordOfQuery) {
           case "connect":
               return "connect";
           case "disconnect":
               return "disconnect";
           default:
               return "statement";
       }
   }

    private static void writeXml(Document doc, OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);


//        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//// initialize StreamResult with File object to save to file
//        StreamResult result = new StreamResult(new StringWriter());
//        DOMSource source = new DOMSource(doc);
//        transformer.transform(source, result);
//        String xmlString = result.getWriter().toString();
//        System.out.println(xmlString);

    }
}
