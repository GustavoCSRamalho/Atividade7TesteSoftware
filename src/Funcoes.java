import teste.Cidade;
import teste.Cidades;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Funcoes {
    private static Connection conexao = null;

    public Connection conectar() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        File bd = new File("bdprevisao.db");
        /* verifica se o arquivo do BD existe na raiz do projeto */
        if (!bd.exists()) {
            /* cria o arquivo do BD na raiz do projeto e cria uma conexão para o BD */
            conexao = DriverManager.getConnection("jdbc:sqlite:bdprevisao.db");
            /* como o BD não existe então é necessário criar as tabelas */
            createTableCidade();
            createTablePrevisao();
        } else {
            /* cria uma conexão com o BD */
            conexao = DriverManager.getConnection("jdbc:sqlite:bdprevisao.db");
        }
        conexao.setAutoCommit(false);
        return conexao;
    }

    public boolean createTablePrevisao() throws SQLException {
        Statement stmt = conexao.createStatement();
        String sql = "create table tbprevisao( " +
                "id int not null," +
                "dia date not null," +
                "tempo char(3) not null," +
                "minima float not null," +
                "maxima float not null," +
                "iuv float not null," +
                "primary key (id, dia)," +
                "foreign key (id) references tbcidade(id) " +
                ")";
        stmt.executeUpdate(sql);
        stmt.close();
        return true;
    }

    public boolean createTableCidade() throws SQLException {
        Statement stmt = conexao.createStatement();
//        stmt.execute("PRAGMA encoding='UTF-8';");
        String sql = "create table tbcidade(" +
                "id int not null," +
                "nome varchar(80) not null," +
                "uf char(2) not null," +
                "atualizacao date not null"
                + ")";

        stmt.executeUpdate(sql);
        stmt.close();
        return true;
    }

    public boolean cleanPrevisao() throws SQLException {

        Statement stmt = conexao.createStatement();
        String sql = "delete from tbprevisao;";
        stmt.executeUpdate(sql);
        stmt.close();
        return true;

    }


    public boolean insertCidade(Cidade cidade) throws SQLException, ParseException {
        /* o campo atualizacao irá receber o valor padrão, ou seja, null */
        String sql = "insert  into tbcidade(id,nome,uf,atualizacao) values(?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        Date d = new Date();
//        stmt.execute("PRAGMA encoding=\"UTF-8\"");
//        String nfdNormalizedString = Normalizer.normalize(cidade.getNome(), Normalizer.Form.NFD);
//        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        pattern.matcher(nfdNormalizedString).replaceAll("");

//        System.out.println("Novo : "+ nf);


        System.out.println(java.sql.Date.valueOf(java.time.LocalDate.now()));
        stmt.setInt(1, cidade.getId());
        stmt.setString(2, cidade.getNome());
        stmt.setString(3, cidade.getUf());
        stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
        stmt.execute();
        stmt.close();
        conexao.commit();
        return true;
    }

    public List<Cidade> selectCidade(String nome) throws SQLException {
        Statement stmt = conexao.createStatement();
        String sql = "select * from tbcidade whene nome = '" + nome + "'";
        ResultSet rs = stmt.executeQuery(sql);
        List<Cidade> lista = new ArrayList<>();
        Cidade cidade;
        while (rs.next()) {
            cidade = new Cidade();
            cidade.setId(rs.getInt("id"));
            cidade.setNome(rs.getString("nome"));
            cidade.setUf(rs.getString("uf"));
            cidade.setAtualizacao(rs.getString("atualizacao"));// troquei o getString pelo get Date, pois
            //a variavel do bando é do tipo DATE nao STRING
            lista.add(cidade);
        }
        rs.close();
        stmt.close();
        conexao.commit();
        return lista;
    }

//    public String getXMLCidade(String cidade) throws Exception {
//        String charset = StandardCharsets.UTF_8.name();
//        String linha, resultado = "";
//        String urlListaCidade = "http://servicos.cptec.inpe.br/XML/listaCidades?city=%s";
//        /* codifica os parâmetros */
//        String parametro = String.format(urlListaCidade, URLEncoder.encode(cidade, charset) );
//        URL url = new URL(parametro);
//        URLConnection conexao = url.openConnection();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
//        while((linha = reader.readLine()) != null){
//            resultado += linha;
//        }
//        return resultado;
//    }

    public Cidade[] getXmlCidadeAndConvertToObjectCidade(String cidade) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Cidades.class);
        URL url = new URL("http://servicos.cptec.inpe.br/XML/listaCidades?city=" + cidade + ";");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream is = http.getInputStream();
        Unmarshaller un = context.createUnmarshaller();
        Cidades cidades = (Cidades) un.unmarshal(is);
//        Cidade[] lista = cidades.getLista();
        return cidades.getLista();
    }


}
