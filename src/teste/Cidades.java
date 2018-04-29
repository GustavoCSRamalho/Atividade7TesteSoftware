package teste;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cidades")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cidades {

    @XmlElement(name = "cidade")
    Cidade[] listaCidade;

    public Cidade[] getLista() {
        return listaCidade;
    }

    public void setLista(Cidade[] lista) {
        this.listaCidade = lista;
    }
}