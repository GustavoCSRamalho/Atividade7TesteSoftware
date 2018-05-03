package xml.cidadePrevisao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlRootElement(name = "cidade")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cidade {

    @XmlElement(name = "nome")
    public String nome;
    @XmlElement(name = "uf")
    public String uf;
    @XmlElement(name = "previsao")
    Previsao[] previsao;
    @XmlElement(name = "atualizacao")
    private Date atualizacao;

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

    public Previsao[] getPrevisao() {
        return previsao;
    }

    public void setPrevisao(Previsao[] previsao) {
        this.previsao = previsao;
    }
}
