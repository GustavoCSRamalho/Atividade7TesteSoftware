package xml.listaCidades;

import javax.xml.bind.annotation.*;
import java.util.Date;

@XmlRootElement(name = "cidade")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cidade {
    @XmlElement(name = "id")
    public Integer id;
    @XmlElement(name = "nome")
    public String nome;
    @XmlElement(name = "uf")
    public String uf;

    public Date atualizacao;

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