

import xml.cidadePrevisao.Cidade;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FuncoeosPrevisao {

    public Cidade getXmlCidadeAndConvertToObjectPrevisao(String cod) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Cidade.class);
        URL url = new URL("http://servicos.cptec.inpe.br/XML/cidade/7dias/" + cod + "/previsao.xml");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream is = http.getInputStream();
        Unmarshaller un = context.createUnmarshaller();
        Cidade cidades = (Cidade) un.unmarshal(is);
        return cidades;
    }
}
