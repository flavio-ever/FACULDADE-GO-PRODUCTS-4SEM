/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author flavio.ever
 */
public class ToDo {

    private Integer primaryKey;
    private String titulo;
    private String dataPrevista;
    private Float valorUnitario;
    private Integer qtd;
    private Float valorTotal;
    private String prioridade;
    private String status;

    /**
     * Metodo que obtém a chave primária
     *
     * @author Flavio Ever
     * @since 10/05/2020
     * @return Inteiro com a chave
     */
    public Integer getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Metodo seta a chave primária
     *
     * @author Flavio Ever 
     * @param primaryKey chave primária
     * @since 10/05/2020
     */
    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Metodo que obtém o titulo
     *
     * @author Flavio Ever
     * @since 10/05/2020
     * @return String titulo do ToDo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Metodo seta o titulo
     *
     * @author Flavio Ever
     * @param titulo titulo
     * @since 10/05/2020
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Metodo que obtém data prevista pra compra do ToDo
     *
     * @author Flavio Ever
     * @since 10/05/2020
     * @return String retorna a data
     */
    public String getDataPrevista() {
        return dataPrevista;
    }

    /**
     * Metodo seta a data prevista da compra do ToDo
     *
     * @author Flavio Ever
     * @param dataPrevista prevista da compra 
     * @since 10/05/2020
     */
    public void setDataPrevista(String dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    /**
     * Metodo obtém o valor unitário
     *
     * @author Flavio Ever
     * @since 10/05/2020
     * @return Float valor unitário
     */
    public Float getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Metodo que seta o valor unitário
     *
     * @author Flavio Ever
     * @param valorUnitario valor unitário
     * @since 10/05/2020
     */
    public void setValorUnitario(Float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * Metodo obtém a quantidade unitário
     *
     * @author Flavio Ever
     * @since 10/05/2020
     * @return Integer quantidad de ToDo
     */
    public Integer getQtd() {
        return qtd;
    }

    /**
     * Metodo que seta a quantidade de ToDo
     *
     * @author Flavio Ever
     * @param qtd seta a quantidade 
     * @since 10/05/2020
     */
    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    /**
     * Metodo que seta a o valor total dos ToDo
     *
     * @author Flavio Ever
     * @return Float valor total
     * @since 10/05/2020
     */
    public Float getValorTotal() {
        return valorTotal;
    }

    /**
     * Metodo que seta o valor total dos ToDo
     *
     * @author Flavio Ever
     * @param valorTotal valor total 
     * @since 10/05/2020
     */
    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Metodo que obtém a prioridade do ToDo
     *
     * @author Flavio Ever
     * @return String prioridade
     * @since 10/05/2020
     */
    public String getPrioridade() {
        return prioridade;
    }

    /**
     * Metodo que seta a prioridade do ToDo
     *
     * @author Flavio Ever
     * @param prioridade prioridade 
     * @since 10/05/2020
     */
    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    /**
     * Metodo que obtém o status do ToDo
     *
     * @author Flavio Ever
     * @return String status do ToDo
     * @since 10/05/2020
     */
    public String getStatus() {
        return status;
    }

    /**
     * Metodo que seta o status do ToDo
     *
     * @author Flavio Ever
     * @param status  status do ToDo 
     * @since 10/05/2020
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
