package br.com.multclin.dao;

import br.com.multclin.dto.PacienteDTO;
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
public class PacienteDAO implements GenericDAO<PacienteDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<PacienteDTO> listarTodos() throws PersistenciaException {

        List<PacienteDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes order by nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PacienteDTO pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCpfDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));
                /**
                 * Adiciona na lista todos os dados capturado pelo laço e
                 * adicionado no objeto pacienteDTO
                 */
                lista.add(pacienteDTO);

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

    public List<PacienteDTO> filtrarPesqRapidaPorCPF(String pesquisar) throws PersistenciaException {

        List<PacienteDTO> lista = new ArrayList<PacienteDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes WHERE cpf LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PacienteDTO pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCpfDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));

                lista.add(pacienteDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<PacienteDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<PacienteDTO> lista = new ArrayList<PacienteDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PacienteDTO pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCpfDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));

                lista.add(pacienteDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<PacienteDTO> buscarPorID(int codigo) throws PersistenciaException {

        List<PacienteDTO> lista = new ArrayList<PacienteDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes where id=" + codigo;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PacienteDTO pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCpfDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));

                lista.add(pacienteDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public PacienteDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        PacienteDTO pacienteDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCpfDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));

                return pacienteDTO;
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
    public void inserir(PacienteDTO pacienteDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbpacientes(id,nome,dtNasc,idade,cpf,sexo,estadocivil,conjuge,mae,pai,celular,email,uf,cidade,bairro,rua,complemento,ncasa,cep,cadastro) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, pacienteDTO.getIdDto());
            statement.setString(2, pacienteDTO.getNomeDto());
            statement.setString(3, pacienteDTO.getDataNascDto());
            statement.setString(4, pacienteDTO.getIdadeDto());
            statement.setString(5, pacienteDTO.getCpfDto());
            statement.setString(6, pacienteDTO.getSexoDto());
            statement.setString(7, pacienteDTO.getEstadoCivilDto());
            statement.setString(8, pacienteDTO.getConjugeDto());
            statement.setString(9, pacienteDTO.getMaeDto());
            statement.setString(10, pacienteDTO.getPaiDto());
            statement.setString(11, pacienteDTO.getCelularPrefDto());
            statement.setString(12, pacienteDTO.getEmailPrefDto());
            statement.setString(13, pacienteDTO.getUfDto());
            statement.setString(14, pacienteDTO.getCidadeDto());
            statement.setString(15, pacienteDTO.getBairroDto());
            statement.setString(16, pacienteDTO.getRuaDto());
            statement.setString(17, pacienteDTO.getComplementoDto());
            statement.setString(18, pacienteDTO.getnCasaDto());
            statement.setString(19, pacienteDTO.getCEPDto());
            statement.setDate(20, null);

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(PacienteDTO pacienteDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "UPDATE tbpacientes SET nome=?,dtNasc=?,idade=?,cpf=?,sexo=?,estadocivil=?,conjuge=?,mae=?,pai=?,celular=?,email=?,uf=?,cidade=?,bairro=?,rua=?,complemento=?,ncasa=?,cep=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, pacienteDTO.getNomeDto());//nome
            statement.setString(2, pacienteDTO.getDataNascDto());//data nascimento
            statement.setString(3, pacienteDTO.getIdadeDto());//idade
            statement.setString(4, pacienteDTO.getCpfDto());//cpf
            statement.setString(5, pacienteDTO.getSexoDto());//sexo
            statement.setString(6, pacienteDTO.getEstadoCivilDto());//estado civil
            statement.setString(7, pacienteDTO.getConjugeDto());//conjuge
            statement.setString(8, pacienteDTO.getMaeDto());//mae
            statement.setString(9, pacienteDTO.getPaiDto());//pai

            statement.setString(10, pacienteDTO.getCelularPrefDto());
            statement.setString(11, pacienteDTO.getEmailPrefDto());
            statement.setString(12, pacienteDTO.getUfDto());
            statement.setString(13, pacienteDTO.getCidadeDto());
            statement.setString(14, pacienteDTO.getBairroDto());
            statement.setString(15, pacienteDTO.getRuaDto());
            statement.setString(16, pacienteDTO.getComplementoDto());
            statement.setString(17, pacienteDTO.getnCasaDto());
            statement.setString(18, pacienteDTO.getCEPDto());
            statement.setInt(19, pacienteDTO.getIdDto());

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
    public void deletar(PacienteDTO obj) throws PersistenciaException {
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
            String sql = "DELETE FROM tbpacientes WHERE id=?";

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
            //                          
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbAgendamentos`, CONSTRAINT `fk_tbAgendamentos_tbpacientes1` FOREIGN KEY (`fk_paciente`) REFERENCES `tbpacientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)")) {

                JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                        + "O Registro do Paciente está vinculado\n"
                        + "à um Agendamento. O Banco de Dados foi  \n"
                        + "fortemente relacionado para que não haja \n"
                        + "perca de registros ou registros sem vínculos\n"
                        + "(soltos) dentro da modelagem conceitual \n"
                        + "proposta no projeto."
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
    public PacienteDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PacienteDTO buscarPor(PacienteDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(PacienteDTO pacienteDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbpacientes where cpf='" + pacienteDTO.getCpfDto() + "'";

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

    public boolean verificaDuplicidadeCPF(PacienteDTO pacienteDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbpacientes where CPF='" + pacienteDTO.getCpfDto() + "'";

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
    public PacienteDTO filtrarAoClicar(PacienteDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        PacienteDTO pacienteDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpacientes WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";

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
                pacienteDTO = new PacienteDTO();

                pacienteDTO.setIdDto(resultSet.getInt("id"));
                pacienteDTO.setNomeDto(resultSet.getString("nome"));
                pacienteDTO.setDataNascDto(resultSet.getString("dtNasc"));
                pacienteDTO.setIdadeDto(resultSet.getString("idade"));
                pacienteDTO.setCEPDto(resultSet.getString("cpf"));
                pacienteDTO.setSexoDto(resultSet.getString("sexo"));
                pacienteDTO.setEstadoCivilDto(resultSet.getString("estadocivil"));
                pacienteDTO.setConjugeDto(resultSet.getString("conjuge"));
                pacienteDTO.setMaeDto(resultSet.getString("mae"));
                pacienteDTO.setPaiDto(resultSet.getString("pai"));
                pacienteDTO.setCelularPrefDto(resultSet.getString("celular"));
                pacienteDTO.setEmailPrefDto(resultSet.getString("email"));
                pacienteDTO.setUfDto(resultSet.getString("uf"));
                pacienteDTO.setCidadeDto(resultSet.getString("cidade"));
                pacienteDTO.setBairroDto(resultSet.getString("bairro"));
                pacienteDTO.setRuaDto(resultSet.getString("rua"));
                pacienteDTO.setComplementoDto(resultSet.getString("complemento"));
                pacienteDTO.setnCasaDto(resultSet.getString("ncasa"));
                pacienteDTO.setCEPDto(resultSet.getString("cep"));

                return pacienteDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(PacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PacienteDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
