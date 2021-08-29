package br.com.multclin.bo;

import br.com.multclin.dao.UsuarioDAO;
import br.com.multclin.dto.UsuarioDTO;
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

public class UsuarioBO {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Formatador de Datas
     */
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void cadastrar(UsuarioDTO usuarioDTO) throws NegocioException {

        try {

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            usuarioDAO.inserir(usuarioDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }

    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void UsuarioBO(UsuarioDTO usuarioDTO) {

        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        UsuarioDAO usuarioDAO;
        try {
            usuarioDAO = new UsuarioDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            usuarioDAO.atualizar(usuarioDTO);

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
        UsuarioDAO usuarioDAO;

        try {

            usuarioDAO = new UsuarioDAO();
            usuarioDAO.deletarPorCodigoTabela(codigo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }

    /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(UsuarioDTO usuarioDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        UsuarioDAO usuarioDAO;
        try {
            usuarioDAO = new UsuarioDAO();
            /*
             * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
             * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            usuarioDAO.deletar(usuarioDTO);
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

    public boolean validarLoginUsuarioEdicao(UsuarioDTO usuarioLogin) throws ValidacaoException {

        boolean ehValido = true;

        if (usuarioLogin.getLoginDto() == null || usuarioLogin.getLoginDto().equals("")) {
            ehValido = false;
            throw new ValidacaoException("Campo Login é obrigatório.");

        } else if (usuarioLogin.getLoginDto().length() > 25) {
            ehValido = false;
            throw new ValidacaoException("Campo Login aceita no máximo 25 caracter.");

        } else {

            ehValido = true;

        }
        return ehValido;
    }

    public boolean duplicidadeUsuarios(UsuarioDTO usuarioDTO) throws PersistenciaException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        boolean verificaDuplicidade = usuarioDAO.verificaDuplicidade(usuarioDTO);

        return verificaDuplicidade;
    }

    public List<UsuarioDTO> listarTodosUsuariosBO() throws PersistenciaException {

        List<UsuarioDTO> listaDeUsuariosBO = new ArrayList<>();

        try {

            listaDeUsuariosBO = usuarioDAO.listarTodos();

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
    public boolean validaCampos(UsuarioDTO usuarioDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (usuarioDTO.getUsuarioDto() == null || usuarioDTO.getUsuarioDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo nome Obrigatório");

        } else if (usuarioDTO.getLoginDto() == null || usuarioDTO.getLoginDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo login Obrigatório");
        } else if (usuarioDTO.getSenhaDto() == null || usuarioDTO.getSenhaDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo senha Obrigatório");
        } else if (usuarioDTO.getSexoDto() == null || usuarioDTO.getSexoDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Selecione um Sexo Válido.");

        } else if (usuarioDTO.getPerfilDto() == null || usuarioDTO.getPerfilDto().equals("")) {
            
            ehValido = false;
            throw new ValidacaoException("É necessário definir um perfil para Usuario.");
        }else if(usuarioDTO.getEstadoDto()==null|| usuarioDTO.getEstadoDto().equals("")){
            
            ehValido = false;
            throw new ValidacaoException("É necessário definir se Usuário está ATIVO OU INATIVO.");
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

            usuarioDAO.deletarTudo();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:" + e.getMessage());
            throw new ValidacaoException(e.getMessage());

        }

    }

    public void atualizarBO(UsuarioDTO usuarioDTO) {

        try {

            usuarioDAO.atualizar(usuarioDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }

    public UsuarioDTO logarBO(UsuarioDTO usuarioDTO) throws ValidacaoException {

        UsuarioDTO resultadoPesquisaBO = null;

        if (usuarioDTO.getLoginDto() == null || "".equals(usuarioDTO.getLoginDto())) {

            throw new ValidacaoException("Login Obrigatorio!");

        } else if (usuarioDTO.getSenhaDto() == null || "".equals(usuarioDTO.getSenhaDto())) {

            throw new ValidacaoException("Senha Obrigatorio!");

        } else {

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            try {
                resultadoPesquisaBO = usuarioDAO.logar(usuarioDTO);

                if (resultadoPesquisaBO == null || resultadoPesquisaBO.equals("")) {
                    throw new ValidacaoException("Usuario ou Senha Incorretos");

                } else {
                    return resultadoPesquisaBO;
                }
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
                ex.getMessage();
            }

        }//aqui fim do else
        return resultadoPesquisaBO;

    }//aqui fim do método 

}//aqui fim da classe 
