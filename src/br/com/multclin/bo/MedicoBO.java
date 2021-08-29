package br.com.multclin.bo;

import br.com.multclin.dao.MedicoDAO;
import br.com.multclin.dto.MedicoDTO;

import br.com.multclin.exceptions.NegocioException;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.exceptions.ValidacaoException;
import br.com.multclin.telas.Login;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedicoBO {

    MedicoDAO medicoDAO = new MedicoDAO();

    /**
     * Formatador de Datas
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void cadastrar(MedicoDTO medicoDTO) throws NegocioException {

        try {

            MedicoDAO medicoDAO = new MedicoDAO();

            medicoDAO.inserir(medicoDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            
        }

    }

    
    public void UsuarioBO(MedicoDTO medicoDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        MedicoDAO medicoDAO;
        try {
            medicoDAO = new MedicoDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            medicoDAO.atualizar(medicoDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirPorCodigoBO(int codigo) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        MedicoDAO medicoDAO;

        try {

            medicoDAO = new MedicoDAO();
            medicoDAO.deletarPorCodigoTabela(codigo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(MedicoDTO medicoDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        MedicoDAO medicoDAO;
        try {
            medicoDAO = new MedicoDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            medicoDAO.deletar(medicoDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    public String ValidarForcaSenha(String senha) throws ValidacaoException {

        String strRetorno = null;

        if (senha == null || senha.equals("")) {

            strRetorno = "Campo Senha Obrigatório.";

            throw new ValidacaoException("Campo SENHA é obrigatório.");

        } else if (senha.length() != 8) {

            strRetorno = "Campo senha deve conter exatamente 8 caracter.";
            throw new ValidacaoException("Campo senha deve conter \nexatamente 8 caracter.");
        } else {

            char c;

            //criando dois cotadores e inicializando-os
            int ctalfa = 0;
            int ctnum = 0;
            int ctcarctEspecial = 0;

            //laço de repetição for agora 
            for (int i = 0; i < senha.length(); i++) {

                c = senha.charAt(i);//i-ésimo caractere da strin s

                // verificando se é um caractere alfabético
                if (((c >= 'A') && (c <= 'Z'))
                        || ((c >= 'a') && (c <= 'z'))) {
                    ctalfa = ctalfa + 1;
                    System.out.printf("%c ---> %da. letra\n", c, ctalfa);
                } // verificando se é um caractere numérico
                else if ((c >= '0') && (c <= '9')) {
                    ctnum = ctnum + 1;
                    System.out.printf("%c ---> %do. número\n", c, ctnum);
                } else {
                    ctcarctEspecial = ctcarctEspecial + 1;
                    System.out.printf("%c\n", c);
                }
            }

            if (ctalfa > 0 && ctnum > 0 && ctcarctEspecial > 0) {
                strRetorno = "Senha Forte";
                return strRetorno;
            } else if ((ctalfa > 0 && ctnum > 0) || (ctalfa > 0 && ctcarctEspecial > 0) || (ctnum > 0 && ctcarctEspecial > 0)) {
                strRetorno = "Senha Mediana";
                return strRetorno;
            } else {
                strRetorno = "Senha Fraca";
                return strRetorno;
            }

        }

    }


    public boolean duplicidadeUsuarios(MedicoDTO medicoDTO) throws PersistenciaException {

        MedicoDAO medicoDAO = new MedicoDAO();

        boolean verificaDuplicidade = medicoDAO.verificaDuplicidade(medicoDTO);

        return verificaDuplicidade;
    }

    public List<MedicoDTO> listarTodosUsuariosBO() throws PersistenciaException {

        List<MedicoDTO> listaDeUsuariosBO = new ArrayList<>();

        try {

            listaDeUsuariosBO = medicoDAO.listarTodos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeUsuariosBO;

    }

    /**
     * Método validaNome() é um método público que tem como retorno uma boolean
     * e recebe como parâmetro uma String.Onde será analisado por este método sé
     * o nome é nulo ou tem um número de caracter superior ao permetido no banco
     * de dados.Método que fica alojado na camada BO(Busines Object, camada de
     * negocio)
     *
     */
    public boolean validaCamposForm(MedicoDTO medicoDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (medicoDTO.getCpfDto() == null || medicoDTO.getCpfDto().equals("   .   .   -  ")) {
            ehValido = false;
            throw new ValidacaoException("Campo CPF Obrigatório");

        } else if (medicoDTO.getNomeDto() == null || medicoDTO.getNomeDto().equals("")) {
            ehValido = false;
            throw new ValidacaoException("Campo NOME Obrigatório");
            
        }  else if (medicoDTO.getSexoDto() == null || medicoDTO.getSexoDto().equals("")) {
            
            ehValido = false;
            throw new ValidacaoException("Campo SEXO Obrigatorio");
            
        }else if(medicoDTO.getEstadoCivilDto()==null|| medicoDTO.getEstadoCivilDto().equals("")){
            
            ehValido = false;
            throw new ValidacaoException("Campo ESTADO CIVIL Obrigatório");
        }else if(medicoDTO.getUfDto()==null|| medicoDTO.getUfDto().equals("")){
            
            ehValido = false;
            throw new ValidacaoException("Campo UF Obrigatório");
        }else if(medicoDTO.getCidadeDto()==null|| medicoDTO.getCidadeDto().equals("")){
            
            ehValido = false;
            throw new ValidacaoException("Campo CIDADE Obrigatório");
        }
        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaEndereco(String endereco) throws ValidacaoException {

        boolean ehValido = true;

        if (endereco == null || endereco.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco Obrigatório");

        }

        if (endereco.length() > 50) {

            ehValido = false;
            throw new ValidacaoException("Campo endereco aceita no MAX 45 chars");

        }

        return ehValido;
    }

    /**
     * Como esse método é um método que verifica algo então terá um retorno
     * boolean verdadeiro ou false
     */
    public boolean validaDtNascimento(String dtNascimento) throws ValidacaoException {

        boolean ehValido = true;

        if (dtNascimento == null || dtNascimento.equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo Data Nascimento Obrigatório");

        } else {

            try {
                dateFormat.parse(dtNascimento);
                ehValido = true;
            } catch (ParseException ex) {
                ehValido = false;
                throw new ValidacaoException("Formato inválido de Data! \n Ex:01/01/2001");

            }
        }

        return ehValido;
    }

    public void removeTudo() throws ValidacaoException {

        try {

            medicoDAO.deletarTudo();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:" + e.getMessage());
            throw new ValidacaoException(e.getMessage());

        }

    }

    public void atualizarBO(MedicoDTO medicoDTO) {

        try {

            medicoDAO.atualizar(medicoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

  

}//aqui fim da classe 
