import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlRootElement(name = "cidade")
@XmlType(propOrder = {"nome", "uf", "id","atualizacao"})
public class Cidade {
    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nome")
    private String nome;
    @XmlElement(name = "uf")
    private String uf;
    @XmlElement(name = "atualizacao")
    private Date atualizacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getAtualizacao() {
        return atualizacao;
    }

    public void setAtualizacao(Date atualizacao) {
        this.atualizacao = atualizacao;
    }
}