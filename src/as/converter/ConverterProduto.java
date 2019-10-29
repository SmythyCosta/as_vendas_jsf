package as.converter;

import blsoft.com.br.controller.ControllerProduto;
import blsoft.com.br.model.ModelProduto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("as.converter.converterProduto")
public class ConverterProduto implements Converter {

    private final ControllerProduto controllerProduto;
    
    public ConverterProduto(){
        controllerProduto = new ControllerProduto();
    }
    
    /**
     * Recebe uma String(geralmente a ID da Entity(model) e a converte em um objeto
     * @param context
     * @param component
     * @param value
     * @return ModelProduto
     */
    @Override
    public ModelProduto getAsObject(FacesContext context, UIComponent component, String value) {

        ModelProduto modelProduto = null;
        
        //valido a informacao vinda do form (componente selectProduto)
        if (!value.equals("")) {

            //converto para long o valor vindo do form
            long id = Long.parseLong(value);
            
            //busco o cliente no banco pela id
            modelProduto = controllerProduto.recuperarProduto(id);
        }

        return modelProduto;
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
        if (!(value instanceof ModelProduto)) {
                return null;
            }
            ModelProduto modelProduto = (ModelProduto) value;
        
           return modelProduto.getID().toString();
    }

}
