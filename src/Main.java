import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.EnderecoNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.Exception.NomeNaoPodeSerNuloException;
import br.ufal.ic.p2.wepayu.Exception.SalarioNaoPodeSerNuloException;
import easyaccept.EasyAccept;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, NomeNaoPodeSerNuloException, EmpregadoNaoExisteException, EnderecoNaoPodeSerNuloException, SalarioNaoPodeSerNuloException {
        String facade = "br.ufal.ic.p2.wepayu.Facade";
        //Serialize
        /*Empregado e1 = new Empregado("1", "José Matheus Santana", "aaa", "bbb", "1,2");
        Empregado e2 = new Empregado("2", "José Matheus Santana2", "aaa", "bbb", "1,2");
        Empregado e3 = new Empregado("3", "José Matheus Santana3", "aaa", "bbb", "1,2");
        Empregado e4 = new Empregado("3", "José Matheus Santana4", "aaa", "bbb", "1,2");
        ListaEmpregados lista = new ListaEmpregados();
        lista.add(e1);
        lista.add(e2);
        lista.add(e3);
        lista.add(e4);
        FileOutputStream fos = new FileOutputStream("XMLFiles\\empregados.xml");
        XMLEncoder encoder = new XMLEncoder(fos);
        encoder.writeObject(lista);
        encoder.close();
        fos.close();*/


        //Deserialize
        /*FileInputStream fis = null;
        try {
            fis = new FileInputStream("XMLFiles\\empregados.xml");
        }
        catch (FileNotFoundException exception)
        {
            File file = new File("XMLFiles\\empregados.xml");
            file.createNewFile();
        } finally {
            fis = new FileInputStream("XMLFiles\\empregados.xml");
        }
        XMLDecoder decoder = new XMLDecoder(fis);
        ListaEmpregados list = null;
        try{

            list = (ListaEmpregados) decoder.readObject();

        } catch (ArrayIndexOutOfBoundsException exception)
        {
            list = new ListaEmpregados();
        }
        finally {
            decoder.close();
            fis.close();
        }


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.getList().get(i).getNome());
        }
        System.out.println("Chega aqui");
*/
        //EasyAccept.main(new String[]{facade, "tests/us1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us1_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us2.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us2_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us3.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us3_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us4.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us4_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us5.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us5_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us6.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us6_1.txt"});
        //EasyAccept.main(new String[]{facade, "tests/us7.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us8.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us9.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us9_1.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us10.txt"});
//        EasyAccept.main(new String[]{facade, "tests/us10_1.txt"});
    }
}


