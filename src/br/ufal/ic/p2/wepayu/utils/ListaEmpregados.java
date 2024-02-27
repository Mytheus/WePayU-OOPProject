package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.HaOutroEmpregadoIdMembroException;
import br.ufal.ic.p2.wepayu.Exception.MembroNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NaoHaEmpregadoComEsseNomeException;
import br.ufal.ic.p2.wepayu.models.empregado.Empregado;

import java.util.ArrayList;
import java.util.List;

public class ListaEmpregados {


    public List<Empregado> getList() {
        return list;
    }

    private final List<Empregado> list;

    public ListaEmpregados(){
        list = new ArrayList<>();
    }

    public void add(Empregado e){
        list.add(e);
    }

    public int size()
    {
        return list.size();
    }


    public void remove(String index) throws EmpregadoNaoExisteException {
        for (int i = 0; i < this.size(); i++) {
            if (this.list.get(i).getId().equalsIgnoreCase(index)) {
                list.remove(i);
                return;
            }
        }
        throw new EmpregadoNaoExisteException();

    }
    public void set(String index, Empregado value) {
        for (int i = 0; i < this.size(); i++) {
            if (this.list.get(i).getId().equalsIgnoreCase(index)) list.set(i, value);
        }
    }

    public Empregado searchEmpregado(String index) throws EmpregadoNaoExisteException
    {
        for (int i = 0; i < this.size(); i++)
        {
            if (this.list.get(i).getId().equalsIgnoreCase(index)) return this.list.get(i);
        }
        throw new EmpregadoNaoExisteException();
    }
    public String searchEmpregadoByName(String name, int index) throws NaoHaEmpregadoComEsseNomeException {
        int iterator = 1;
        for (int i = 0; i < this.size(); i++)
        {
            if (this.list.get(i).getNome().equalsIgnoreCase(name) && iterator==index) return this.list.get(i).getId();
            else if (this.list.get(i).getNome().equalsIgnoreCase(name)) iterator++;
        }
        throw new NaoHaEmpregadoComEsseNomeException();
    }
    public Empregado searchEmpregadoMembro(String membro) throws MembroNaoExisteException {
        for (int i = 0; i < this.size(); i++)
        {
            if (this.list.get(i).isSindicalizado())
                if(this.list.get(i).getSindicato().getIdMembro().equals(membro))
                    return this.list.get(i);
        }
        throw new MembroNaoExisteException();
    }

    public void checkMembroId(String membro) throws HaOutroEmpregadoIdMembroException {
        for (int i = 0; i < this.size(); i++)
        {
            if (this.list.get(i).isSindicalizado())
                if(this.list.get(i).getSindicato().getIdMembro().equals(membro))
                    throw new HaOutroEmpregadoIdMembroException();
        }
    }
}
