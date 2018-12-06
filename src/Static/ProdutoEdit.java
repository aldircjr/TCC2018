/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Static;

import Model.Produto;

/**
 *
 * @author aldir
 */
public class ProdutoEdit {
    public static class ProdutoStatic{
        
        private static Produto produto;
        private static int previsaoParaRessuprir;

        public static Produto getProduto() {
            return produto;
        }

        public static void setProduto(Produto fornecedor) {
            ProdutoStatic.produto = fornecedor;
        }

        public static void setNull (){
            ProdutoStatic.produto = null;
        }

        public static int getPrevisaoParaRessuprir() {
            return previsaoParaRessuprir;
        }

        public static void setPrevisaoParaRessuprir(int previsaoParaRessuprir) {
            ProdutoStatic.previsaoParaRessuprir = previsaoParaRessuprir;
        }
        
        
    }
}
