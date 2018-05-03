import sun.java2d.pipe.SpanShapeRenderer;
import xml.cidadePrevisao.Cidade;
import xml.cidadePrevisao.Previsao;

import java.text.SimpleDateFormat;
import java.util.List;

public class Teste {
    public static void main(String[] args) throws Exception {
//    FuncoeosPrevisao func = new FuncoeosPrevisao();
    FuncoesXML func = new FuncoesXML();
    func.conectar();
    List<Previsao> lista = func.selectPrevisao("2680");

    }
}
