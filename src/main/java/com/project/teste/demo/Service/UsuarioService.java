package com.project.teste.demo.Service;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;


    @Autowired
    private UsuarioRepository repository;

    public int createUserService(Usuario entityUser) {
        int retornoRepository = repository.crateUserRepository(entityUser);
        return retornoRepository;
    }

    public List<Usuario> listaUsuarioService() {
        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        return retornoConsulta;
    }

    public ResponseEntity<UsuarioRespose> loginUserService(Usuario entityUser) {
        UsuarioRespose loginResponse = new UsuarioRespose();

         String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE EMAIL = ? AND SENHA = ?) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL \n";
         String loginSucess = jdbcTemplate.queryForObject(sql, String.class, entityUser.getEmail(), entityUser.getSenha());

        if (loginSucess.equals("1")){
            loginResponse.setSucesso(true);
            loginResponse.setMensagem("Login efetuado com sucesso");
        } else{
            loginResponse.setSucesso(false);
            loginResponse.setMensagem("Credenciais inválidas");
        }
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    public ResponseEntity<UsuarioRespose> atualizaUsuario(Usuario usuario) throws RuntimeException {
        UsuarioRespose response = new UsuarioRespose();

            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
            String retornoConsulta = jdbcTemplate.queryForObject(sql, String.class, usuario.getId(), usuario.getSenha(), usuario.getSenha());

            System.out.println("retornoConsulta " + retornoConsulta);

            if (retornoConsulta.equals("1")){
                response.setSucesso(true);
                response.setMensagem("Atualização realizada com sucesso. ");
            }else{
                response.setSucesso(false);
                response.setMensagem("Erro. A senha ja foi utilizada. ");
            }
        try {
            repository.atualizaUsuario(usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
