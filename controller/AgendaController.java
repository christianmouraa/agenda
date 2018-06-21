/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.controller;

import agenda.model.ContatoDAO;
import agenda.model.Contato;
import agenda.view.AgendaView;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chris
 */
public class AgendaController {
    
    AgendaView view;
    ContatoDAO model;
    
    public AgendaController(AgendaView view){
        
        this.view = view;
        this.model = new ContatoDAO();
    }
    
    public void editarContato(){
        
        int linha = view.getTabelaContatos().getSelectedRow();
        int coluna = view.getTabelaContatos().getSelectedColumn();
        
        String valorAntigo = (String) view.getTabelaContatos().getValueAt(linha, coluna);
        String valorNovo = view.getCampoTextoGenerico().getText(); 
        
        model.alterar(valorAntigo, valorNovo);
        this.popularTabela();        
    }
    
    public boolean AdicionarContato(String nome, String telefone, String email, String aniversario){
        
        Contato contato = new Contato(nome, telefone, email, aniversario);
        
        if (contato.isContatoValido()) {
            
            if (!model.salvar(contato, false)) {
                int opcao = JOptionPane.showConfirmDialog(view, 
                    "Este número de telefone já está relacionado à um contato"
                  + "deseja continuar?"
                );

                if (opcao == 0) model.salvar(contato, true);
                
                else return false;
            }
            
            return true;
            
        }else {
            
            String[] arrContato = contato.toArray();
            String mensagem = "Erro de formato de entrada:";
            
            for (int i = 0; i < arrContato.length; i++) {
            
                if (arrContato[i].equals("")) {
                   
                   switch(i){
                       
                        case 1: 
                            mensagem = mensagem.concat("\n * Nome deve ter apenas letras e números.");
                            break;
                       
                        case 2:
                            mensagem = mensagem.concat("\n * Telefone deve conter apenas números e ter entre 3 e 11 dígitos.");
                            break;
                        case 3:
                            mensagem = mensagem.concat("\n * Formato de email inválido");
                            break;
                        case 4: 
                            mensagem = mensagem.concat("\n * Aniversário deve estar no padrão dd/mm/aaaa");
                   }
                }
            }
            
            return false;
        }
    }
    
    public void popularTabela(){

        ArrayList<Contato> contatos = model.getContatos();
        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaContatos().getModel();

        //limpa conteudo atual da tabela
        tableModel.setRowCount(0);

        for (Contato contato : contatos) {

            String[] arrContato = contato.toArray();
            tableModel.addRow(arrContato);
        }

        view.getTabelaContatos().setModel(tableModel);

    }
  
}
