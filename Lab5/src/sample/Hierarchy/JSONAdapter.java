package sample.Hierarchy;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

public class JSONAdapter implements IJSONAdapter {

    private final Legion legion;

    public JSONAdapter(Legion legion) {
        this.legion = legion;
    }

    @Override
    public byte[] toJson() {
        try {
            byte[] xml = legion.xmlSerialize();
            Legion legion = JAXB.unmarshal(new ByteArrayInputStream(xml), Legion.class);

            ObjectMapper objectMapper = new ObjectMapper();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectMapper.writeValue(byteArrayOutputStream, legion);

            return byteArrayOutputStream.toByteArray();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void fromJson(byte[] json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Legion legion = objectMapper.readValue(json, Legion.class);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JAXB.marshal(legion, byteArrayOutputStream);
            legion.xmlDeserialize(new StringReader(byteArrayOutputStream.toString()));
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
