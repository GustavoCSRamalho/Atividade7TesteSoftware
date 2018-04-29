import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
        if( !bd.exists() ){
            /* cria o arquivo do BD na raiz do projeto e cria uma conexão para o BD */
            conexao = DriverManager.getConnection("jdbc:sqlite:bdprevisao.db");
            /* como o BD não existe então é necessário criar as tabelas */
            createTableCidade();
            createTablePrevisao();
        }
        else{
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

    public boolean createTableCidade() throws SQLException{
        Statement stmt = conexao.createStatement();
//        stmt.execute("PRAGMA encoding='UTF-8';");
        String sql = "create table tbcidade("+
                "id int not null,"+
                "nome varchar(80) not null,"+
                "uf char(2) not null,"+
                "atualizacao date not null"
                +")";

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
        System.out.println(java.sql.Date.valueOf(java.time.LocalDate.now()));
        stmt.setInt(1, cidade.getId() );
        stmt.setString(2, cidade.getNome() );
        stmt.setString(3, cidade.getUf() );
        stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
        stmt.execute();
        stmt.close();
        conexao.commit();
        return true;
    }

    public List<Cidade> selectCidade(String sql) throws SQLException{
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Cidade> lista = new ArrayList<>();
        Cidade cidade;
        while ( rs.next() ) {
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

    public String getXMLCidade(String cidade) throws Exception {
        String charset = java.nio.charset.StandardCharsets.ISO_8859_1.name();
        String linha, resultado = "";
        String urlListaCidade = "http://servicos.cptec.inpe.br/XML/listaCidades?city=%s";
        /* codifica os parâmetros */
        String parametro = String.format(urlListaCidade, URLEncoder.encode(cidade, charset) );
        URL url = new URL(parametro);
        URLConnection conexao = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        while((linha = reader.readLine()) != null){
            resultado += linha;
        }
        return resultado;
    }

    public Cidade[] xmlToObjectCidade(String xml) throws Exception {
        StringReader sr = new StringReader(xml);
//        System.out.println(sr.toString());
        /* a base do XML é uma marcação de nome cidades */
        JAXBContext context = JAXBContext.newInstance(Cidades.class);
        Unmarshaller un = context.createUnmarshaller();
        Cidades cidades = (Cidades) un.unmarshal(sr);
        return cidades.getLista();
    }


}
