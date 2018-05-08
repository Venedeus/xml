package learning.java.jaxb;

import javax.xml.bind.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Group createJavaObject(){
        Group group = new Group();
        group.setName("Test Group");

        try {
            group.getMembers().add(createPerson("Alice", "Anderssen", "1970-01-01"));
            group.getMembers().add(createPerson("Bert", "Bobo", "1980-02-02"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return group;
    }

    public Person createPerson(String firstName, String lastName, String birthDate) throws ParseException{
        Person person = new Person();

        person.setBirthDate(format.parse(birthDate));
        person.setFirstName(firstName);
        person.setLastName(lastName);

        return person;
    }

    public void marshall(Object object, OutputStream stream){
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, stream);
        } catch (JAXBException e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, "marshall threw JAXBException.", e);
        }
    }

    public void generateSchema(JAXBContext context) throws IOException {
        SchemaOutputResolver sor = new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                File file = new File(suggestedFileName);
                StreamResult result = new StreamResult(file);
                result.setSystemId(file.toURI().toURL().toString());

                return result;
            }
        };

        context.generateSchema(sor);
    }

    public Object unmarshall(Class clazz, InputStream stream){
        Object object = null;

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = unmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, "marshall threw JAXBException.", e);
        }

        return object;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Application instance = new Application();

        instance.marshall(instance.createJavaObject(), System.out);
        System.out.println(instance.unmarshall(Group.class, new FileInputStream(new File("file.xml"))));
    }
}
