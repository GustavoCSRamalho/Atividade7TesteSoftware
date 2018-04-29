package teste;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Teste {

    public static void main(String[] args) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Cidades.class);
        URL url = new URL("http://servicos.cptec.inpe.br/XML/listaCidades?city=Jacare");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream is = http.getInputStream();
        Unmarshaller un = context.createUnmarshaller();
        Cidades cidades = (Cidades) un.unmarshal(is);
        Cidade[] lista = cidades.getLista();
        for(int i = 0; i < lista.length;i++){
            System.out.println("Nome : "+lista[i].getNome());
        }

    }
}
