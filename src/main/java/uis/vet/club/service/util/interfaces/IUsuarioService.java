package uis.vet.club.service.util.interfaces;

import uis.vet.club.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    public List<Usuario> listarUsuarios();

    public Usuario guardar(Usuario usuario) throws Exception;

    public void eliminar(Usuario usuario);

    public Usuario encontrarUsuario(Usuario usuario);
}
