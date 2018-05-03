import xml.cidadePrevisao.Previsao;
import xml.listaCidades.Cidade;
import xml.listaCidades.Cidades;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FuncoesXML {
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
        conexao.commit();
        return true;

    }
    public boolean updateAtualizacaoCidade(String id) throws SQLException {

        Statement stmt = conexao.createStatement();
        Date data = java.sql.Date.valueOf(java.time.LocalDate.now());
        String sql = "update tbcidade set atualizacao = '"+data.getTime()+"' where id = "+id+";";
        stmt.executeUpdate(sql);
        stmt.close();
        conexao.commit();
        return true;

    }



    public boolean insertCidade(Cidade cidade) throws SQLException, ParseException {
        /* o campo atualizacao irá receber o valor padrão, ou seja, null */
        String sql = "insert  into tbcidade(id,nome,uf,atualizacao) values(?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        String novoNome = Normalizer.normalize(cidade.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

//        System.out.println(java.sql.Date.valueOf(java.time.LocalDate.now()));

//        Date data = ;

        stmt.setInt(1, cidade.getId());
        stmt.setString(2, novoNome.toLowerCase());
        stmt.setString(3, cidade.getUf());
        stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
        stmt.execute();
        stmt.close();
        conexao.commit();
        return true;
    }

    public boolean insertPrevisao(xml.cidadePrevisao.Previsao previsao, int id) throws SQLException{
        /* o campo atualizacao irá receber o valor padrão, ou seja, null */
        String sql = "insert  into tbprevisao(id,dia,tempo,minima,maxima,iuv) values(?,?,?,?,?,?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
//        String novoNome = Normalizer.normalize(cidade.getNome(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

//        System.out.println(java.sql.Date.valueOf(java.time.LocalDate.now()));
        Date date = new Date(previsao.getDia().getTime());
        stmt.setInt(1, id);
        stmt.setDate(2, date);
        stmt.setString(3, previsao.getTempo());
        stmt.setFloat(4, Float.parseFloat(previsao.getMinima()));
        stmt.setFloat(5,Float.parseFloat(previsao.getMaxima()));
        stmt.setString(6, previsao.getIuv());
        stmt.execute();
        stmt.close();
        conexao.commit();
        return true;
    }

    public List<Cidade> selectCidade(String nome) throws SQLException {
        Statement stmt = conexao.createStatement();
        String sql = "select * from tbcidade where nome = '" + nome + "';";
        ResultSet rs = stmt.executeQuery(sql);
        List<Cidade> lista = new ArrayList<>();
        Cidade cidade;
        while (rs.next()) {
            cidade = new Cidade();
            cidade.setId(rs.getInt("id"));
            cidade.setNome(rs.getString("nome"));
            cidade.setUf(rs.getString("uf"));
            cidade.setAtualizacao(rs.getDate("atualizacao"));// troquei o getString pelo get Date, pois
            //a variavel do bando é do tipo DATE nao STRING
            lista.add(cidade);
        }
        rs.close();
        stmt.close();
        conexao.commit();
        return lista;
    }
    public List<Previsao> selectPrevisao(String cod) throws SQLException {
        Statement stmt = conexao.createStatement();
        String sql = "select * from tbprevisao where id = '" + cod+ "';";
        ResultSet rs = stmt.executeQuery(sql);
        List<Previsao> lista = new ArrayList<>();
        xml.cidadePrevisao.Previsao previsao;
        while (rs.next()) {
            previsao = new xml.cidadePrevisao.Previsao();
            previsao.setId(rs.getInt("id"));
            previsao.setDia(rs.getDate("dia"));
            previsao.setTempo(rs.getString("tempo"));
            previsao.setMinima(rs.getString("minima"));// troquei o getString pelo get Date, pois
            previsao.setMaxima(rs.getString("maxima"));
            previsao.setIuv(rs.getString("iuv"));

            //a variavel do bando é do tipo DATE nao STRING
            lista.add(previsao);
        }
        rs.close();
        stmt.close();
        conexao.commit();
        return lista;
    }


    public Cidade[] getXmlCidadeAndConvertToObjectCidade(String cidade) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Cidades.class);
        URL url = new URL("http://servicos.cptec.inpe.br/XML/listaCidades?city=" + cidade);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.addRequestProperty("User-Agent", "Mozilla/4.76");
        InputStream is = http.getInputStream();
        Unmarshaller un = context.createUnmarshaller();
        Cidades cidades = (Cidades) un.unmarshal(is);
        return cidades.getLista();
    }


}
