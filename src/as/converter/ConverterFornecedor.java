package as.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import as.controller.ControllerFornecedor;
import as.model.ModelFornecedor;

@FacesConverter("as.converter.converterFornecedor")
public class ConverterFornecedor implements Converter {

    private final ControllerFornecedor controllerFornecedor;
    
    public ConverterFornecedor(){
        controllerFornecedor = new ControllerFornecedor();
    }
    
    /**
     * Recebe uma String(geralmente a ID da Entity(model) e a converte em um objeto
     * @param context
     * @param component
     * @param value
     * @return ModelFornecedor
     */
    @Override
    public ModelFornecedor getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("VALOR " + value);

        ModelFornecedor modelFornecedor = null;
        
        //valido a informacao vinda do form (arquivo viewProduto.xhtml componente selectFornecedor)
        if (!value.equals("")) {

            //converto para long o valor vindo do form
            long id = Long.parseLong(value);
            
            //busco o fornecedor no banco pela id
            modelFornecedor = controllerFornecedor.recuperarFornecedor(id);
        }

        return modelFornecedor;
    }

    /**
     * Recebe um Object e o transforma numa String
     * @param context
     * @param component
     * @param value
     * @return String
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (!(value instanceof ModelFornecedor)) {
                return null;
            }
            ModelFornecedor modelFornecedor = (ModelFornecedor) value;
        
           return modelFornecedor.getId().toString();
    }

}
