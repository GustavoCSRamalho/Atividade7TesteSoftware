import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xml.listaCidades.Cidade;

import java.sql.Connection;
import java.sql.SQLException;

public class funcaoXMLTest {
    private FuncoesXML funcoesXML;

    @Before
    public void setup(){
        funcoesXML = new FuncoesXML();
    }

    @Test
    public void test1() throws SQLException, ClassNotFoundException {
        Connection connection = funcoesXML.conectar();
        Assert.assertNotNull(connection);
    }

    @Test
    public void test2() throws Exception{
        Cidade[] cidades = funcoesXML.getXmlCidadeAndConvertToObjectCidade("jacarei");
        Assert.assertNotNull(cidades);
    }
    @Test
    public void test3() throws Exception{
        Cidade[] cidades = null;
        try {
            cidades = funcoesXML.getXmlCidadeAndConvertToObjectCidade(null);
        } catch (Exception e) {
            Assert.assertNull(cidades);
        }
    }

    @Test
    public void test4() throws Exception{
        Boolean checar = funcoesXML.createTableCidade();
        Assert.assertTrue("Criou pois nao existe",checar);
    }

    @Test(expected = SQLException.class)
    public void test6() throws Exception{
        Boolean checar = funcoesXML.createTableCidade();
        Assert.assertFalse("Não criou pois existe",checar);
    }
    @Test(expected = SQLException.class)
    public void test7() throws Exception{
        Boolean checar = funcoesXML.createTableCidade();
        Assert.assertFalse("Não criou pois existe",checar);
    }

    @Test
    public void test5() throws Exception{
        Boolean checar = funcoesXML.createTablePrevisao();
        Assert.assertTrue("Criou pois nao existe",checar);
    }

    @Test
    public void test8() throws SQLException{
        Boolean checar =  funcoesXML.cleanPrevisao();
        Assert.assertTrue(checar);
    }

//    @Test
}
