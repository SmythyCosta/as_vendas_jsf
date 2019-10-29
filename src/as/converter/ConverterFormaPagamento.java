package as.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import as.controller.ControllerFormaPagamento;
import as.model.ModelFormaPagamento;

@FacesConverter("as.converter.converterFormaPagamento")
public class ConverterFormaPagamento implements Converter {

    private final ControllerFormaPagamento controllerFormaPagamento;
    
    public ConverterFormaPagamento(){
        controllerFormaPagamento = new ControllerFormaPagamento();
    }
    
    /**
     * Recebe uma String(geralmente a ID da Entity(model) e a converte em um objeto
     * @param context
     * @param component
     * @param value
     * @return ModelFormaPagamento
     */
    @Override
    public ModelFormaPagamento getAsObject(FacesContext context, UIComponent component, String value) {

        ModelFormaPagamento modelFormaPagamento = null;
        
        //valido a informacao vinda do form (componente selectFormaPagamento)
        if (!value.equals("")) {

            //converto para long o valor vindo do form
            long id = Long.parseLong(value);
            
            //busco o cliente no banco pela id
            modelFormaPagamento = controllerFormaPagamento.recuperarFormaPagamento(id);
        }

        return modelFormaPagamento;
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
        if (!(value instanceof ModelFormaPagamento)) {
                return null;
            }
            ModelFormaPagamento modelFormaPagamento = (ModelFormaPagamento) value;
        
           return modelFormaPagamento.getID().toString();
    }

}
