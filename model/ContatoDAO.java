/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author chris
 */
public class ContatoDAO {
    
    private static final String[] CAMPOS = {"nome", "telefone", "email", "aniversário"};
    private final File file = new File("C:/Users/Chris/Desktop/listaContatos.txt");
    
    public ContatoDAO(){}
    /**
     * @return the CAMPOS
     */
    public String[] getCAMPOS() {
        return CAMPOS;
    }
    
    public boolean salvar(Contato contato, boolean ignorarDuplicado){
        
        ArrayList<Contato> tempFile = getContatos();

        boolean canWrite = true;

        if (!ignorarDuplicado) {

            for (Contato contatoTemp : tempFile) {

                if (contatoTemp.getTelefone().equals(contato.getTelefone())){

                    canWrite = false;
                }
            }
        }

        if (canWrite) {

            tempFile.add(contato);
            escreveArquivo(tempFile);
            return true;
        }

        return false;
    }
    
    public void alterar(String valorAntigo, String valorNovo){
        
        ArrayList<Contato> tempFile = this.getContatos();

        String[] arrContato;

        for (Contato contato : tempFile) {

            arrContato = contato.toArray();

            for (int j = 0; j < arrContato.length; j++) {

                if (arrContato[j].equals(valorAntigo)) {

                    arrContato[j] = valorNovo;
                    contato.setByArray(arrContato);
                }
            }
        }
            
        escreveArquivo(tempFile);
        
    }
    
    public void excluir(File file){

        String numero = JOptionPane.showInputDialog("Entre com o numero do contato que deseja excluir");

        ArrayList<Contato> tempFile = this.getContatos();

        int index = 121212;

        for (int i = 0; i < tempFile.size(); i++) {

            if (tempFile.get(i).getTelefone().matches(numero)) {
                index = i;
            }
        }

        if (index != 121212) {

            tempFile.remove(index);

        }else System.out.println("Contato não encontrado para o número " + numero);

        escreveArquivo(tempFile);
    }
    
    public ArrayList<Contato> getContatos(){

        try{

            ArrayList<Contato> tempFile = new ArrayList<>();
            Scanner scan = new Scanner(file);

            String linha;

            while(scan.hasNextLine()){

                linha = scan.nextLine();

                String[] arrContato = linha.split(",");

                Contato contato = new Contato(
                        arrContato[0],
                        arrContato[1],
                        arrContato[2],
                        arrContato[3]
                );

                tempFile.add(contato);
            }

            return tempFile;

        }catch(FileNotFoundException e){

            System.out.println("O arquivo não foi encontrado");
        }
        
       return null;
    }
    
    
    private void escreveArquivo(ArrayList<Contato> tempFile){
               
    
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.file));

            for (Contato contato : tempFile) {

                bw.write(contato.serialize());
                bw.newLine();
            }

            bw.close();
        
        }catch(IOException e){
            
            System.out.println("Ocoreu um erro com o arquivo");
        }
    }
}
