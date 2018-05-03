import java.sql.Date;
import java.time.LocalDate;

public class Main {

    //    private static FuncoesXML func;
    public static Boolean roda = true;
    public static String sair = "n";
    private static FuncoesMain func;


    public static void main(String[] args) throws Exception {
        func = new FuncoesMain();
        func.connectDB();

        while (roda) {

            func.readInputData();
            func.fillListCidades();

            func.findCidadeInList();

            Date data = Date.valueOf(LocalDate.now());

//            Date dataT = new Date(Long.parseLong(func.getCidadeBD().getAtualizacao()));
            Date dataT = (Date) func.getCidadeBD().getAtualizacao();

            if (data.toString().equals(dataT.toString())) {
                System.out.println("Ver previsão!");

                func.getAndInsertPrevisao();
                func.fillListPrevisao();
                func.imprimePrevisao();
            } else {
                System.out.println("Deu ruim!");
                func.updateAtualizacao();
                func.dropPrevisa();
                func.getAndInsertPrevisao();
                func.fillListPrevisao();
                func.imprimePrevisao();
            }
            if (func.exit()) {
                roda = false;
            }

        }


    }


}
