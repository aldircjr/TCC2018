/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Static;

import Model.Pedido;
import Model.Produto;

/**
 *
 * @author aldir
 */
public class PedidoEdit {
    public static class ProdutoStatic{
        
        private static Pedido pedido;

        public static Pedido getPedido() {
            return pedido;
        }

        public static void setPedido(Pedido pedido) {
            ProdutoStatic.pedido = pedido;
        }
        
        public static void setNull (){
            ProdutoStatic.pedido = null;
        }
        
    }
}
