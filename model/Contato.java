/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda.model;

/**
 *
 * @author chris
 */
public  class Contato {
        
    private String nome;
    private String telefone;
    private String email;
    private String aniversario;
    private boolean contatoValido;
    
    public Contato(){}
    
    public Contato(String nome, String telefone, String email, String aniversario){
        
        contatoValido = setNome(nome) && setTelefone(telefone) && setEmail(email) && setAniversario(aniversario);
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public boolean setNome(String nome) {
        
        if (nome.matches("\\w{1,}")) {
            
            this.nome = nome;
            return true;
            
        }else return false; 
    }
    
    /**
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone the telefone to set
     */
    public boolean setTelefone(String telefone) {
        
        if (telefone.matches("\\d{3,11}")) {
            
            this.telefone = telefone;
            return true;
        }else return false;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public boolean setEmail(String email) {
        if (email.matches("[a-zA-Z1-9\\.-]{2,}@[a-zA-Z1-9-]{2,}\\.[a-zA-Z1-9-]{2,5}(\\.\\w{2,5}){0,1}")) {
            this.email = email;
            return true;
        }else return false;
    }    
    
    /**
     * @return the aniversario
     */
    public String getAniversario() {
        return aniversario;
    }

    /**
     * @param aniversario the aniversario to set
     */
    public boolean setAniversario(String aniversario) {
        if (aniversario.matches("[0-2]{1}[0-9]|3[0-1]/0[1-9]|1[0-2]/19\\d{2}|20[0-9]{2}")) {
            this.aniversario = aniversario;
            return true;
        }else return false;
    }
            
    public void setByArray(String[] arrContato){
        
        contatoValido = 
                this.setNome(arrContato[0]) && 
                this.setTelefone(arrContato[1]) && 
                this.setEmail(arrContato[2]) &&
                this.setAniversario(arrContato[3]
        );
    }
    
    public String[] toArray(){
            
        return this.serialize().split(",");
    }
    
    public String serialize(){
        
        return
                this.nome + "," +
                this.telefone + "," +
                this.email + "," +
                this.aniversario;
    }

    /**
     * @return the contatoValido
     */
    public boolean isContatoValido() {
        return contatoValido;
    }
    
}
