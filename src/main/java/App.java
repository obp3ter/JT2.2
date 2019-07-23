import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.*;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws FileNotFoundException, IOException, DocumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        writeJson();
//        readJson();
//        writeXml();
//        readXml();
        reflect();
    }
    private static void writeJson() throws FileNotFoundException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Peter");
        jsonObject.put("lastName", "Oprea-Benko");
        Map<String, String> map = new HashMap<>();
        map.put("Street", "Croitorilor");
        map.put("Number", "12-14");
        map.put("City", "Cluj-Napoca");
        jsonObject.put("Address", map);

        JSONArray jsonArray = new JSONArray();
        map = new HashMap<>();
        map.put("Type", "Home");
        map.put("Number", "0740003023");
        jsonArray.put(map);
        map = new HashMap<>();
        map.put("Type", "Work");
        map.put("Number", "0266456852");
        jsonArray.put(map);
        jsonObject.put("Phone numbers", jsonArray);

//        System.out.println(jsonObject);
        PrintWriter pw =new PrintWriter("jsonexample.json");

        pw.print(jsonObject.toString());
        pw.flush();
        pw.close();
    }
    private static void readJson() throws FileNotFoundException {
        FileReader fl = new FileReader("jsonexample.json");
        JSONObject jsonObject = new JSONObject(new JSONTokener(fl));
        JSONArray phone_numbers = jsonObject.getJSONArray("Phone numbers");
        Iterator<Object> iterator = phone_numbers.iterator();
        while (iterator.hasNext())
        {
            Object next = iterator.next();
            System.out.println(next.toString());
        }



    }

    private static void readXml() throws DocumentException {
//        FileReader fl = new FileReader("xmlexample.xml");
        SAXReader reader= new SAXReader();
        Document document = reader.read(new File("xmlexample.xml"));
        System.out.println(document.getRootElement().getName());
        document.getRootElement().elements().forEach(s-> System.out.println(((Element) s).getName()));

    }
    private static void writeXml() throws IOException {
        Document doc = DocumentHelper.createDocument();
//        "xmlexample.xml"
        Element root = doc.addElement("XMLExample");

        Element child= root.addElement("Person").addAttribute("firstName","Peter");
        child.addAttribute("lastName","Oprea-Benko");
        child.addElement("Address").addText(" Croitorilor 12-14");




        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        FileWriter fw = new FileWriter(new File("xmlexample.xml"));
        XMLWriter xmlWriter= new XMLWriter(fw,outputFormat);

        xmlWriter.write(doc);
        xmlWriter.flush();
        xmlWriter.close();
    }

    private static void reflect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class myclass= JSONObject.class;
//        System.out.println(myclass.getName());
//        System.out.println(Modifier.toString(myclass.getModifiers()));
//        System.out.println(myclass.getSuperclass());
        List<Constructor> cl = Arrays.asList(myclass.getConstructors());
//        cl.forEach(s -> System.out.println(s));
        Constructor constructor = myclass.getConstructor(JSONObject.class, String[].class);
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("firstName", "Peter");
        JSONObject instance =(JSONObject) constructor.newInstance(jsonObject, JSONObject.getNames(jsonObject));
        System.out.println(instance.get("firstName"));
        List<Method> ml = Arrays.asList(myclass.getMethods());

//        ml.forEach(s-> System.out.println(s));

        Method method = myclass.getMethod("length");

//        System.out.println(method.getReturnType());

        System.out.println(method.invoke(jsonObject));

        List<Field> fieldList =  Arrays.asList(myclass.getDeclaredFields());
        fieldList.forEach(s -> System.out.println(s));


        }

    }

