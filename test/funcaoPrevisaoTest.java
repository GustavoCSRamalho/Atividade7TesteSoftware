import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class funcaoPrevisaoTest {
    private FuncoeosPrevisao funcoeosPrevisao;
    @Before
    public void setup(){
        funcoeosPrevisao = new FuncoeosPrevisao();
    }
    @Test
    public void test1() throws Exception{
        xml.cidadePrevisao.Cidade cid = funcoeosPrevisao.getXmlCidadeAndConvertToObjectPrevisao("2680");
        Assert.assertNotNull("Teste getXmlCidadePrevisao",cid);
    }
    @Test(expected = Exception.class)
    public void test2() throws Exception {
        xml.cidadePrevisao.Cidade cid = funcoeosPrevisao.getXmlCidadeAndConvertToObjectPrevisao(null);
    }



}
