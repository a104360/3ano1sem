/*
 *  DISCLAIMER: Este código foi criado para discussão e edição durante as aulas práticas de DSS, representando
 *  uma solução em construção. Como tal, não deverá ser visto como uma solução canónica, ou mesmo acabada.
 *  É disponibilizado para auxiliar o processo de estudo. Os alunos são encorajados a testar adequadamente o
 *  código fornecido e a procurar soluções alternativas, à medida que forem adquirindo mais conhecimentos.
 */
package uminho.dss.turmas3l;

import uminho.dss.turmas3l.ui.*;

/**
 * @author DSS
 * @version 20201206
 */
public class Main {

    /**
     * O método main cria a aplicação e invoca o método run()
     */
    public static void main(String[] args) {
        try {
            TextUI t = new TextUI();
            t.run();
        }
        catch (Exception e) {
            System.out.println("Erro fatal: "+e.getMessage()+" ["+e.toString()+"]");
        }
    }


}
