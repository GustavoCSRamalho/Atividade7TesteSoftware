

import sun.java2d.pipe.SpanShapeRenderer;
import xml.cidadePrevisao.Previsao;
import xml.listaCidades.Cidade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

public class FuncoesMain {


    private static Cidade cidadeBD;
    private static Cidade[] cid = null;
    private static List<Cidade> cidadesLista = null;
    private static List<Previsao> previsaoLista = null;
    private static String cidade;
    private static FuncoesXML func;
    private static FuncoeosPrevisao prev;
    private static BufferedReader scan;

    public FuncoesMain() {
        func = new FuncoesXML();
        scan = new BufferedReader(new InputStreamReader(System.in));
        prev = new FuncoeosPrevisao();
    }

    public static Cidade getCidadeBD() {
        return cidadeBD;
    }

    public static void setCidadeBD(Cidade cidadeBD) {
        FuncoesMain.cidadeBD = cidadeBD;
    }

    void connectDB() {
        try {
            func.conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    boolean exit() {
        System.out.println("Deseja continuar ? (Y/n)");
        String sair = null;
        try {
            sair = scan.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            Cidade[] lista = func.xmlToObjectCidade();
        if (sair.toLowerCase().equals("n")) {
            return false;
        }
        return true;
    }

    void findCidadeInList() {
        for (int i = 0; i < cidadesLista.size(); i++) {
            if (cidadesLista.get(i).getNome().equals(cidade)) {
                cidadeBD = cidadesLista.get(i);
            }
        }
    }

//    void findPrevisaoInList(){
//        for(int i = 0; i < previsaoLista.size();i++){
//            if(previsaoLista.get(i).getId() == 2860){}
//        }
//    }

    void fillListCidades() throws Exception {
        if (cidadesLista == null) {
            cidadesLista = func.selectCidade(cidade);
            if (cidadesLista.size() == 0) {
                getAndInsertData();
            }
        } else {
            List<Cidade> verCidade = func.selectCidade(cidade);
            if (verCidade.size() == 0) {
                getAndInsertData();
            }
        }
    }

    void fillListPrevisao() throws Exception{
        String id = cidadeBD.getId().toString();
        if(previsaoLista == null){
            previsaoLista = func.selectPrevisao(id);
            if(previsaoLista.size() == 0){
                getAndInsertPrevisao();
            }
        }else{
            List<Previsao> prevLista = func.selectPrevisao(id);
            if(prevLista.size() == 0){
                getAndInsertPrevisao();
            }
        }
    }

    void readInputData() throws IOException {
        System.out.println("Digite o nome de uma cidade : ");
        cidade = scan.readLine();
        cidade = Normalizer.normalize(cidade, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        System.out.println("Cidade digitada : " + cidade);
    }

    void getAndInsertData() throws Exception {
        cid = func.getXmlCidadeAndConvertToObjectCidade(cidade);
        for (int i = 0; i < cid.length; i++) {
            func.insertCidade(cid[i]);
        }
        cidadesLista = func.selectCidade(cidade);
    }

    void getAndInsertPrevisao() throws Exception {
        xml.cidadePrevisao.Cidade cidade = prev.getXmlCidadeAndConvertToObjectPrevisao(cidadeBD.getId().toString());
        xml.cidadePrevisao.Previsao[] previsao = cidade.getPrevisao();
        for(int i =0;i < previsao.length;i++){
            func.insertPrevisao(previsao[i],cidadeBD.id);
        }

    }

    void imprimePrevisao(){
        for(int i  = 0;i < previsaoLista.size();i++){
            Previsao prev = previsaoLista.get(i);
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String d = df.format(prev.getDia());
            System.out.println(d+" | "+prev.getTempo()+" | "+prev.getIuv()+" | "+prev.getMinima()+" | "+prev.getMaxima());
        }
    }

    void dropPrevisa() throws  Exception{
        func.cleanPrevisao();
    }

    void updateAtualizacao() throws  Exception{
        func.updateAtualizacaoCidade(cidadeBD.getId().toString());
    }
}
