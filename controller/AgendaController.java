/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.controller;

import agenda.model.ContatoDAO;
import agenda.model.Contato;
import agenda.view.AgendaView;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        switch(coluna){
//            
//            case 0:
//                if (valorNovo.matches("\\w")) model.alterar(valorAntigo, valorNovo);
//                else JOptionPane.showMessageDialog(view, "Entre apenas com letras e/ou números");
//                break;
//                
//            case 1:
//                if (valorNovo.matches("\\d{8,12}")) model.alterar(valorAntigo, valorNovo);
//                else JOptionPane.showMessageDialog(view, valorNovo + " não é um número válido");
//                break;
//                
//            case 2:
//                String emailRegex = "[a-zA-Z1-9\\.-]{2,}@[a-zA-Z1-9-]{2,}\\.[a-zA-Z1-9-]{2,5}\\.{0,1}\\w{0,3}.*[^\\.]$";
//                if (valorNovo.matches(emailRegex)) model.alterar(valorAntigo, valorNovo);
//                else JOptionPane.showMessageDialog(view, "Este não é um formato de e-mail válido");
//                break;
//            
//            case 3:
//                if (valorNovo.matches("\\d{2}/\\d{2}")) {
//                
//                }
//        }
        
    }
    
    public boolean AdicionarContato(){
        
        Contato contato = new Contato(
                
            view.getCampoTextoGenerico().getText(),
            view.getCampoTelefone().getText(),
            view.getCampoEmail().getText(),
            view.getCampoAniver().getText()
        );
        
        if (!contato.getTelefone().equals("")) {
            
            if (!model.adicionar(contato, false)) {
                int opcao = JOptionPane.showConfirmDialog(view, 
                    "Este número de telefone já está relacionado à um contato"
                  + "deseja continuar?"
                );

                if (opcao == 0) model.adicionar(contato, true);
                else return false;
            }

            popularTabela();
            
            return true;
        }
        
        return false;
    }
    
    public void popularTabela(){
        
        try {
            
            ArrayList<String> contatos = model.getContatos();
            DefaultTableModel tableModel = (DefaultTableModel) view.getTabelaContatos().getModel();
   
            //limpa conteudo atual da tabela
            tableModel.setRowCount(0);
            
            for (String contato : contatos) {
            
                String[] contatoArray = contato.split(",");
                tableModel.addRow(contatoArray);
            }
            
            view.getTabelaContatos().setModel(tableModel);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AgendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
}
