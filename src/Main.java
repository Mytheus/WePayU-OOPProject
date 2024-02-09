import br.ufal.ic.p2.wepayu.Exception.AtributoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.IdEmpregadoNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.utils.Persistence;
import easyaccept.EasyAccept;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws EmpregadoNaoExisteException, IdEmpregadoNaoPodeSerNuloException, AtributoNaoExisteException, IOException {
        String facade = "br.ufal.ic.p2.wepayu.Facade";
        EasyAccept.main(new String[]{facade, "tests/us1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us1_1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us2.txt"});
        EasyAccept.main(new String[]{facade, "tests/us2_1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us3.txt"});
        EasyAccept.main(new String[]{facade, "tests/us3_1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us4.txt"});
        EasyAccept.main(new String[]{facade, "tests/us4_1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us5.txt"});
        EasyAccept.main(new String[]{facade, "tests/us5_1.txt"});
        EasyAccept.main(new String[]{facade, "tests/us6.txt"});
        EasyAccept.main(new String[]{facade, "tests/us6_1.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us7.txt"});
/*        Persistence persistence = new Persistence("folha-2005-01-07.txt");
        persistence.createFile();
        persistence.readWriteToFile("folhaPagamentoCabecalhos\\horista.txt");*/
//        EasyAccept.main(new String[]{facade, "tests/us8.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us9.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us9_1.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us10.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us10_1.txt"});
    }
}


