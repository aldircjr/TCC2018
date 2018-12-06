/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Static;

import Model.Usuario;

/**
 *
 * @author aldir
 */
public class UsuarioEdit {
    public static class UsuarioStatic{
        
        private static Usuario usuario;

        public static Usuario getUsuario() {
            return usuario;
        }

        public static void setUsuario(Usuario usuario) {
            UsuarioStatic.usuario = usuario;
        }
        
        public static void setNull (){
            UsuarioStatic.usuario = null;
        }
    }
}
