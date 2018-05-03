package xml.listaCidades;

import javax.xml.bind.annotation.*;

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