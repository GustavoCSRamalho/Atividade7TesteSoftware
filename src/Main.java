import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    private static Funcoes func;
    private static Boolean roda = true;
    private static String cidade;
    private static String sair = "n";

    public static void main(String[] args) throws Exception {
        func = new Funcoes();
        func.conectar();
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        while(roda){
            System.out.println("Digite o nome de uma cidade : ");
            cidade = scan.readLine();
            System.out.println("Cidade digitada : "+cidade);

//            List<Cidade> cidades = func.selectCidade("select * from tbcidade where uf = '"+cidade+"'");
//            if(cidades.size() == 0){
//                System.out.println("Não exite cidade!");
//            }else{
//                System.out.println("Existe cidades!");
//            }
            String xml = func.getXMLCidade(cidade);
            xml = new String(xml.getBytes(),"UTF-8");
            System.out.println("xml : "+xml);
            Cidade[] cid = func.xmlToObjectCidade(xml);
            for(int i  = 0; i < cid.length;i++){
                func.insertCidade(cid[i]);
            }

            List<Cidade> cidades = func.selectCidade("select * from tbcidade where uf = '"+"SP"+"'");
            System.out.println("Tamanho : "+cidades.size());
            System.out.println("Deseja continuar ? (Y/n)");
            sair = scan.readLine();

//            Cidade[] lista = func.xmlToObjectCidade();
            if(sair.toLowerCase().equals("n")){
                roda = false;
            }
        }




    }
}
