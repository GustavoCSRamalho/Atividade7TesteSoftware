import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "cidades")
@XmlType(propOrder = {"cidade"})
public class Cidades {
    @XmlElement
    private Cidade[] cidade;

    public Cidade[] getCidade() {
        return cidade;
    }

    public void setCidade(Cidade[] cidade) {
        this.cidade = cidade;
    }
}