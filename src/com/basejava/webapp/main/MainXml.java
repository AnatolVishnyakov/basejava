package com.basejava.webapp.main;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Item {
    private String date;
    private String mode;
    private String unit;
    private String current;
    private String interactive;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getCurrent() {
        return current;
    }
    public void setCurrent(String current) {
        this.current = current;
    }
    public String getInteractive() {
        return interactive;
    }
    public void setInteractive(String interactive) {
        this.interactive = interactive;
    }

    @Override
    public String toString() {
        return "Item [current=" + current + ", date=" + date + ", interactive="
                + interactive + ", mode=" + mode + ", unit=" + unit + "]";
    }
}

class StAXParser {
    static final String DATE = "date";
    static final String ITEM = "item";
    static final String MODE = "mode";
    static final String UNIT = "unit";
    static final String CURRENT = "current";
    static final String INTERACTIVE = "interactive";

    @SuppressWarnings({ "unchecked", "null" })
    public List<Item> readConfig(String configFile) {
        List<Item> items = new ArrayList<Item>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Item item = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(ITEM)) {
                        item = new Item();
                        // We read the attributes from this tag and add the date
                        // attribute to our object
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(DATE)) {
                                item.setDate(attribute.getValue());
                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(MODE)) {
                            event = eventReader.nextEvent();
                            item.setMode(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(UNIT)) {
                        event = eventReader.nextEvent();
                        item.setUnit(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(CURRENT)) {
                        event = eventReader.nextEvent();
                        item.setCurrent(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(INTERACTIVE)) {
                        event = eventReader.nextEvent();
                        item.setInteractive(event.asCharacters().getData());
                        continue;
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ITEM)) {
                        items.add(item);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}

class StAXWriter {
    private String configFile;

    public void setFile(String configFile) {
        this.configFile = configFile;
    }

    public void saveConfig() throws Exception {
        // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(configFile));
        // create an EventFactory
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);

        // create config open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", "config");
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        createNode(eventWriter, "mode", "1");
        createNode(eventWriter, "unit", "901");
        createNode(eventWriter, "current", "0");
        createNode(eventWriter, "interactive", "0");

        eventWriter.add(eventFactory.createEndElement("", "", "config"));
        eventWriter.add(end);
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.close();
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);

    }

}

public class MainXml {
    public static void main(String[] args) {
        // READ
        StAXParser read = new StAXParser();
        List<Item> readConfig = read.readConfig("D:\\IdeaProjects\\basejava\\storage\\config.xml");
        for (Item item : readConfig) {
            System.out.println(item);
        }

        // WRITE
        StAXWriter configFile = new StAXWriter();
        configFile.setFile("D:\\IdeaProjects\\basejava\\storage\\config2.xml");
        try {
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
