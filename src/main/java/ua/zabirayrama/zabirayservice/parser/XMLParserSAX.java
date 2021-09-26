package ua.zabirayrama.zabirayservice.parser;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import ua.zabirayrama.zabirayservice.domain.LoadCategory;
import ua.zabirayrama.zabirayservice.domain.LoadOffer;
import ua.zabirayrama.zabirayservice.repo.CategoryRepository;
import ua.zabirayrama.zabirayservice.repo.LoadCategoryRepository;
import ua.zabirayrama.zabirayservice.repo.SupplierRepository;

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

  //  public static String urlPath = "https://zabiray.com.ua/index.php?route=extension/feed/yandex_yml&token=rfHdys";
    // public static String urlPathYAV = "https://yavshoke.ua/media/export/dom.xml";
  //   public static String urlPathYAV = "https://yavshoke.ua/media/export/textile_opt_price.xml";

    public static List<LoadOffer> xmlParserSAX(LoadCategoryRepository loadCategoryRepository, SupplierRepository supplierRepository, String urlPath, String codSupp) {



        try (FileWriter fileWriter = new FileWriter("parsingURL.txt")) {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            LoadOfferHandler handler = new LoadOfferHandler(loadCategoryRepository, supplierRepository);
            handler.setCodSupp(codSupp);
            saxParser.parse(connection.getInputStream(), handler);
            //Get Employees list
            List<LoadOffer> loadOfferList = handler.getLoadOfferList();
            //print employee information
            for (LoadOffer loadOffer : loadOfferList) {
          //      System.out.println(offers);
                fileWriter.write(loadOffer.toString() + '\n');
            }
            return loadOfferList;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<LoadCategory> xmlParserSAXCategory(String urlPath) {

     //   String urlPathcat = "https://zabiray.com.ua/index.php?route=extension/feed/yandex_yml&token=rfHdys";


        try (FileWriter fileWriter1 = new FileWriter("categoryURL.txt")) {
            URL urlcat = new URL(urlPath);
            HttpURLConnection connection1 = (HttpURLConnection) urlcat.openConnection();
            SAXParserFactory saxParserFactory1 = SAXParserFactory.newInstance();
            SAXParser saxParser1 = saxParserFactory1.newSAXParser();
            LoadCategoryHandler loadCategoryHandler = new LoadCategoryHandler();
            saxParser1.parse(connection1.getInputStream(), loadCategoryHandler);
            //Get Employees list
            List<LoadCategory> loadCategoryList = loadCategoryHandler.getLoadCategoryList();
            //print employee information
            for (LoadCategory loadCategory : loadCategoryList) {
                System.out.println(loadCategory);
                fileWriter1.write(loadCategory.toString() + '\n');
            }
            return loadCategoryList;
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
