package ua.zabirayrama.zabirayservice.parser;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import ua.zabirayrama.zabirayservice.domain.Category;
import ua.zabirayrama.zabirayservice.domain.Offer;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class XMLParserSAX {
    public static List<Offer> xmlParserSAX() {

        String urlPath = "http://sat-ellite.net/yandex_market.xml?hash_tag=73151c839448b47c3b219d818efa07fb&sales_notes=&product_ids=&label_ids=&exclude_fields=&html_description=1&yandex_cpa=&process_presence_sure=&group_ids=6445166%2C6445163%2C44720119%2C6445170%2C6445167%2C6445169%2C6445178%2C6157855&nested_group_ids=44720119%2C6157855";

        try (FileWriter fileWriter = new FileWriter("parsingURL.txt")) {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            OfferHandler handler = new OfferHandler();
            saxParser.parse(connection.getInputStream(), handler);
            //Get Employees list
            List<Offer> offersList = handler.getOffersList();
            //print employee information
            for (Offer offers : offersList) {
                System.out.println(offers);
                fileWriter.write(offers.toString() + '\n');
            }
            return offersList;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Category> xmlParserSAXCategory() {

        String urlPathcat = "http://sat-ellite.net/yandex_market.xml?hash_tag=73151c839448b47c3b219d818efa07fb&sales_notes=&product_ids=&label_ids=&exclude_fields=&html_description=1&yandex_cpa=&process_presence_sure=&group_ids=6445166%2C6445163%2C44720119%2C6445170%2C6445167%2C6445169%2C6445178%2C6157855&nested_group_ids=44720119%2C6157855";


        try (FileWriter fileWriter1 = new FileWriter("categoryURL.txt")) {
            URL urlcat = new URL(urlPathcat);
            HttpURLConnection connection1 = (HttpURLConnection) urlcat.openConnection();
            SAXParserFactory saxParserFactory1 = SAXParserFactory.newInstance();
            SAXParser saxParser1 = saxParserFactory1.newSAXParser();
            CategoryHandler categoryHandler = new CategoryHandler();
            saxParser1.parse(connection1.getInputStream(), categoryHandler);
            //Get Employees list
            List<Category> categories = categoryHandler.getCategoryList();
            //print employee information
            for (Category category : categories) {
                System.out.println(category);
                fileWriter1.write(category.toString() + '\n');
            }
            return categories;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

/*
    //для теста пробуем обработать Offer
    public static void main(String[] args) {

        String urlPath = "http://sat-ellite.net/yandex_market.xml?hash_tag=73151c839448b47c3b219d818efa07fb&sales_notes=&product_ids=&label_ids=&exclude_fields=&html_description=1&yandex_cpa=&process_presence_sure=&group_ids=6445166%2C6445163%2C44720119%2C6445170%2C6445167%2C6445169%2C6445178%2C6157855&nested_group_ids=44720119%2C6157855";

        try (FileWriter fileWriter = new FileWriter("parsingURL.txt")) {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(connection.getInputStream(), handler);
            //Get Employees list
            List<Offer> offersList = handler.getOffersList();
            //print employee information
            for(Offer offers : offersList) {
                System.out.println(offers);
                fileWriter.write(offers.toString()+'\n');
            }
            connection.disconnect();
            } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try (FileWriter fileWriter = new FileWriter("parsing.txt")){
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            File file = new File("c:\\1\\kievopt.xml");
            System.out.println(""+file.getAbsolutePath());
            System.out.println("---------------------");
            saxParser.parse(file, handler);
            //Get Employees list
            List<Offer> offersList = handler.getOffersList();
            //print employee information
            for(Offer offers : offersList) {
                System.out.println(offers);
                fileWriter.write(offers.toString()+'\n');
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
*/

}
