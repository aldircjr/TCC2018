/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Static;

import Model.Fornecedor;

/**
 *
 * @author aldir
 */
public class FornecedorEdit {
    public static class FornecedorStatic{
        
        private static Fornecedor fornecedor;

        public static Fornecedor getFornecedor() {
            return fornecedor;
        }

        public static void setFornecedor(Fornecedor fornecedor) {
            FornecedorStatic.fornecedor = fornecedor;
        }

        public static void setNull (){
            FornecedorStatic.fornecedor = null;
        }
    }
}
