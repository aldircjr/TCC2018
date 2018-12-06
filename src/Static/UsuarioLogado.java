/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Static;

import ConsumerRest.UsuarioResource;
import Model.Usuario;
import com.google.gson.Gson;
import java.io.IOException;

/**
 *
 * @author aldir
 */
public class UsuarioLogado {
    
    
    public static class UsuarioStatic{
        
        private static Usuario usuario;

        public static Usuario getUsuario() {
            return usuario;
        }

        public static void setUsuario(Usuario usuario) {
            UsuarioStatic.usuario = usuario;
        }
        
        public static void atualizaUsuario() throws IOException{
            UsuarioResource usuario = new UsuarioResource();
            Gson json = new Gson();
            UsuarioStatic.usuario = json.fromJson(usuario.findByName(UsuarioStatic.usuario.getNome()), Usuario.class);
        }
    }
}
