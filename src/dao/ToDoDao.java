/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import classes.ToDo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.Util;

/**
 *
 * @author flavio.ever
 */
public class ToDoDao {

    private Util util = new Util();

    /**
     * Metodo lista os itens por titulo ou data
     *
     * @author Flavio Ever
     * @param id primary key do todo
     * @param titulo titulo da pesquisa
     * @param data data da pesquisa
     * @return List Lista de ToDo
     * @since 10/05/2020
     */
    public List<ToDo> index(String id, String titulo, String data) {
        List<ToDo> ToDoList = new ArrayList<>();

        // Se houver algo então usar condição;
        String whereId = id.equals("") ? "" : "WHERE cd_todo = ?";
        String whereTitulo = titulo.equals("") ? "" : "WHERE titulo LIKE ?";
        String whereData = data.equals("") ? "" : "WHERE data_prevista LIKE ?";

        try {

            Connection con = Conecta.getConexao();
            String sql = "SELECT * FROM tb_todo " + whereTitulo + whereData + whereId + ";";
            PreparedStatement ps = con.prepareStatement(sql);

            if (!id.equals("")) {
                ps.setString(1, id);
            }
            
            if (!titulo.equals("")) {
                ps.setString(1, "%" + titulo + "%");
            }

            if (!data.equals("")) {
                ps.setString(1, "%" + util.dbParserData(data) + "%");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToDo ToDoItem = new ToDo();

                // Primary Key
                ToDoItem.setPrimaryKey(rs.getInt("cd_todo"));

                // All
                ToDoItem.setTitulo(rs.getString("titulo"));
                ToDoItem.setDataPrevista(util.dbParserDataPtBr(rs.getString("data_prevista")));
                ToDoItem.setValorUnitario(Float.parseFloat(rs.getString("valor_unitario")));
                ToDoItem.setQtd(Integer.parseInt(rs.getString("qtd")));
                ToDoItem.setValorTotal(Float.parseFloat(rs.getString("valor_total")));
                ToDoItem.setPrioridade(rs.getString("prioridade"));
                ToDoItem.setStatus(rs.getString("status_tarefa"));

                // Cria lista
                ToDoList.add(ToDoItem);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ToDoList;
    }

    /**
     * Metodo cria ToDo
     *
     * @author Flavio Ever
     * @param todo objeto estrutura
     * @return String resultado da criacao
     * @since 10/05/2020
     */
    public String store(ToDo todo) {
        String resp = "";

        try {
            Connection conn = Conecta.getConexao();

            // Estrutura objeto
            PreparedStatement psmtOut = conn.prepareStatement("SELECT * FROM tb_todo WHERE titulo=?");
            PreparedStatement psmtIn = conn.prepareStatement("INSERT INTO tb_todo VALUES(null, ?, ?, ?, ?, ?, ?, ?)");

            // Incrementa objeto
            psmtOut.setString(1, todo.getTitulo());

            psmtIn.setString(1, todo.getTitulo());
            psmtIn.setString(2, util.dbParserData(todo.getDataPrevista()));
            psmtIn.setFloat(3, todo.getValorUnitario());
            psmtIn.setInt(4, todo.getQtd());
            psmtIn.setFloat(5, todo.getValorTotal());
            psmtIn.setString(6, todo.getPrioridade());
            psmtIn.setString(7, todo.getStatus());

            ResultSet rs = psmtOut.executeQuery();
            if (rs.next()) {
                resp = "Ops! Item já cadastrado";
            } else {
                psmtIn.execute();
                resp = "ok";
            }

            // Fecha possíveis conexões
            psmtOut.close();
            psmtIn.close();
            conn.close();
        } catch (Exception e) {
            resp = e.toString();
        }

        return resp;
    }

    /**
     * Metodo atualiza o ToDo a partir da chave primaria
     *
     * @author Flavio Ever
     * @param todo objeto estrutura
     * @return String resultado da ataulizacao
     * @since 10/05/2020
     */
    public String update(ToDo todo) {
        String resp = "";

        try {
            Connection conn = Conecta.getConexao();
            System.out.println("aaaaa " + todo.getStatus());
            // Estrutura objeto
            PreparedStatement psmtIn = conn.prepareStatement("UPDATE tb_todo SET titulo=?, data_prevista=?, valor_unitario=?, qtd=?, valor_total=?, prioridade=?, status_tarefa=? WHERE cd_todo=?");

            psmtIn.setString(1, todo.getTitulo());
            psmtIn.setString(2, util.dbParserData(todo.getDataPrevista()));
            psmtIn.setFloat(3, todo.getValorUnitario());
            psmtIn.setInt(4, todo.getQtd());
            psmtIn.setFloat(5, todo.getValorTotal());
            psmtIn.setString(6, todo.getPrioridade());
            psmtIn.setString(7, todo.getStatus());

            // Primary Key
            psmtIn.setInt(8, todo.getPrimaryKey());

            psmtIn.execute();
            resp = "ok";

            // Fecha possíveis conexões
            psmtIn.close();
            conn.close();
        } catch (Exception e) {
            resp = e.toString();
        }

        return resp;
    }

    /**
     * Metodo remove o ToDo a partir da chave primaria
     *
     * @author Flavio Ever
     * @param key chave primaria
     * @return String resultado da remocao
     * @since 10/05/2020
     */
    public String remove(Integer key) {
        String resp = "";

        try {
            Connection conn = Conecta.getConexao();

            // Estrutura objeto
            PreparedStatement psmtIn = conn.prepareStatement("DELETE FROM tb_todo WHERE cd_todo=?");

            // Primary Key
            psmtIn.setInt(1, key);

            psmtIn.execute();
            resp = "ok";

            // Fecha possíveis conexões
            psmtIn.close();
            conn.close();
        } catch (Exception e) {
            resp = e.toString();
        }

        return resp;
    }
}
