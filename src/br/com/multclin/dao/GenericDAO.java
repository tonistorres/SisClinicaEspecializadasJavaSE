package br.com.multclin.dao;

import br.com.multclin.exceptions.PersistenciaException;
import java.util.List;

/**
 * AULA 14: Nesta CAMADA que chamarei de INTERMEDI�RIA OU AUXILIAR 3� CAMADA DAO
 * iremos implementar uma interface com os m�todos mais comuns e rotineiros aos
 * objetos DAO da 3� Camada. A programa��o gen�rica � um estilo de programa��o
 * na qual os algoritmos s�o escritos com tipos a serem especificados
 * posteriormente, ou seja, s�o instanciados posteriormente para um tipo
 * espec�fico fornecido como par�metro. Gen�ricos s�o uma facilidade da
 * programa��o gen�rica que foi adicionado � linguagem Java na vers�o 5.0.
 *
 * Este tipo de programa��o permite que um m�todo opere sobre objetos de v�rios
 * tipos, fornecendo �type safety� em tempo de compila��o e promovendo a
 * elimina��o da necessidade de casts.
 *
 * Sem gen�ricos � poss�vel escrever c�digo com erros de tipifica��o de objetos
 * que passem pela fase de compila��o com sucesso, sendo detectados apenas em
 * tempo de execu��o.
 *
 * E, como sabemos, corrigir erros de compila��o � mais f�cil do que corrigir
 * erros de execu��o.
 */
public interface GenericDAO<T> {

    void inserir(T obj) throws PersistenciaException;
    
    boolean inserirControlEmail (T obj) throws PersistenciaException;

    void atualizar(T obj) throws PersistenciaException;
    
    boolean atualizarControlEmail(T obj)throws PersistenciaException;

    
    void deletar(T obj) throws PersistenciaException;
    boolean deletarControlEmail(T obj)throws PersistenciaException;
    
    void deletarTudo () throws PersistenciaException;
    boolean deletarTudoControlEmail(T obj)throws PersistenciaException;
    
    
    void deletarPorCodigoTabela(int codigo)throws PersistenciaException;
    boolean deletarControlEmail( int codigo)throws PersistenciaException;

    List<T> listarTodos() throws PersistenciaException;
     List<T> listarTodosParametro(String str) throws PersistenciaException;
    

    T buscarPorId(Integer id) throws PersistenciaException;

    T buscarPor(T obj) throws Exception;

    boolean verificaDuplicidade(T obj) throws PersistenciaException;

    T filtrarAoClicar(T modelo) throws PersistenciaException;
    
    

}
