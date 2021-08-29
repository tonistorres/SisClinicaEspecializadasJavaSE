package br.com.multclin.dao;

import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class MedicoDAO implements GenericDAO<MedicoDTO> {

    @Override
    public List<MedicoDTO> listarTodos() throws PersistenciaException {

        List<MedicoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            
            String sql = "SELECT *FROM tbMedicos order by nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                MedicoDTO medicoDTO = new MedicoDTO();

                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setDataNascDto(resultSet.getString("dtNasc"));
                medicoDTO.setIdadeDto(resultSet.getString("idade"));
                medicoDTO.setCpfDto(resultSet.getString("cpf"));
                medicoDTO.setSexoDto(resultSet.getString("sexo"));
                medicoDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                medicoDTO.setEmailPrefDto(resultSet.getString("email"));
                medicoDTO.setUfDto(resultSet.getString("uf"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                medicoDTO.setBairroDto(resultSet.getString("bairro"));
                medicoDTO.setRuaDto(resultSet.getString("rua"));
                medicoDTO.setComplementoDto(resultSet.getString("complemento"));
                medicoDTO.setnCasaDto(resultSet.getString("ncasa"));
                medicoDTO.setCEPDto(resultSet.getString("cep"));
                medicoDTO.setFoneFixoDto(resultSet.getString("fone"));
                medicoDTO.setCelularPrefDto(resultSet.getString("celular"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));

                /**
                 * Adiciona na lista todos os dados capturado pelo laço e
                 * adicionado no objeto medicoDTO
                 */
                lista.add(medicoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<MedicoDTO> filtrarPesqRapidaPorCPF(String pesquisar) throws PersistenciaException {

        List<MedicoDTO> lista = new ArrayList<MedicoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbMedicos WHERE cpf LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                MedicoDTO medicoDTO = new MedicoDTO();

                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setDataNascDto(resultSet.getString("dtNasc"));
                medicoDTO.setIdadeDto(resultSet.getString("idade"));
                medicoDTO.setCpfDto(resultSet.getString("cpf"));
                medicoDTO.setSexoDto(resultSet.getString("sexo"));
                medicoDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                medicoDTO.setEmailPrefDto(resultSet.getString("email"));
                medicoDTO.setUfDto(resultSet.getString("uf"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                medicoDTO.setBairroDto(resultSet.getString("bairro"));
                medicoDTO.setRuaDto(resultSet.getString("rua"));
                medicoDTO.setComplementoDto(resultSet.getString("complemento"));
                medicoDTO.setnCasaDto(resultSet.getString("ncasa"));
                medicoDTO.setCEPDto(resultSet.getString("cep"));
                medicoDTO.setFoneFixoDto(resultSet.getString("fone"));
                medicoDTO.setCelularPrefDto(resultSet.getString("celular"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));

                lista.add(medicoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<MedicoDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<MedicoDTO> lista = new ArrayList<MedicoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbMedicos WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                MedicoDTO medicoDTO = new MedicoDTO();

                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setDataNascDto(resultSet.getString("dtNasc"));
                medicoDTO.setIdadeDto(resultSet.getString("idade"));
                medicoDTO.setCpfDto(resultSet.getString("cpf"));
                medicoDTO.setSexoDto(resultSet.getString("sexo"));
                medicoDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                medicoDTO.setEmailPrefDto(resultSet.getString("email"));
                medicoDTO.setUfDto(resultSet.getString("uf"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                medicoDTO.setBairroDto(resultSet.getString("bairro"));
                medicoDTO.setRuaDto(resultSet.getString("rua"));
                medicoDTO.setComplementoDto(resultSet.getString("complemento"));
                medicoDTO.setnCasaDto(resultSet.getString("ncasa"));
                medicoDTO.setCEPDto(resultSet.getString("cep"));
                medicoDTO.setFoneFixoDto(resultSet.getString("fone"));
                medicoDTO.setCelularPrefDto(resultSet.getString("celular"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));
                lista.add(medicoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Esse método que recebe como parâmetro um numero do tipo inteiro irá fazer
     * uma pesquisa no Banco de Dados Infoq na tabela de usuaios e em seguida
     * retorna o nome encontrado caso exista, pois, trata-se de um método com
     * retorno do tipo MedicoDTO e que recebe como parâmetro um número inteiro
     * vindo de uma JTable
     */
    public MedicoDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        MedicoDTO medicoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbMedicos where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                medicoDTO = new MedicoDTO();

                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setDataNascDto(resultSet.getString("dtNasc"));
                medicoDTO.setIdadeDto(resultSet.getString("idade"));
                medicoDTO.setCpfDto(resultSet.getString("cpf"));
                medicoDTO.setSexoDto(resultSet.getString("sexo"));
                medicoDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                medicoDTO.setEmailPrefDto(resultSet.getString("email"));
                medicoDTO.setUfDto(resultSet.getString("uf"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                medicoDTO.setBairroDto(resultSet.getString("bairro"));
                medicoDTO.setRuaDto(resultSet.getString("rua"));
                medicoDTO.setComplementoDto(resultSet.getString("complemento"));
                medicoDTO.setnCasaDto(resultSet.getString("ncasa"));
                medicoDTO.setCEPDto(resultSet.getString("cep"));
                medicoDTO.setFoneFixoDto(resultSet.getString("fone"));
                medicoDTO.setCelularPrefDto(resultSet.getString("celular"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));

                return medicoDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    private void erroMsg(String mensagemErro) {

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer é acessar o host abaixo estarei usando
        //o host do google para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Erro Camada GUI - Metodo:SalvarAdicoesEdicoes()");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

            //enviando mensagem de confirmação Usuário
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Todos Procedimentos concluídos com Sucesso."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro ao Enviar Email." + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    @Override
    public void inserir(MedicoDTO medicoDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbMedicos(id,nome,dtNasc,idade,cpf,sexo,estadocivil,email,uf,cidade,bairro,rua,complemento,ncasa,cep,fone,celular,Conselho,cadastro) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, medicoDTO.getIdDto());//id
            statement.setString(2, medicoDTO.getNomeDto());
            statement.setString(3, medicoDTO.getDataNascDto());
            statement.setString(4, medicoDTO.getIdadeDto());
            statement.setString(5, medicoDTO.getCpfDto());

            statement.setString(6, medicoDTO.getSexoDto());
            statement.setString(7, medicoDTO.getEstadoCivilDto());
            statement.setString(8, medicoDTO.getEmailPrefDto());

            statement.setString(9, medicoDTO.getUfDto());
            statement.setString(10, medicoDTO.getCidadeDto());
            statement.setString(11, medicoDTO.getBairroDto());
            statement.setString(12, medicoDTO.getRuaDto());
            statement.setString(13, medicoDTO.getComplementoDto());
            statement.setString(14, medicoDTO.getnCasaDto());
            statement.setString(15, medicoDTO.getCEPDto());
            statement.setString(16, medicoDTO.getFoneFixoDto());
            statement.setString(17, medicoDTO.getCelularPrefDto());
            statement.setString(18, medicoDTO.getConselhoDto());
            statement.setDate(19, null);

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(MedicoDTO medicoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "UPDATE tbMedicos SET nome=?,dtNasc=?,idade=?,cpf=?,sexo=?,estadocivil=?,email=?,uf=?,cidade=?,bairro=?,rua=?,complemento=?,ncasa=?,cep=?,fone=?,celular=?,Conselho=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, medicoDTO.getNomeDto());//nome
            statement.setString(2, medicoDTO.getDataNascDto());//data nascimento
            statement.setString(3, medicoDTO.getIdadeDto());//idade
            statement.setString(4, medicoDTO.getCpfDto());//cpf
            statement.setString(5, medicoDTO.getSexoDto());//sexo
            statement.setString(6, medicoDTO.getEstadoCivilDto());//estado civil
            statement.setString(7, medicoDTO.getEmailPrefDto());
            statement.setString(8, medicoDTO.getUfDto());
            statement.setString(9, medicoDTO.getCidadeDto());
            statement.setString(10, medicoDTO.getBairroDto());
            statement.setString(11, medicoDTO.getRuaDto());
            statement.setString(12, medicoDTO.getComplementoDto());
            statement.setString(13, medicoDTO.getnCasaDto());
            statement.setString(14, medicoDTO.getCEPDto());
            statement.setString(15, medicoDTO.getFoneFixoDto());
            statement.setString(16, medicoDTO.getCelularPrefDto());
            statement.setString(17, medicoDTO.getConselhoDto());
            statement.setInt(18, medicoDTO.getIdDto());

            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(MedicoDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * O código abaixo é uma pesquisa montada na linguagem estruturada
             * de consulta que tem como funcionalidade buscar no campo
             * nome_medico o nome digitado pelo usuário no FormMedico campo
             * txtPesquisa que foi setado pelo metodo de encapsulamento
             * setPesquisa() contido na camada MedicoDTO e agora recuperado pelo
             * parametro medico que recupera o valor digitado pelo
             * getPesquisa(). OBSERVAÇÃO IMPORTANTE:O operador ILIKE é
             * específico do PostgreSQL e seu comportamento é semelhante ao
             * LIKE. A única diferença é que ele é case-insensitive, ou seja,
             * não diferencia maiúsculas de minúsculas.
             * Fontes:https://pt.stackoverflow.com/questions/96926/como-fazer-consulta-sql-que-ignora-mai%C3%BAsculas-min%C3%BAsculas-e-acentos
             */
            /**
             * 12min
             */
            String sql = "DELETE FROM tbMedicos WHERE id=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, obj.getIdDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                    + "Dados deletados com sucesso. "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbAgendamentos`, CONSTRAINT `fk_tbAgendamentos_tbMedicos1` FOREIGN KEY (`fk_Medico`) REFERENCES `tbMedicos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)")) {

                JOptionPane.showMessageDialog(null, "" + "\n Info Camada DAO:\n"
                        + "O Registrodo Médico que está tentando excluir\n"
                        + "possui vínculo com um Agendamento ja processado,\n"
                        + "logo, esse Banco de Dados está fortemente relacionado.\n"
                        + "De forma que um registro não pode ser excluído quando\n"
                        + "outro depende do seu vínculo [Agendamento]<->[Médico]  ."
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        }
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MedicoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MedicoDTO buscarPor(MedicoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(MedicoDTO medicoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbMedicos where cpf='" + medicoDTO.getCpfDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    public boolean verificaDuplicidadeCPF(MedicoDTO medicoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbMedicos where CPF='" + medicoDTO.getCpfDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    @Override
    public MedicoDTO filtrarAoClicar(MedicoDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        MedicoDTO medicoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbMedicos WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                medicoDTO = new MedicoDTO();

                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setDataNascDto(resultSet.getString("dtNasc"));
                medicoDTO.setIdadeDto(resultSet.getString("idade"));
                medicoDTO.setCpfDto(resultSet.getString("cpf"));
                medicoDTO.setSexoDto(resultSet.getString("sexo"));
                medicoDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                medicoDTO.setEmailPrefDto(resultSet.getString("email"));
                medicoDTO.setUfDto(resultSet.getString("uf"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                medicoDTO.setBairroDto(resultSet.getString("bairro"));
                medicoDTO.setRuaDto(resultSet.getString("rua"));
                medicoDTO.setComplementoDto(resultSet.getString("complemento"));
                medicoDTO.setnCasaDto(resultSet.getString("ncasa"));
                medicoDTO.setCEPDto(resultSet.getString("cep"));
                medicoDTO.setFoneFixoDto(resultSet.getString("fone"));
                medicoDTO.setCelularPrefDto(resultSet.getString("celular"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));

                return medicoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(MedicoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(MedicoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(MedicoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(MedicoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MedicoDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
