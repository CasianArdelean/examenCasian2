package com.example.dm2.casianExamenProm;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Xml {

    private URL rssUrl;

    public Xml(String url){
        try {
            this.rssUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(){
        try {
            return rssUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> returnTimeTempSky(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<String> horas = new ArrayList<String>();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(this.getInputStream());
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName("pronostico_horas");
            Node item = items.item(0);
            NodeList horasItems = item.getChildNodes();
            Node hora = horasItems.item(0);
            NodeList itemHora = hora.getChildNodes();
            for(int i=0;i<itemHora.getLength();i++){
                String tag = itemHora.item(i).getNodeName();
                if (tag.equals("hora_datos"))
                    horas.add(getNodeText(itemHora.item(i)));
                else if (tag.equals("temperatura"))
                    horas.add(getNodeText(itemHora.item(i)));
                else if (tag.equals("texto"))
                    horas.add(getNodeText(itemHora.item(i)));
            }
            Log.d("XMLParser",horas.toString());
            return horas;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getNodeText(Node dato){
        StringBuilder texto = new StringBuilder();
        NodeList fragmetnso = dato.getChildNodes();
        for (int i=0;i<fragmetnso.getLength();i++){
            texto.append(fragmetnso.item(i).getNodeValue());
        }
        return texto.toString();
    }

}
