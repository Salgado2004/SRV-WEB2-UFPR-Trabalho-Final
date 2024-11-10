/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufpr.webII.trabalhoFinal.infra.connection;

import java.util.List;

/**
 *
 * @author mateus
 * @param <T>
 */
public interface DAO <T> {
    public void insert(T element) throws Exception;
    public void update(T objeto) throws Exception;
    public void delete(T objeto) throws Exception;
    public List<T> listAll() throws Exception;
}
