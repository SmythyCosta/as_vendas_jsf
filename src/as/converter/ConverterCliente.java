package as.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import as.model.ModelCliente;
import as.controller.ControllerCliente;


@FacesConverter("as.converter.converterCliente")
public class ConverterCliente implements Converter {

    private final ControllerCliente controllerCliente;
    
    public ConverterCliente(){
        controllerCliente = new ControllerCliente();
    }
    
    /**
     * Recebe uma String(geralmente a ID da Entity(model) e a converte em um objeto
     * @param context
     * @param component
     * @param value
     * @return ModelCliente
     */
    @Override
    public ModelCliente getAsObject(FacesContext context, UIComponent component, String value) {

        ModelCliente modelCliente = null;
        
        //valido a informacao vinda do form (componente selectCliente)
        if (!value.equals("")) {

            //converto para long o valor vindo do form
            long id = Long.parseLong(value);
            
            //busco o cliente no banco pela id
            modelCliente = controllerCliente.recuperarCliente(id);
        }

        return modelCliente;
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
        if (!(value instanceof ModelCliente)) {
                return null;
            }
            ModelCliente modelCliente = (ModelCliente) value;
        
           return modelCliente.getId().toString();
    }

}
